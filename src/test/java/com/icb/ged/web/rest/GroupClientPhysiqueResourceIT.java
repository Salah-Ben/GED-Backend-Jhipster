package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.GroupClientPhysique;
import com.icb.ged.repository.GroupClientPhysiqueRepository;
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
 * Integration tests for the {@link GroupClientPhysiqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupClientPhysiqueResourceIT {

    private static final String DEFAULT_NOM_GROUPE_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GROUPE_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_GROUPE_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_GROUPE_PHYSIQUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_PERSONNE_PHYSIQUE = 1;
    private static final Integer UPDATED_NOMBRE_PERSONNE_PHYSIQUE = 2;

    private static final String ENTITY_API_URL = "/api/group-client-physiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupClientPhysiqueRepository groupClientPhysiqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupClientPhysiqueMockMvc;

    private GroupClientPhysique groupClientPhysique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupClientPhysique createEntity(EntityManager em) {
        GroupClientPhysique groupClientPhysique = new GroupClientPhysique()
            .nomGroupePhysique(DEFAULT_NOM_GROUPE_PHYSIQUE)
            .lienGroupePhysique(DEFAULT_LIEN_GROUPE_PHYSIQUE)
            .nombrePersonnePhysique(DEFAULT_NOMBRE_PERSONNE_PHYSIQUE);
        return groupClientPhysique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupClientPhysique createUpdatedEntity(EntityManager em) {
        GroupClientPhysique groupClientPhysique = new GroupClientPhysique()
            .nomGroupePhysique(UPDATED_NOM_GROUPE_PHYSIQUE)
            .lienGroupePhysique(UPDATED_LIEN_GROUPE_PHYSIQUE)
            .nombrePersonnePhysique(UPDATED_NOMBRE_PERSONNE_PHYSIQUE);
        return groupClientPhysique;
    }

    @BeforeEach
    public void initTest() {
        groupClientPhysique = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupClientPhysique() throws Exception {
        int databaseSizeBeforeCreate = groupClientPhysiqueRepository.findAll().size();
        // Create the GroupClientPhysique
        restGroupClientPhysiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isCreated());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeCreate + 1);
        GroupClientPhysique testGroupClientPhysique = groupClientPhysiqueList.get(groupClientPhysiqueList.size() - 1);
        assertThat(testGroupClientPhysique.getNomGroupePhysique()).isEqualTo(DEFAULT_NOM_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getLienGroupePhysique()).isEqualTo(DEFAULT_LIEN_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getNombrePersonnePhysique()).isEqualTo(DEFAULT_NOMBRE_PERSONNE_PHYSIQUE);
    }

    @Test
    @Transactional
    void createGroupClientPhysiqueWithExistingId() throws Exception {
        // Create the GroupClientPhysique with an existing ID
        groupClientPhysique.setId(1L);

        int databaseSizeBeforeCreate = groupClientPhysiqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupClientPhysiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroupClientPhysiques() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        // Get all the groupClientPhysiqueList
        restGroupClientPhysiqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupClientPhysique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomGroupePhysique").value(hasItem(DEFAULT_NOM_GROUPE_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].lienGroupePhysique").value(hasItem(DEFAULT_LIEN_GROUPE_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].nombrePersonnePhysique").value(hasItem(DEFAULT_NOMBRE_PERSONNE_PHYSIQUE)));
    }

    @Test
    @Transactional
    void getGroupClientPhysique() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        // Get the groupClientPhysique
        restGroupClientPhysiqueMockMvc
            .perform(get(ENTITY_API_URL_ID, groupClientPhysique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupClientPhysique.getId().intValue()))
            .andExpect(jsonPath("$.nomGroupePhysique").value(DEFAULT_NOM_GROUPE_PHYSIQUE))
            .andExpect(jsonPath("$.lienGroupePhysique").value(DEFAULT_LIEN_GROUPE_PHYSIQUE))
            .andExpect(jsonPath("$.nombrePersonnePhysique").value(DEFAULT_NOMBRE_PERSONNE_PHYSIQUE));
    }

    @Test
    @Transactional
    void getNonExistingGroupClientPhysique() throws Exception {
        // Get the groupClientPhysique
        restGroupClientPhysiqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGroupClientPhysique() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();

        // Update the groupClientPhysique
        GroupClientPhysique updatedGroupClientPhysique = groupClientPhysiqueRepository.findById(groupClientPhysique.getId()).get();
        // Disconnect from session so that the updates on updatedGroupClientPhysique are not directly saved in db
        em.detach(updatedGroupClientPhysique);
        updatedGroupClientPhysique
            .nomGroupePhysique(UPDATED_NOM_GROUPE_PHYSIQUE)
            .lienGroupePhysique(UPDATED_LIEN_GROUPE_PHYSIQUE)
            .nombrePersonnePhysique(UPDATED_NOMBRE_PERSONNE_PHYSIQUE);

        restGroupClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroupClientPhysique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGroupClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        GroupClientPhysique testGroupClientPhysique = groupClientPhysiqueList.get(groupClientPhysiqueList.size() - 1);
        assertThat(testGroupClientPhysique.getNomGroupePhysique()).isEqualTo(UPDATED_NOM_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getLienGroupePhysique()).isEqualTo(UPDATED_LIEN_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getNombrePersonnePhysique()).isEqualTo(UPDATED_NOMBRE_PERSONNE_PHYSIQUE);
    }

    @Test
    @Transactional
    void putNonExistingGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupClientPhysique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupClientPhysiqueWithPatch() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();

        // Update the groupClientPhysique using partial update
        GroupClientPhysique partialUpdatedGroupClientPhysique = new GroupClientPhysique();
        partialUpdatedGroupClientPhysique.setId(groupClientPhysique.getId());

        partialUpdatedGroupClientPhysique.lienGroupePhysique(UPDATED_LIEN_GROUPE_PHYSIQUE);

        restGroupClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupClientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        GroupClientPhysique testGroupClientPhysique = groupClientPhysiqueList.get(groupClientPhysiqueList.size() - 1);
        assertThat(testGroupClientPhysique.getNomGroupePhysique()).isEqualTo(DEFAULT_NOM_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getLienGroupePhysique()).isEqualTo(UPDATED_LIEN_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getNombrePersonnePhysique()).isEqualTo(DEFAULT_NOMBRE_PERSONNE_PHYSIQUE);
    }

    @Test
    @Transactional
    void fullUpdateGroupClientPhysiqueWithPatch() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();

        // Update the groupClientPhysique using partial update
        GroupClientPhysique partialUpdatedGroupClientPhysique = new GroupClientPhysique();
        partialUpdatedGroupClientPhysique.setId(groupClientPhysique.getId());

        partialUpdatedGroupClientPhysique
            .nomGroupePhysique(UPDATED_NOM_GROUPE_PHYSIQUE)
            .lienGroupePhysique(UPDATED_LIEN_GROUPE_PHYSIQUE)
            .nombrePersonnePhysique(UPDATED_NOMBRE_PERSONNE_PHYSIQUE);

        restGroupClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupClientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupClientPhysique))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
        GroupClientPhysique testGroupClientPhysique = groupClientPhysiqueList.get(groupClientPhysiqueList.size() - 1);
        assertThat(testGroupClientPhysique.getNomGroupePhysique()).isEqualTo(UPDATED_NOM_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getLienGroupePhysique()).isEqualTo(UPDATED_LIEN_GROUPE_PHYSIQUE);
        assertThat(testGroupClientPhysique.getNombrePersonnePhysique()).isEqualTo(UPDATED_NOMBRE_PERSONNE_PHYSIQUE);
    }

    @Test
    @Transactional
    void patchNonExistingGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupClientPhysique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupClientPhysique() throws Exception {
        int databaseSizeBeforeUpdate = groupClientPhysiqueRepository.findAll().size();
        groupClientPhysique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientPhysiqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientPhysique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupClientPhysique in the database
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroupClientPhysique() throws Exception {
        // Initialize the database
        groupClientPhysiqueRepository.saveAndFlush(groupClientPhysique);

        int databaseSizeBeforeDelete = groupClientPhysiqueRepository.findAll().size();

        // Delete the groupClientPhysique
        restGroupClientPhysiqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupClientPhysique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupClientPhysique> groupClientPhysiqueList = groupClientPhysiqueRepository.findAll();
        assertThat(groupClientPhysiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
