package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.Dossier;
import com.icb.ged.repository.DossierRepository;
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
 * Integration tests for the {@link DossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DossierResourceIT {

    private static final Long DEFAULT_DOSSIER_ID = 1L;
    private static final Long UPDATED_DOSSIER_ID = 2L;

    private static final String DEFAULT_TITRE_DOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_DOSSIER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DOSSIER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLACEMENT_DOSSIER_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_QR_CODE_DOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_QR_CODE_DOSSIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .dossierID(DEFAULT_DOSSIER_ID)
            .titreDossier(DEFAULT_TITRE_DOSSIER)
            .typeDossier(DEFAULT_TYPE_DOSSIER)
            .typeService(DEFAULT_TYPE_SERVICE)
            .emplacementDossierPhysique(DEFAULT_EMPLACEMENT_DOSSIER_PHYSIQUE)
            .qrCodeDossier(DEFAULT_QR_CODE_DOSSIER);
        return dossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createUpdatedEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .dossierID(UPDATED_DOSSIER_ID)
            .titreDossier(UPDATED_TITRE_DOSSIER)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .typeService(UPDATED_TYPE_SERVICE)
            .emplacementDossierPhysique(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE)
            .qrCodeDossier(UPDATED_QR_CODE_DOSSIER);
        return dossier;
    }

    @BeforeEach
    public void initTest() {
        dossier = createEntity(em);
    }

    @Test
    @Transactional
    void createDossier() throws Exception {
        int databaseSizeBeforeCreate = dossierRepository.findAll().size();
        // Create the Dossier
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isCreated());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate + 1);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getDossierID()).isEqualTo(DEFAULT_DOSSIER_ID);
        assertThat(testDossier.getTitreDossier()).isEqualTo(DEFAULT_TITRE_DOSSIER);
        assertThat(testDossier.getTypeDossier()).isEqualTo(DEFAULT_TYPE_DOSSIER);
        assertThat(testDossier.getTypeService()).isEqualTo(DEFAULT_TYPE_SERVICE);
        assertThat(testDossier.getEmplacementDossierPhysique()).isEqualTo(DEFAULT_EMPLACEMENT_DOSSIER_PHYSIQUE);
        assertThat(testDossier.getQrCodeDossier()).isEqualTo(DEFAULT_QR_CODE_DOSSIER);
    }

    @Test
    @Transactional
    void createDossierWithExistingId() throws Exception {
        // Create the Dossier with an existing ID
        dossier.setId(1L);

        int databaseSizeBeforeCreate = dossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDossiers() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].dossierID").value(hasItem(DEFAULT_DOSSIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].titreDossier").value(hasItem(DEFAULT_TITRE_DOSSIER)))
            .andExpect(jsonPath("$.[*].typeDossier").value(hasItem(DEFAULT_TYPE_DOSSIER)))
            .andExpect(jsonPath("$.[*].typeService").value(hasItem(DEFAULT_TYPE_SERVICE)))
            .andExpect(jsonPath("$.[*].emplacementDossierPhysique").value(hasItem(DEFAULT_EMPLACEMENT_DOSSIER_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].qrCodeDossier").value(hasItem(DEFAULT_QR_CODE_DOSSIER)));
    }

    @Test
    @Transactional
    void getDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.dossierID").value(DEFAULT_DOSSIER_ID.intValue()))
            .andExpect(jsonPath("$.titreDossier").value(DEFAULT_TITRE_DOSSIER))
            .andExpect(jsonPath("$.typeDossier").value(DEFAULT_TYPE_DOSSIER))
            .andExpect(jsonPath("$.typeService").value(DEFAULT_TYPE_SERVICE))
            .andExpect(jsonPath("$.emplacementDossierPhysique").value(DEFAULT_EMPLACEMENT_DOSSIER_PHYSIQUE))
            .andExpect(jsonPath("$.qrCodeDossier").value(DEFAULT_QR_CODE_DOSSIER));
    }

    @Test
    @Transactional
    void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findById(dossier.getId()).get();
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .dossierID(UPDATED_DOSSIER_ID)
            .titreDossier(UPDATED_TITRE_DOSSIER)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .typeService(UPDATED_TYPE_SERVICE)
            .emplacementDossierPhysique(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE)
            .qrCodeDossier(UPDATED_QR_CODE_DOSSIER);

        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getDossierID()).isEqualTo(UPDATED_DOSSIER_ID);
        assertThat(testDossier.getTitreDossier()).isEqualTo(UPDATED_TITRE_DOSSIER);
        assertThat(testDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDossier.getTypeService()).isEqualTo(UPDATED_TYPE_SERVICE);
        assertThat(testDossier.getEmplacementDossierPhysique()).isEqualTo(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE);
        assertThat(testDossier.getQrCodeDossier()).isEqualTo(UPDATED_QR_CODE_DOSSIER);
    }

    @Test
    @Transactional
    void putNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier.typeDossier(UPDATED_TYPE_DOSSIER).emplacementDossierPhysique(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getDossierID()).isEqualTo(DEFAULT_DOSSIER_ID);
        assertThat(testDossier.getTitreDossier()).isEqualTo(DEFAULT_TITRE_DOSSIER);
        assertThat(testDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDossier.getTypeService()).isEqualTo(DEFAULT_TYPE_SERVICE);
        assertThat(testDossier.getEmplacementDossierPhysique()).isEqualTo(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE);
        assertThat(testDossier.getQrCodeDossier()).isEqualTo(DEFAULT_QR_CODE_DOSSIER);
    }

    @Test
    @Transactional
    void fullUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .dossierID(UPDATED_DOSSIER_ID)
            .titreDossier(UPDATED_TITRE_DOSSIER)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .typeService(UPDATED_TYPE_SERVICE)
            .emplacementDossierPhysique(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE)
            .qrCodeDossier(UPDATED_QR_CODE_DOSSIER);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getDossierID()).isEqualTo(UPDATED_DOSSIER_ID);
        assertThat(testDossier.getTitreDossier()).isEqualTo(UPDATED_TITRE_DOSSIER);
        assertThat(testDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDossier.getTypeService()).isEqualTo(UPDATED_TYPE_SERVICE);
        assertThat(testDossier.getEmplacementDossierPhysique()).isEqualTo(UPDATED_EMPLACEMENT_DOSSIER_PHYSIQUE);
        assertThat(testDossier.getQrCodeDossier()).isEqualTo(UPDATED_QR_CODE_DOSSIER);
    }

    @Test
    @Transactional
    void patchNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeDelete = dossierRepository.findAll().size();

        // Delete the dossier
        restDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
