package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.Collaborateur;
import com.icb.ged.repository.CollaborateurRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollaborateurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CollaborateurResourceIT {

    private static final Long DEFAULT_COLLABORATEUR_ID = 1L;
    private static final Long UPDATED_COLLABORATEUR_ID = 2L;

    private static final String DEFAULT_NOM_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CIN_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_CIN_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_COLLABORATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_COLLABORATEUR = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_COLLABORATEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collaborateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Mock
    private CollaborateurRepository collaborateurRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollaborateurMockMvc;

    private Collaborateur collaborateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createEntity(EntityManager em) {
        Collaborateur collaborateur = new Collaborateur()
            .collaborateurID(DEFAULT_COLLABORATEUR_ID)
            .nomCollaborateur(DEFAULT_NOM_COLLABORATEUR)
            .prenomCollaborateur(DEFAULT_PRENOM_COLLABORATEUR)
            .emailCollaborateur(DEFAULT_EMAIL_COLLABORATEUR)
            .telephoneCollaborateur(DEFAULT_TELEPHONE_COLLABORATEUR)
            .cinCollaborateur(DEFAULT_CIN_COLLABORATEUR)
            .adresseCollaborateur(DEFAULT_ADRESSE_COLLABORATEUR)
            .villeCollaborateur(DEFAULT_VILLE_COLLABORATEUR);
        return collaborateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createUpdatedEntity(EntityManager em) {
        Collaborateur collaborateur = new Collaborateur()
            .collaborateurID(UPDATED_COLLABORATEUR_ID)
            .nomCollaborateur(UPDATED_NOM_COLLABORATEUR)
            .prenomCollaborateur(UPDATED_PRENOM_COLLABORATEUR)
            .emailCollaborateur(UPDATED_EMAIL_COLLABORATEUR)
            .telephoneCollaborateur(UPDATED_TELEPHONE_COLLABORATEUR)
            .cinCollaborateur(UPDATED_CIN_COLLABORATEUR)
            .adresseCollaborateur(UPDATED_ADRESSE_COLLABORATEUR)
            .villeCollaborateur(UPDATED_VILLE_COLLABORATEUR);
        return collaborateur;
    }

    @BeforeEach
    public void initTest() {
        collaborateur = createEntity(em);
    }

    @Test
    @Transactional
    void createCollaborateur() throws Exception {
        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();
        // Create the Collaborateur
        restCollaborateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isCreated());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getCollaborateurID()).isEqualTo(DEFAULT_COLLABORATEUR_ID);
        assertThat(testCollaborateur.getNomCollaborateur()).isEqualTo(DEFAULT_NOM_COLLABORATEUR);
        assertThat(testCollaborateur.getPrenomCollaborateur()).isEqualTo(DEFAULT_PRENOM_COLLABORATEUR);
        assertThat(testCollaborateur.getEmailCollaborateur()).isEqualTo(DEFAULT_EMAIL_COLLABORATEUR);
        assertThat(testCollaborateur.getTelephoneCollaborateur()).isEqualTo(DEFAULT_TELEPHONE_COLLABORATEUR);
        assertThat(testCollaborateur.getCinCollaborateur()).isEqualTo(DEFAULT_CIN_COLLABORATEUR);
        assertThat(testCollaborateur.getAdresseCollaborateur()).isEqualTo(DEFAULT_ADRESSE_COLLABORATEUR);
        assertThat(testCollaborateur.getVilleCollaborateur()).isEqualTo(DEFAULT_VILLE_COLLABORATEUR);
    }

    @Test
    @Transactional
    void createCollaborateurWithExistingId() throws Exception {
        // Create the Collaborateur with an existing ID
        collaborateur.setId(1L);

        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaborateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCollaborateurs() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        // Get all the collaborateurList
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].collaborateurID").value(hasItem(DEFAULT_COLLABORATEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].nomCollaborateur").value(hasItem(DEFAULT_NOM_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].prenomCollaborateur").value(hasItem(DEFAULT_PRENOM_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].emailCollaborateur").value(hasItem(DEFAULT_EMAIL_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].telephoneCollaborateur").value(hasItem(DEFAULT_TELEPHONE_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].cinCollaborateur").value(hasItem(DEFAULT_CIN_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].adresseCollaborateur").value(hasItem(DEFAULT_ADRESSE_COLLABORATEUR)))
            .andExpect(jsonPath("$.[*].villeCollaborateur").value(hasItem(DEFAULT_VILLE_COLLABORATEUR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsEnabled() throws Exception {
        when(collaborateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collaborateurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collaborateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collaborateurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        // Get the collaborateur
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL_ID, collaborateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaborateur.getId().intValue()))
            .andExpect(jsonPath("$.collaborateurID").value(DEFAULT_COLLABORATEUR_ID.intValue()))
            .andExpect(jsonPath("$.nomCollaborateur").value(DEFAULT_NOM_COLLABORATEUR))
            .andExpect(jsonPath("$.prenomCollaborateur").value(DEFAULT_PRENOM_COLLABORATEUR))
            .andExpect(jsonPath("$.emailCollaborateur").value(DEFAULT_EMAIL_COLLABORATEUR))
            .andExpect(jsonPath("$.telephoneCollaborateur").value(DEFAULT_TELEPHONE_COLLABORATEUR))
            .andExpect(jsonPath("$.cinCollaborateur").value(DEFAULT_CIN_COLLABORATEUR))
            .andExpect(jsonPath("$.adresseCollaborateur").value(DEFAULT_ADRESSE_COLLABORATEUR))
            .andExpect(jsonPath("$.villeCollaborateur").value(DEFAULT_VILLE_COLLABORATEUR));
    }

    @Test
    @Transactional
    void getNonExistingCollaborateur() throws Exception {
        // Get the collaborateur
        restCollaborateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur
        Collaborateur updatedCollaborateur = collaborateurRepository.findById(collaborateur.getId()).get();
        // Disconnect from session so that the updates on updatedCollaborateur are not directly saved in db
        em.detach(updatedCollaborateur);
        updatedCollaborateur
            .collaborateurID(UPDATED_COLLABORATEUR_ID)
            .nomCollaborateur(UPDATED_NOM_COLLABORATEUR)
            .prenomCollaborateur(UPDATED_PRENOM_COLLABORATEUR)
            .emailCollaborateur(UPDATED_EMAIL_COLLABORATEUR)
            .telephoneCollaborateur(UPDATED_TELEPHONE_COLLABORATEUR)
            .cinCollaborateur(UPDATED_CIN_COLLABORATEUR)
            .adresseCollaborateur(UPDATED_ADRESSE_COLLABORATEUR)
            .villeCollaborateur(UPDATED_VILLE_COLLABORATEUR);

        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollaborateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getCollaborateurID()).isEqualTo(UPDATED_COLLABORATEUR_ID);
        assertThat(testCollaborateur.getNomCollaborateur()).isEqualTo(UPDATED_NOM_COLLABORATEUR);
        assertThat(testCollaborateur.getPrenomCollaborateur()).isEqualTo(UPDATED_PRENOM_COLLABORATEUR);
        assertThat(testCollaborateur.getEmailCollaborateur()).isEqualTo(UPDATED_EMAIL_COLLABORATEUR);
        assertThat(testCollaborateur.getTelephoneCollaborateur()).isEqualTo(UPDATED_TELEPHONE_COLLABORATEUR);
        assertThat(testCollaborateur.getCinCollaborateur()).isEqualTo(UPDATED_CIN_COLLABORATEUR);
        assertThat(testCollaborateur.getAdresseCollaborateur()).isEqualTo(UPDATED_ADRESSE_COLLABORATEUR);
        assertThat(testCollaborateur.getVilleCollaborateur()).isEqualTo(UPDATED_VILLE_COLLABORATEUR);
    }

    @Test
    @Transactional
    void putNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collaborateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur.collaborateurID(UPDATED_COLLABORATEUR_ID);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getCollaborateurID()).isEqualTo(UPDATED_COLLABORATEUR_ID);
        assertThat(testCollaborateur.getNomCollaborateur()).isEqualTo(DEFAULT_NOM_COLLABORATEUR);
        assertThat(testCollaborateur.getPrenomCollaborateur()).isEqualTo(DEFAULT_PRENOM_COLLABORATEUR);
        assertThat(testCollaborateur.getEmailCollaborateur()).isEqualTo(DEFAULT_EMAIL_COLLABORATEUR);
        assertThat(testCollaborateur.getTelephoneCollaborateur()).isEqualTo(DEFAULT_TELEPHONE_COLLABORATEUR);
        assertThat(testCollaborateur.getCinCollaborateur()).isEqualTo(DEFAULT_CIN_COLLABORATEUR);
        assertThat(testCollaborateur.getAdresseCollaborateur()).isEqualTo(DEFAULT_ADRESSE_COLLABORATEUR);
        assertThat(testCollaborateur.getVilleCollaborateur()).isEqualTo(DEFAULT_VILLE_COLLABORATEUR);
    }

    @Test
    @Transactional
    void fullUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur
            .collaborateurID(UPDATED_COLLABORATEUR_ID)
            .nomCollaborateur(UPDATED_NOM_COLLABORATEUR)
            .prenomCollaborateur(UPDATED_PRENOM_COLLABORATEUR)
            .emailCollaborateur(UPDATED_EMAIL_COLLABORATEUR)
            .telephoneCollaborateur(UPDATED_TELEPHONE_COLLABORATEUR)
            .cinCollaborateur(UPDATED_CIN_COLLABORATEUR)
            .adresseCollaborateur(UPDATED_ADRESSE_COLLABORATEUR)
            .villeCollaborateur(UPDATED_VILLE_COLLABORATEUR);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getCollaborateurID()).isEqualTo(UPDATED_COLLABORATEUR_ID);
        assertThat(testCollaborateur.getNomCollaborateur()).isEqualTo(UPDATED_NOM_COLLABORATEUR);
        assertThat(testCollaborateur.getPrenomCollaborateur()).isEqualTo(UPDATED_PRENOM_COLLABORATEUR);
        assertThat(testCollaborateur.getEmailCollaborateur()).isEqualTo(UPDATED_EMAIL_COLLABORATEUR);
        assertThat(testCollaborateur.getTelephoneCollaborateur()).isEqualTo(UPDATED_TELEPHONE_COLLABORATEUR);
        assertThat(testCollaborateur.getCinCollaborateur()).isEqualTo(UPDATED_CIN_COLLABORATEUR);
        assertThat(testCollaborateur.getAdresseCollaborateur()).isEqualTo(UPDATED_ADRESSE_COLLABORATEUR);
        assertThat(testCollaborateur.getVilleCollaborateur()).isEqualTo(UPDATED_VILLE_COLLABORATEUR);
    }

    @Test
    @Transactional
    void patchNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collaborateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collaborateur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        int databaseSizeBeforeDelete = collaborateurRepository.findAll().size();

        // Delete the collaborateur
        restCollaborateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, collaborateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
