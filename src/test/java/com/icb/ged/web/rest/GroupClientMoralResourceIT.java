package com.icb.ged.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.icb.ged.IntegrationTest;
import com.icb.ged.domain.GroupClientMoral;
import com.icb.ged.repository.GroupClientMoralRepository;
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
 * Integration tests for the {@link GroupClientMoralResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupClientMoralResourceIT {

    private static final String DEFAULT_NOM_GROUPE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GROUPE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_GROUPE_CLIENT_MORAL = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_GROUPE_CLIENT_MORAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_CLIENT_MORAL = 1;
    private static final Integer UPDATED_NOMBRE_CLIENT_MORAL = 2;

    private static final String ENTITY_API_URL = "/api/group-client-morals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupClientMoralRepository groupClientMoralRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupClientMoralMockMvc;

    private GroupClientMoral groupClientMoral;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupClientMoral createEntity(EntityManager em) {
        GroupClientMoral groupClientMoral = new GroupClientMoral()
            .nomGroupeClientMoral(DEFAULT_NOM_GROUPE_CLIENT_MORAL)
            .lienGroupeClientMoral(DEFAULT_LIEN_GROUPE_CLIENT_MORAL)
            .nombreClientMoral(DEFAULT_NOMBRE_CLIENT_MORAL);
        return groupClientMoral;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupClientMoral createUpdatedEntity(EntityManager em) {
        GroupClientMoral groupClientMoral = new GroupClientMoral()
            .nomGroupeClientMoral(UPDATED_NOM_GROUPE_CLIENT_MORAL)
            .lienGroupeClientMoral(UPDATED_LIEN_GROUPE_CLIENT_MORAL)
            .nombreClientMoral(UPDATED_NOMBRE_CLIENT_MORAL);
        return groupClientMoral;
    }

    @BeforeEach
    public void initTest() {
        groupClientMoral = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupClientMoral() throws Exception {
        int databaseSizeBeforeCreate = groupClientMoralRepository.findAll().size();
        // Create the GroupClientMoral
        restGroupClientMoralMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isCreated());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeCreate + 1);
        GroupClientMoral testGroupClientMoral = groupClientMoralList.get(groupClientMoralList.size() - 1);
        assertThat(testGroupClientMoral.getNomGroupeClientMoral()).isEqualTo(DEFAULT_NOM_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getLienGroupeClientMoral()).isEqualTo(DEFAULT_LIEN_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getNombreClientMoral()).isEqualTo(DEFAULT_NOMBRE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void createGroupClientMoralWithExistingId() throws Exception {
        // Create the GroupClientMoral with an existing ID
        groupClientMoral.setId(1L);

        int databaseSizeBeforeCreate = groupClientMoralRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupClientMoralMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroupClientMorals() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        // Get all the groupClientMoralList
        restGroupClientMoralMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupClientMoral.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomGroupeClientMoral").value(hasItem(DEFAULT_NOM_GROUPE_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].lienGroupeClientMoral").value(hasItem(DEFAULT_LIEN_GROUPE_CLIENT_MORAL)))
            .andExpect(jsonPath("$.[*].nombreClientMoral").value(hasItem(DEFAULT_NOMBRE_CLIENT_MORAL)));
    }

    @Test
    @Transactional
    void getGroupClientMoral() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        // Get the groupClientMoral
        restGroupClientMoralMockMvc
            .perform(get(ENTITY_API_URL_ID, groupClientMoral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupClientMoral.getId().intValue()))
            .andExpect(jsonPath("$.nomGroupeClientMoral").value(DEFAULT_NOM_GROUPE_CLIENT_MORAL))
            .andExpect(jsonPath("$.lienGroupeClientMoral").value(DEFAULT_LIEN_GROUPE_CLIENT_MORAL))
            .andExpect(jsonPath("$.nombreClientMoral").value(DEFAULT_NOMBRE_CLIENT_MORAL));
    }

    @Test
    @Transactional
    void getNonExistingGroupClientMoral() throws Exception {
        // Get the groupClientMoral
        restGroupClientMoralMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGroupClientMoral() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();

        // Update the groupClientMoral
        GroupClientMoral updatedGroupClientMoral = groupClientMoralRepository.findById(groupClientMoral.getId()).get();
        // Disconnect from session so that the updates on updatedGroupClientMoral are not directly saved in db
        em.detach(updatedGroupClientMoral);
        updatedGroupClientMoral
            .nomGroupeClientMoral(UPDATED_NOM_GROUPE_CLIENT_MORAL)
            .lienGroupeClientMoral(UPDATED_LIEN_GROUPE_CLIENT_MORAL)
            .nombreClientMoral(UPDATED_NOMBRE_CLIENT_MORAL);

        restGroupClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroupClientMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGroupClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
        GroupClientMoral testGroupClientMoral = groupClientMoralList.get(groupClientMoralList.size() - 1);
        assertThat(testGroupClientMoral.getNomGroupeClientMoral()).isEqualTo(UPDATED_NOM_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getLienGroupeClientMoral()).isEqualTo(UPDATED_LIEN_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getNombreClientMoral()).isEqualTo(UPDATED_NOMBRE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void putNonExistingGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupClientMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupClientMoralWithPatch() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();

        // Update the groupClientMoral using partial update
        GroupClientMoral partialUpdatedGroupClientMoral = new GroupClientMoral();
        partialUpdatedGroupClientMoral.setId(groupClientMoral.getId());

        partialUpdatedGroupClientMoral
            .nomGroupeClientMoral(UPDATED_NOM_GROUPE_CLIENT_MORAL)
            .lienGroupeClientMoral(UPDATED_LIEN_GROUPE_CLIENT_MORAL);

        restGroupClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupClientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
        GroupClientMoral testGroupClientMoral = groupClientMoralList.get(groupClientMoralList.size() - 1);
        assertThat(testGroupClientMoral.getNomGroupeClientMoral()).isEqualTo(UPDATED_NOM_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getLienGroupeClientMoral()).isEqualTo(UPDATED_LIEN_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getNombreClientMoral()).isEqualTo(DEFAULT_NOMBRE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void fullUpdateGroupClientMoralWithPatch() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();

        // Update the groupClientMoral using partial update
        GroupClientMoral partialUpdatedGroupClientMoral = new GroupClientMoral();
        partialUpdatedGroupClientMoral.setId(groupClientMoral.getId());

        partialUpdatedGroupClientMoral
            .nomGroupeClientMoral(UPDATED_NOM_GROUPE_CLIENT_MORAL)
            .lienGroupeClientMoral(UPDATED_LIEN_GROUPE_CLIENT_MORAL)
            .nombreClientMoral(UPDATED_NOMBRE_CLIENT_MORAL);

        restGroupClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupClientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupClientMoral))
            )
            .andExpect(status().isOk());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
        GroupClientMoral testGroupClientMoral = groupClientMoralList.get(groupClientMoralList.size() - 1);
        assertThat(testGroupClientMoral.getNomGroupeClientMoral()).isEqualTo(UPDATED_NOM_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getLienGroupeClientMoral()).isEqualTo(UPDATED_LIEN_GROUPE_CLIENT_MORAL);
        assertThat(testGroupClientMoral.getNombreClientMoral()).isEqualTo(UPDATED_NOMBRE_CLIENT_MORAL);
    }

    @Test
    @Transactional
    void patchNonExistingGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupClientMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupClientMoral() throws Exception {
        int databaseSizeBeforeUpdate = groupClientMoralRepository.findAll().size();
        groupClientMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupClientMoralMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupClientMoral))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupClientMoral in the database
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroupClientMoral() throws Exception {
        // Initialize the database
        groupClientMoralRepository.saveAndFlush(groupClientMoral);

        int databaseSizeBeforeDelete = groupClientMoralRepository.findAll().size();

        // Delete the groupClientMoral
        restGroupClientMoralMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupClientMoral.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupClientMoral> groupClientMoralList = groupClientMoralRepository.findAll();
        assertThat(groupClientMoralList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
