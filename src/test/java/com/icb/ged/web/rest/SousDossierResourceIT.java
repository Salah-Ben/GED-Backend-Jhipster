package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.SousDossier;
import com.icb.ged.repository.SousDossierRepository;
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
 * Integration tests for the {@link SousDossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SousDossierResourceIT {

    private static final String DEFAULT_QRCODE = "AAAAAAAAAA";
    private static final String UPDATED_QRCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sous-dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SousDossierRepository sousDossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousDossierMockMvc;

    private SousDossier sousDossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousDossier createEntity(EntityManager em) {
        SousDossier sousDossier = new SousDossier().qrcode(DEFAULT_QRCODE).titre(DEFAULT_TITRE);
        return sousDossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousDossier createUpdatedEntity(EntityManager em) {
        SousDossier sousDossier = new SousDossier().qrcode(UPDATED_QRCODE).titre(UPDATED_TITRE);
        return sousDossier;
    }

    @BeforeEach
    public void initTest() {
        sousDossier = createEntity(em);
    }

    @Test
    @Transactional
    void createSousDossier() throws Exception {
        int databaseSizeBeforeCreate = sousDossierRepository.findAll().size();
        // Create the SousDossier
        restSousDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousDossier)))
            .andExpect(status().isCreated());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeCreate + 1);
        SousDossier testSousDossier = sousDossierList.get(sousDossierList.size() - 1);
        assertThat(testSousDossier.getQrcode()).isEqualTo(DEFAULT_QRCODE);
        assertThat(testSousDossier.getTitre()).isEqualTo(DEFAULT_TITRE);
    }

    @Test
    @Transactional
    void createSousDossierWithExistingId() throws Exception {
        // Create the SousDossier with an existing ID
        sousDossier.setId(1L);

        int databaseSizeBeforeCreate = sousDossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousDossier)))
            .andExpect(status().isBadRequest());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSousDossiers() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        // Get all the sousDossierList
        restSousDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].qrcode").value(hasItem(DEFAULT_QRCODE)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)));
    }

    @Test
    @Transactional
    void getSousDossier() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        // Get the sousDossier
        restSousDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, sousDossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousDossier.getId().intValue()))
            .andExpect(jsonPath("$.qrcode").value(DEFAULT_QRCODE))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE));
    }

    @Test
    @Transactional
    void getNonExistingSousDossier() throws Exception {
        // Get the sousDossier
        restSousDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSousDossier() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();

        // Update the sousDossier
        SousDossier updatedSousDossier = sousDossierRepository.findById(sousDossier.getId()).get();
        // Disconnect from session so that the updates on updatedSousDossier are not directly saved in db
        em.detach(updatedSousDossier);
        updatedSousDossier.qrcode(UPDATED_QRCODE).titre(UPDATED_TITRE);

        restSousDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSousDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSousDossier))
            )
            .andExpect(status().isOk());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
        SousDossier testSousDossier = sousDossierList.get(sousDossierList.size() - 1);
        assertThat(testSousDossier.getQrcode()).isEqualTo(UPDATED_QRCODE);
        assertThat(testSousDossier.getTitre()).isEqualTo(UPDATED_TITRE);
    }

    @Test
    @Transactional
    void putNonExistingSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sousDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousDossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSousDossierWithPatch() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();

        // Update the sousDossier using partial update
        SousDossier partialUpdatedSousDossier = new SousDossier();
        partialUpdatedSousDossier.setId(sousDossier.getId());

        restSousDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousDossier))
            )
            .andExpect(status().isOk());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
        SousDossier testSousDossier = sousDossierList.get(sousDossierList.size() - 1);
        assertThat(testSousDossier.getQrcode()).isEqualTo(DEFAULT_QRCODE);
        assertThat(testSousDossier.getTitre()).isEqualTo(DEFAULT_TITRE);
    }

    @Test
    @Transactional
    void fullUpdateSousDossierWithPatch() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();

        // Update the sousDossier using partial update
        SousDossier partialUpdatedSousDossier = new SousDossier();
        partialUpdatedSousDossier.setId(sousDossier.getId());

        partialUpdatedSousDossier.qrcode(UPDATED_QRCODE).titre(UPDATED_TITRE);

        restSousDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousDossier))
            )
            .andExpect(status().isOk());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
        SousDossier testSousDossier = sousDossierList.get(sousDossierList.size() - 1);
        assertThat(testSousDossier.getQrcode()).isEqualTo(UPDATED_QRCODE);
        assertThat(testSousDossier.getTitre()).isEqualTo(UPDATED_TITRE);
    }

    @Test
    @Transactional
    void patchNonExistingSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sousDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSousDossier() throws Exception {
        int databaseSizeBeforeUpdate = sousDossierRepository.findAll().size();
        sousDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousDossierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sousDossier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousDossier in the database
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSousDossier() throws Exception {
        // Initialize the database
        sousDossierRepository.saveAndFlush(sousDossier);

        int databaseSizeBeforeDelete = sousDossierRepository.findAll().size();

        // Delete the sousDossier
        restSousDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, sousDossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousDossier> sousDossierList = sousDossierRepository.findAll();
        assertThat(sousDossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
