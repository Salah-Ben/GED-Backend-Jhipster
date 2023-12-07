package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.ClientPhysique;
import com.icb.ged.repository.ClientPhysiqueRepository;
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
 * Integration tests for the {@link ClientPhysiqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientPhysiqueResourceIT {

    private static final String DEFAULT_NOM_CLIENT_PHYSISQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT_PHYSISQUE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CLIENT_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CLIENT_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_CIN_CLIENT_PHYSISQUE = "AAAAAAAAAA";
    private static final String UPDATED_CIN_CLIENT_PHYSISQUE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CLIENT_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CLIENT_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_CLIENT_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_CLIENT_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_CLIENT_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_CLIENT_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_CLIENT_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CLIENT_PHYSIQUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-physiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientPhysiqueRepository clientPhysiqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientPhysiqueMockMvc;

    private ClientPhysique clientPhysique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPhysique createEntity(EntityManager em) {
        ClientPhysique clientPhysique = new ClientPhysique()
            .nomClientPhysisque(DEFAULT_NOM_CLIENT_PHYSISQUE)
            .prenomClientPhysique(DEFAULT_PRENOM_CLIENT_PHYSIQUE)
            .cinClientPhysisque(DEFAULT_CIN_CLIENT_PHYSISQUE)
            .emailClientPhysique(DEFAULT_EMAIL_CLIENT_PHYSIQUE)
            .telephoneClientPhysique(DEFAULT_TELEPHONE_CLIENT_PHYSIQUE)
            .adresseClientPhysique(DEFAULT_ADRESSE_CLIENT_PHYSIQUE)
            .villeClientPhysique(DEFAULT_VILLE_CLIENT_PHYSIQUE);
        return clientPhysique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPhysique createUpdatedEntity(EntityManager em) {
        ClientPhysique clientPhysique = new ClientPhysique()
            .nomClientPhysisque(UPDATED_NOM_CLIENT_PHYSISQUE)
            .prenomClientPhysique(UPDATED_PRENOM_CLIENT_PHYSIQUE)
            .cinClientPhysisque(UPDATED_CIN_CLIENT_PHYSISQUE)
            .emailClientPhysique(UPDATED_EMAIL_CLIENT_PHYSIQUE)
            .telephoneClientPhysique(UPDATED_TELEPHONE_CLIENT_PHYSIQUE)
            .adresseClientPhysique(UPDATED_ADRESSE_CLIENT_PHYSIQUE)
            .villeClientPhysique(UPDATED_VILLE_CLIENT_PHYSIQUE);
        return clientPhysique;
    }

    @BeforeEach
    public void initTest() {
        clientPhysique = createEntity(em);
    }

    @Test
    @Transactional
    void createClientPhysique() throws Exception {
        int databaseSizeBeforeCreate = clientPhysiqueRepository.findAll().size();
        // Create the ClientPhysique
        restClientPhysiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isCreated());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPhysique testClientPhysique = clientPhysiqueList.get(clientPhysiqueList.size() - 1);
        assertThat(testClientPhysique.getNomClientPhysisque()).isEqualTo(DEFAULT_NOM_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getPrenomClientPhysique()).isEqualTo(DEFAULT_PRENOM_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getCinClientPhysisque()).isEqualTo(DEFAULT_CIN_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getEmailClientPhysique()).isEqualTo(DEFAULT_EMAIL_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getTelephoneClientPhysique()).isEqualTo(DEFAULT_TELEPHONE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getAdresseClientPhysique()).isEqualTo(DEFAULT_ADRESSE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getVilleClientPhysique()).isEqualTo(DEFAULT_VILLE_CLIENT_PHYSIQUE);
    }

    @Test
    @Transactional
    void createClientPhysiqueWithExistingId() throws Exception {
        // Create the ClientPhysique with an existing ID
        clientPhysique.setId(1L);

        int databaseSizeBeforeCreate = clientPhysiqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPhysiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientPhysiques() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        // Get all the clientPhysiqueList
        restClientPhysiqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPhysique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomClientPhysisque").value(hasItem(DEFAULT_NOM_CLIENT_PHYSISQUE)))
            .andExpect(jsonPath("$.[*].prenomClientPhysique").value(hasItem(DEFAULT_PRENOM_CLIENT_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].cinClientPhysisque").value(hasItem(DEFAULT_CIN_CLIENT_PHYSISQUE)))
            .andExpect(jsonPath("$.[*].emailClientPhysique").value(hasItem(DEFAULT_EMAIL_CLIENT_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].telephoneClientPhysique").value(hasItem(DEFAULT_TELEPHONE_CLIENT_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].adresseClientPhysique").value(hasItem(DEFAULT_ADRESSE_CLIENT_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].villeClientPhysique").value(hasItem(DEFAULT_VILLE_CLIENT_PHYSIQUE)));
    }

    @Test
    @Transactional
    void getClientPhysique() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        // Get the clientPhysique
        restClientPhysiqueMockMvc
            .perform(get(ENTITY_API_URL_ID, clientPhysique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientPhysique.getId().intValue()))
            .andExpect(jsonPath("$.nomClientPhysisque").value(DEFAULT_NOM_CLIENT_PHYSISQUE))
            .andExpect(jsonPath("$.prenomClientPhysique").value(DEFAULT_PRENOM_CLIENT_PHYSIQUE))
            .andExpect(jsonPath("$.cinClientPhysisque").value(DEFAULT_CIN_CLIENT_PHYSISQUE))
            .andExpect(jsonPath("$.emailClientPhysique").value(DEFAULT_EMAIL_CLIENT_PHYSIQUE))
            .andExpect(jsonPath("$.telephoneClientPhysique").value(DEFAULT_TELEPHONE_CLIENT_PHYSIQUE))
            .andExpect(jsonPath("$.adresseClientPhysique").value(DEFAULT_ADRESSE_CLIENT_PHYSIQUE))
            .andExpect(jsonPath("$.villeClientPhysique").value(DEFAULT_VILLE_CLIENT_PHYSIQUE));
    }

    @Test
    @Transactional
    void getNonExistingClientPhysique() throws Exception {
        // Get the clientPhysique
        restClientPhysiqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClientPhysique() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();

        // Update the clientPhysique
        ClientPhysique updatedClientPhysique = clientPhysiqueRepository.findById(clientPhysique.getId()).get();
        // Disconnect from session so that the updates on updatedClientPhysique are not directly saved in db
        em.detach(updatedClientPhysique);
        updatedClientPhysique
            .nomClientPhysisque(UPDATED_NOM_CLIENT_PHYSISQUE)
            .prenomClientPhysique(UPDATED_PRENOM_CLIENT_PHYSIQUE)
            .cinClientPhysisque(UPDATED_CIN_CLIENT_PHYSISQUE)
            .emailClientPhysique(UPDATED_EMAIL_CLIENT_PHYSIQUE)
            .telephoneClientPhysique(UPDATED_TELEPHONE_CLIENT_PHYSIQUE)
            .adresseClientPhysique(UPDATED_ADRESSE_CLIENT_PHYSIQUE)
            .villeClientPhysique(UPDATED_VILLE_CLIENT_PHYSIQUE);

        restClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientPhysique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        ClientPhysique testClientPhysique = clientPhysiqueList.get(clientPhysiqueList.size() - 1);
        assertThat(testClientPhysique.getNomClientPhysisque()).isEqualTo(UPDATED_NOM_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getPrenomClientPhysique()).isEqualTo(UPDATED_PRENOM_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getCinClientPhysisque()).isEqualTo(UPDATED_CIN_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getEmailClientPhysique()).isEqualTo(UPDATED_EMAIL_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getTelephoneClientPhysique()).isEqualTo(UPDATED_TELEPHONE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getAdresseClientPhysique()).isEqualTo(UPDATED_ADRESSE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getVilleClientPhysique()).isEqualTo(UPDATED_VILLE_CLIENT_PHYSIQUE);
    }

    @Test
    @Transactional
    void putNonExistingClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientPhysique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhysique)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientPhysiqueWithPatch() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();

        // Update the clientPhysique using partial update
        ClientPhysique partialUpdatedClientPhysique = new ClientPhysique();
        partialUpdatedClientPhysique.setId(clientPhysique.getId());

        partialUpdatedClientPhysique
            .prenomClientPhysique(UPDATED_PRENOM_CLIENT_PHYSIQUE)
            .cinClientPhysisque(UPDATED_CIN_CLIENT_PHYSISQUE)
            .emailClientPhysique(UPDATED_EMAIL_CLIENT_PHYSIQUE)
            .telephoneClientPhysique(UPDATED_TELEPHONE_CLIENT_PHYSIQUE)
            .adresseClientPhysique(UPDATED_ADRESSE_CLIENT_PHYSIQUE);

        restClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        ClientPhysique testClientPhysique = clientPhysiqueList.get(clientPhysiqueList.size() - 1);
        assertThat(testClientPhysique.getNomClientPhysisque()).isEqualTo(DEFAULT_NOM_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getPrenomClientPhysique()).isEqualTo(UPDATED_PRENOM_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getCinClientPhysisque()).isEqualTo(UPDATED_CIN_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getEmailClientPhysique()).isEqualTo(UPDATED_EMAIL_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getTelephoneClientPhysique()).isEqualTo(UPDATED_TELEPHONE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getAdresseClientPhysique()).isEqualTo(UPDATED_ADRESSE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getVilleClientPhysique()).isEqualTo(DEFAULT_VILLE_CLIENT_PHYSIQUE);
    }

    @Test
    @Transactional
    void fullUpdateClientPhysiqueWithPatch() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();

        // Update the clientPhysique using partial update
        ClientPhysique partialUpdatedClientPhysique = new ClientPhysique();
        partialUpdatedClientPhysique.setId(clientPhysique.getId());

        partialUpdatedClientPhysique
            .nomClientPhysisque(UPDATED_NOM_CLIENT_PHYSISQUE)
            .prenomClientPhysique(UPDATED_PRENOM_CLIENT_PHYSIQUE)
            .cinClientPhysisque(UPDATED_CIN_CLIENT_PHYSISQUE)
            .emailClientPhysique(UPDATED_EMAIL_CLIENT_PHYSIQUE)
            .telephoneClientPhysique(UPDATED_TELEPHONE_CLIENT_PHYSIQUE)
            .adresseClientPhysique(UPDATED_ADRESSE_CLIENT_PHYSIQUE)
            .villeClientPhysique(UPDATED_VILLE_CLIENT_PHYSIQUE);

        restClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        ClientPhysique testClientPhysique = clientPhysiqueList.get(clientPhysiqueList.size() - 1);
        assertThat(testClientPhysique.getNomClientPhysisque()).isEqualTo(UPDATED_NOM_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getPrenomClientPhysique()).isEqualTo(UPDATED_PRENOM_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getCinClientPhysisque()).isEqualTo(UPDATED_CIN_CLIENT_PHYSISQUE);
        assertThat(testClientPhysique.getEmailClientPhysique()).isEqualTo(UPDATED_EMAIL_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getTelephoneClientPhysique()).isEqualTo(UPDATED_TELEPHONE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getAdresseClientPhysique()).isEqualTo(UPDATED_ADRESSE_CLIENT_PHYSIQUE);
        assertThat(testClientPhysique.getVilleClientPhysique()).isEqualTo(UPDATED_VILLE_CLIENT_PHYSIQUE);
    }

    @Test
    @Transactional
    void patchNonExistingClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = clientPhysiqueRepository.findAll().size();
        clientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientPhysique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientPhysique in the database
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientPhysique() throws Exception {
        // Initialize the database
        clientPhysiqueRepository.saveAndFlush(clientPhysique);

        int databaseSizeBeforeDelete = clientPhysiqueRepository.findAll().size();

        // Delete the clientPhysique
        restClientPhysiqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientPhysique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientPhysique> clientPhysiqueList = clientPhysiqueRepository.findAll();
        assertThat(clientPhysiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
