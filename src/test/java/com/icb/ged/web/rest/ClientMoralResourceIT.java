package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.ClientMoral;
import com.icb.ged.repository.ClientMoralRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClientMoralResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientMoralResourceIT {

    private static final String DEFAULT_NOM_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-morals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientMoralRepository clientMoralRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMoralMockMvc;

    private ClientMoral clientMoral;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientMoral createEntity(EntityManager em) {
        ClientMoral clientMoral = new ClientMoral()
            .nomClientMoral(DEFAULT_NOM_CLIENT_MORAL)
            .numeroIdentificationClientMoral(DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL)
            .adresseClientMoral(DEFAULT_ADRESSE_CLIENT_MORAL)
            .emailClientMoral(DEFAULT_EMAIL_CLIENT_MORAL)
            .telephoneClientMoral(DEFAULT_TELEPHONE_CLIENT_MORAL)
            .villeClientMoral(DEFAULT_VILLE_CLIENT_MORAL)
            .activiteClientMoral(DEFAULT_ACTIVITE_CLIENT_MORAL);
        return clientMoral;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientMoral createUpdatedEntity(EntityManager em) {
        ClientMoral clientMoral = new ClientMoral()
            .nomClientMoral(UPDATED_NOM_CLIENT_MORAL)
            .numeroIdentificationClientMoral(UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL)
            .adresseClientMoral(UPDATED_ADRESSE_CLIENT_MORAL)
            .emailClientMoral(UPDATED_EMAIL_CLIENT_MORAL)
            .telephoneClientMoral(UPDATED_TELEPHONE_CLIENT_MORAL)
            .villeClientMoral(UPDATED_VILLE_CLIENT_MORAL)
            .activiteClientMoral(UPDATED_ACTIVITE_CLIENT_MORAL);
        return clientMoral;
    }

    @BeforeEach
    public void initTest() {
        clientMoral = createEntity(em);
    }

    @Test
    @Transactional
    void createClientMoral() throws Exception {
        int databaseSizeBeforeCreate = clientMoralRepository.findAll().size();
        // Create the ClientMoral
        restClientMoralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientMoral)))
            .andExpect(status().isCreated());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeCreate + 1);
        ClientMoral testClientMoral = clientMoralList.get(clientMoralList.size() - 1);
        assertThat(testClientMoral.getNomClientMoral()).isEqualTo(DEFAULT_NOM_CLIENT_MORAL);
        assertThat(testClientMoral.getNumeroIdentificationClientMoral()).isEqualTo(DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL);
        assertThat(testClientMoral.getAdresseClientMoral()).isEqualTo(DEFAULT_ADRESSE_CLIENT_MORAL);
        assertThat(testClientMoral.getEmailClientMoral()).isEqualTo(DEFAULT_EMAIL_CLIENT_MORAL);
        assertThat(testClientMoral.getTelephoneClientMoral()).isEqualTo(DEFAULT_TELEPHONE_CLIENT_MORAL);
        assertThat(testClientMoral.getVilleClientMoral()).isEqualTo(DEFAULT_VILLE_CLIENT_MORAL);
        assertThat(testClientMoral.getActiviteClientMoral()).isEqualTo(DEFAULT_ACTIVITE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void createClientMoralWithExistingId() throws Exception {
        // Create the ClientMoral with an existing ID
        clientMoral.setId(1L);

        int databaseSizeBeforeCreate = clientMoralRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMoralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientMoral)))
            .andExpect(status().isBadRequest());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientMorals() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        // Get all the clientMoralList
        restClientMoralMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientMoral.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomClientMoral").value(hasItem(DEFAULT_NOM_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].numeroIdentificationClientMoral").value(hasItem(DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].adresseClientMoral").value(hasItem(DEFAULT_ADRESSE_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].emailClientMoral").value(hasItem(DEFAULT_EMAIL_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].telephoneClientMoral").value(hasItem(DEFAULT_TELEPHONE_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].villeClientMoral").value(hasItem(DEFAULT_VILLE_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].activiteClientMoral").value(hasItem(DEFAULT_ACTIVITE_CLIENT_MORAL)));
    }

    @Test
    @Transactional
    void getClientMoral() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        // Get the clientMoral
        restClientMoralMockMvc
            .perform(get(ENTITY_API_URL_ID, clientMoral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientMoral.getId().intValue()))
            .andExpect(jsonPath("$.nomClientMoral").value(DEFAULT_NOM_CLIENT_MORAL))
            .andExpect(jsonPath("$.numeroIdentificationClientMoral").value(DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL))
            .andExpect(jsonPath("$.adresseClientMoral").value(DEFAULT_ADRESSE_CLIENT_MORAL))
            .andExpect(jsonPath("$.emailClientMoral").value(DEFAULT_EMAIL_CLIENT_MORAL))
            .andExpect(jsonPath("$.telephoneClientMoral").value(DEFAULT_TELEPHONE_CLIENT_MORAL))
            .andExpect(jsonPath("$.villeClientMoral").value(DEFAULT_VILLE_CLIENT_MORAL))
            .andExpect(jsonPath("$.activiteClientMoral").value(DEFAULT_ACTIVITE_CLIENT_MORAL));
    }

    @Test
    @Transactional
    void getNonExistingClientMoral() throws Exception {
        // Get the clientMoral
        restClientMoralMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClientMoral() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();

        // Update the clientMoral
        ClientMoral updatedClientMoral = clientMoralRepository.findById(clientMoral.getId()).get();
        // Disconnect from session so that the updates on updatedClientMoral are not directly saved in db
        em.detach(updatedClientMoral);
        updatedClientMoral
            .nomClientMoral(UPDATED_NOM_CLIENT_MORAL)
            .numeroIdentificationClientMoral(UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL)
            .adresseClientMoral(UPDATED_ADRESSE_CLIENT_MORAL)
            .emailClientMoral(UPDATED_EMAIL_CLIENT_MORAL)
            .telephoneClientMoral(UPDATED_TELEPHONE_CLIENT_MORAL)
            .villeClientMoral(UPDATED_VILLE_CLIENT_MORAL)
            .activiteClientMoral(UPDATED_ACTIVITE_CLIENT_MORAL);

        restClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
        ClientMoral testClientMoral = clientMoralList.get(clientMoralList.size() - 1);
        assertThat(testClientMoral.getNomClientMoral()).isEqualTo(UPDATED_NOM_CLIENT_MORAL);
        assertThat(testClientMoral.getNumeroIdentificationClientMoral()).isEqualTo(UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL);
        assertThat(testClientMoral.getAdresseClientMoral()).isEqualTo(UPDATED_ADRESSE_CLIENT_MORAL);
        assertThat(testClientMoral.getEmailClientMoral()).isEqualTo(UPDATED_EMAIL_CLIENT_MORAL);
        assertThat(testClientMoral.getTelephoneClientMoral()).isEqualTo(UPDATED_TELEPHONE_CLIENT_MORAL);
        assertThat(testClientMoral.getVilleClientMoral()).isEqualTo(UPDATED_VILLE_CLIENT_MORAL);
        assertThat(testClientMoral.getActiviteClientMoral()).isEqualTo(UPDATED_ACTIVITE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void putNonExistingClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientMoral)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientMoralWithPatch() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();

        // Update the clientMoral using partial update
        ClientMoral partialUpdatedClientMoral = new ClientMoral();
        partialUpdatedClientMoral.setId(clientMoral.getId());

        partialUpdatedClientMoral
            .emailClientMoral(UPDATED_EMAIL_CLIENT_MORAL)
            .villeClientMoral(UPDATED_VILLE_CLIENT_MORAL)
            .activiteClientMoral(UPDATED_ACTIVITE_CLIENT_MORAL);

        restClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
        ClientMoral testClientMoral = clientMoralList.get(clientMoralList.size() - 1);
        assertThat(testClientMoral.getNomClientMoral()).isEqualTo(DEFAULT_NOM_CLIENT_MORAL);
        assertThat(testClientMoral.getNumeroIdentificationClientMoral()).isEqualTo(DEFAULT_NUMERO_IDENTIFICATION_CLIENT_MORAL);
        assertThat(testClientMoral.getAdresseClientMoral()).isEqualTo(DEFAULT_ADRESSE_CLIENT_MORAL);
        assertThat(testClientMoral.getEmailClientMoral()).isEqualTo(UPDATED_EMAIL_CLIENT_MORAL);
        assertThat(testClientMoral.getTelephoneClientMoral()).isEqualTo(DEFAULT_TELEPHONE_CLIENT_MORAL);
        assertThat(testClientMoral.getVilleClientMoral()).isEqualTo(UPDATED_VILLE_CLIENT_MORAL);
        assertThat(testClientMoral.getActiviteClientMoral()).isEqualTo(UPDATED_ACTIVITE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void fullUpdateClientMoralWithPatch() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();

        // Update the clientMoral using partial update
        ClientMoral partialUpdatedClientMoral = new ClientMoral();
        partialUpdatedClientMoral.setId(clientMoral.getId());

        partialUpdatedClientMoral
            .nomClientMoral(UPDATED_NOM_CLIENT_MORAL)
            .numeroIdentificationClientMoral(UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL)
            .adresseClientMoral(UPDATED_ADRESSE_CLIENT_MORAL)
            .emailClientMoral(UPDATED_EMAIL_CLIENT_MORAL)
            .telephoneClientMoral(UPDATED_TELEPHONE_CLIENT_MORAL)
            .villeClientMoral(UPDATED_VILLE_CLIENT_MORAL)
            .activiteClientMoral(UPDATED_ACTIVITE_CLIENT_MORAL);

        restClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
        ClientMoral testClientMoral = clientMoralList.get(clientMoralList.size() - 1);
        assertThat(testClientMoral.getNomClientMoral()).isEqualTo(UPDATED_NOM_CLIENT_MORAL);
        assertThat(testClientMoral.getNumeroIdentificationClientMoral()).isEqualTo(UPDATED_NUMERO_IDENTIFICATION_CLIENT_MORAL);
        assertThat(testClientMoral.getAdresseClientMoral()).isEqualTo(UPDATED_ADRESSE_CLIENT_MORAL);
        assertThat(testClientMoral.getEmailClientMoral()).isEqualTo(UPDATED_EMAIL_CLIENT_MORAL);
        assertThat(testClientMoral.getTelephoneClientMoral()).isEqualTo(UPDATED_TELEPHONE_CLIENT_MORAL);
        assertThat(testClientMoral.getVilleClientMoral()).isEqualTo(UPDATED_VILLE_CLIENT_MORAL);
        assertThat(testClientMoral.getActiviteClientMoral()).isEqualTo(UPDATED_ACTIVITE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void patchNonExistingClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = clientMoralRepository.findAll().size();
        clientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientMoral))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientMoral in the database
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientMoral() throws Exception {
        // Initialize the database
        clientMoralRepository.saveAndFlush(clientMoral);

        int databaseSizeBeforeDelete = clientMoralRepository.findAll().size();

        // Delete the clientMoral
        restClientMoralMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientMoral.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientMoral> clientMoralList = clientMoralRepository.findAll();
        assertThat(clientMoralList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
