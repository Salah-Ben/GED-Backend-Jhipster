package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.Entreprise;
import com.icb.ged.repository.EntrepriseRepository;
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
 * Integration tests for the {@link EntrepriseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EntrepriseResourceIT {

    private static final Long DEFAULT_ENTERPRISE_ID = 1L;
    private static final Long UPDATED_ENTERPRISE_ID = 2L;

    private static final String DEFAULT_NOM_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IDENTIFICATION_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTIFICATION_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CABINET = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITE_CABINET = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE_CABINET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entreprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private EntrepriseRepository entrepriseRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .enterpriseID(DEFAULT_ENTERPRISE_ID)
            .nomCabinet(DEFAULT_NOM_CABINET)
            .numeroIdentificationCabinet(DEFAULT_NUMERO_IDENTIFICATION_CABINET)
            .emailCabinet(DEFAULT_EMAIL_CABINET)
            .adresseCabinet(DEFAULT_ADRESSE_CABINET)
            .telephoneCabinet(DEFAULT_TELEPHONE_CABINET)
            .villeCabinet(DEFAULT_VILLE_CABINET)
            .activiteCabinet(DEFAULT_ACTIVITE_CABINET);
        return entreprise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createUpdatedEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .enterpriseID(UPDATED_ENTERPRISE_ID)
            .nomCabinet(UPDATED_NOM_CABINET)
            .numeroIdentificationCabinet(UPDATED_NUMERO_IDENTIFICATION_CABINET)
            .emailCabinet(UPDATED_EMAIL_CABINET)
            .adresseCabinet(UPDATED_ADRESSE_CABINET)
            .telephoneCabinet(UPDATED_TELEPHONE_CABINET)
            .villeCabinet(UPDATED_VILLE_CABINET)
            .activiteCabinet(UPDATED_ACTIVITE_CABINET);
        return entreprise;
    }

    @BeforeEach
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();
        // Create the Entreprise
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getEnterpriseID()).isEqualTo(DEFAULT_ENTERPRISE_ID);
        assertThat(testEntreprise.getNomCabinet()).isEqualTo(DEFAULT_NOM_CABINET);
        assertThat(testEntreprise.getNumeroIdentificationCabinet()).isEqualTo(DEFAULT_NUMERO_IDENTIFICATION_CABINET);
        assertThat(testEntreprise.getEmailCabinet()).isEqualTo(DEFAULT_EMAIL_CABINET);
        assertThat(testEntreprise.getAdresseCabinet()).isEqualTo(DEFAULT_ADRESSE_CABINET);
        assertThat(testEntreprise.getTelephoneCabinet()).isEqualTo(DEFAULT_TELEPHONE_CABINET);
        assertThat(testEntreprise.getVilleCabinet()).isEqualTo(DEFAULT_VILLE_CABINET);
        assertThat(testEntreprise.getActiviteCabinet()).isEqualTo(DEFAULT_ACTIVITE_CABINET);
    }

    @Test
    @Transactional
    void createEntrepriseWithExistingId() throws Exception {
        // Create the Entreprise with an existing ID
        entreprise.setId(1L);

        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entrepriseList
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].enterpriseID").value(hasItem(DEFAULT_ENTERPRISE_ID.intValue())))
            .andExpect(jsonPath("$.[*].nomCabinet").value(hasItem(DEFAULT_NOM_CABINET)))
            .andExpect(jsonPath("$.[*].numeroIdentificationCabinet").value(hasItem(DEFAULT_NUMERO_IDENTIFICATION_CABINET)))
            .andExpect(jsonPath("$.[*].emailCabinet").value(hasItem(DEFAULT_EMAIL_CABINET)))
            .andExpect(jsonPath("$.[*].adresseCabinet").value(hasItem(DEFAULT_ADRESSE_CABINET)))
            .andExpect(jsonPath("$.[*].telephoneCabinet").value(hasItem(DEFAULT_TELEPHONE_CABINET)))
            .andExpect(jsonPath("$.[*].villeCabinet").value(hasItem(DEFAULT_VILLE_CABINET)))
            .andExpect(jsonPath("$.[*].activiteCabinet").value(hasItem(DEFAULT_ACTIVITE_CABINET)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntreprisesWithEagerRelationshipsIsEnabled() throws Exception {
        when(entrepriseRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntrepriseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(entrepriseRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntreprisesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(entrepriseRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntrepriseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(entrepriseRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL_ID, entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.enterpriseID").value(DEFAULT_ENTERPRISE_ID.intValue()))
            .andExpect(jsonPath("$.nomCabinet").value(DEFAULT_NOM_CABINET))
            .andExpect(jsonPath("$.numeroIdentificationCabinet").value(DEFAULT_NUMERO_IDENTIFICATION_CABINET))
            .andExpect(jsonPath("$.emailCabinet").value(DEFAULT_EMAIL_CABINET))
            .andExpect(jsonPath("$.adresseCabinet").value(DEFAULT_ADRESSE_CABINET))
            .andExpect(jsonPath("$.telephoneCabinet").value(DEFAULT_TELEPHONE_CABINET))
            .andExpect(jsonPath("$.villeCabinet").value(DEFAULT_VILLE_CABINET))
            .andExpect(jsonPath("$.activiteCabinet").value(DEFAULT_ACTIVITE_CABINET));
    }

    @Test
    @Transactional
    void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findById(entreprise.getId()).get();
        // Disconnect from session so that the updates on updatedEntreprise are not directly saved in db
        em.detach(updatedEntreprise);
        updatedEntreprise
            .enterpriseID(UPDATED_ENTERPRISE_ID)
            .nomCabinet(UPDATED_NOM_CABINET)
            .numeroIdentificationCabinet(UPDATED_NUMERO_IDENTIFICATION_CABINET)
            .emailCabinet(UPDATED_EMAIL_CABINET)
            .adresseCabinet(UPDATED_ADRESSE_CABINET)
            .telephoneCabinet(UPDATED_TELEPHONE_CABINET)
            .villeCabinet(UPDATED_VILLE_CABINET)
            .activiteCabinet(UPDATED_ACTIVITE_CABINET);

        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getEnterpriseID()).isEqualTo(UPDATED_ENTERPRISE_ID);
        assertThat(testEntreprise.getNomCabinet()).isEqualTo(UPDATED_NOM_CABINET);
        assertThat(testEntreprise.getNumeroIdentificationCabinet()).isEqualTo(UPDATED_NUMERO_IDENTIFICATION_CABINET);
        assertThat(testEntreprise.getEmailCabinet()).isEqualTo(UPDATED_EMAIL_CABINET);
        assertThat(testEntreprise.getAdresseCabinet()).isEqualTo(UPDATED_ADRESSE_CABINET);
        assertThat(testEntreprise.getTelephoneCabinet()).isEqualTo(UPDATED_TELEPHONE_CABINET);
        assertThat(testEntreprise.getVilleCabinet()).isEqualTo(UPDATED_VILLE_CABINET);
        assertThat(testEntreprise.getActiviteCabinet()).isEqualTo(UPDATED_ACTIVITE_CABINET);
    }

    @Test
    @Transactional
    void putNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .enterpriseID(UPDATED_ENTERPRISE_ID)
            .nomCabinet(UPDATED_NOM_CABINET)
            .numeroIdentificationCabinet(UPDATED_NUMERO_IDENTIFICATION_CABINET)
            .villeCabinet(UPDATED_VILLE_CABINET);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getEnterpriseID()).isEqualTo(UPDATED_ENTERPRISE_ID);
        assertThat(testEntreprise.getNomCabinet()).isEqualTo(UPDATED_NOM_CABINET);
        assertThat(testEntreprise.getNumeroIdentificationCabinet()).isEqualTo(UPDATED_NUMERO_IDENTIFICATION_CABINET);
        assertThat(testEntreprise.getEmailCabinet()).isEqualTo(DEFAULT_EMAIL_CABINET);
        assertThat(testEntreprise.getAdresseCabinet()).isEqualTo(DEFAULT_ADRESSE_CABINET);
        assertThat(testEntreprise.getTelephoneCabinet()).isEqualTo(DEFAULT_TELEPHONE_CABINET);
        assertThat(testEntreprise.getVilleCabinet()).isEqualTo(UPDATED_VILLE_CABINET);
        assertThat(testEntreprise.getActiviteCabinet()).isEqualTo(DEFAULT_ACTIVITE_CABINET);
    }

    @Test
    @Transactional
    void fullUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .enterpriseID(UPDATED_ENTERPRISE_ID)
            .nomCabinet(UPDATED_NOM_CABINET)
            .numeroIdentificationCabinet(UPDATED_NUMERO_IDENTIFICATION_CABINET)
            .emailCabinet(UPDATED_EMAIL_CABINET)
            .adresseCabinet(UPDATED_ADRESSE_CABINET)
            .telephoneCabinet(UPDATED_TELEPHONE_CABINET)
            .villeCabinet(UPDATED_VILLE_CABINET)
            .activiteCabinet(UPDATED_ACTIVITE_CABINET);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getEnterpriseID()).isEqualTo(UPDATED_ENTERPRISE_ID);
        assertThat(testEntreprise.getNomCabinet()).isEqualTo(UPDATED_NOM_CABINET);
        assertThat(testEntreprise.getNumeroIdentificationCabinet()).isEqualTo(UPDATED_NUMERO_IDENTIFICATION_CABINET);
        assertThat(testEntreprise.getEmailCabinet()).isEqualTo(UPDATED_EMAIL_CABINET);
        assertThat(testEntreprise.getAdresseCabinet()).isEqualTo(UPDATED_ADRESSE_CABINET);
        assertThat(testEntreprise.getTelephoneCabinet()).isEqualTo(UPDATED_TELEPHONE_CABINET);
        assertThat(testEntreprise.getVilleCabinet()).isEqualTo(UPDATED_VILLE_CABINET);
        assertThat(testEntreprise.getActiviteCabinet()).isEqualTo(UPDATED_ACTIVITE_CABINET);
    }

    @Test
    @Transactional
    void patchNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Delete the entreprise
        restEntrepriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, entreprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
