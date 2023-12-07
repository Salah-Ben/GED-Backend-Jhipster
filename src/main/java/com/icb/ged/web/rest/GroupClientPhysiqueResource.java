package com.icb.ged.web.rest;

import com.icb.ged.domain.GroupClientPhysique;
import com.icb.ged.repository.GroupClientPhysiqueRepository;
import com.icb.ged.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.icb.ged.domain.GroupClientPhysique}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupClientPhysiqueResource {

    private final Logger log = LoggerFactory.getLogger(GroupClientPhysiqueResource.class);

    private static final String ENTITY_NAME = "groupClientPhysique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupClientPhysiqueRepository groupClientPhysiqueRepository;

    public GroupClientPhysiqueResource(GroupClientPhysiqueRepository groupClientPhysiqueRepository) {
        this.groupClientPhysiqueRepository = groupClientPhysiqueRepository;
    }

    /**
     * {@code POST  /group-client-physiques} : Create a new groupClientPhysique.
     *
     * @param groupClientPhysique the groupClientPhysique to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupClientPhysique, or with status {@code 400 (Bad Request)} if the groupClientPhysique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-client-physiques")
    public ResponseEntity<GroupClientPhysique> createGroupClientPhysique(@RequestBody GroupClientPhysique groupClientPhysique)
        throws URISyntaxException {
        log.debug("REST request to save GroupClientPhysique : {}", groupClientPhysique);
        if (groupClientPhysique.getId() != null) {
            throw new BadRequestAlertException("A new groupClientPhysique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupClientPhysique result = groupClientPhysiqueRepository.save(groupClientPhysique);
        return ResponseEntity
            .created(new URI("/api/group-client-physiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-client-physiques/:id} : Updates an existing groupClientPhysique.
     *
     * @param id the id of the groupClientPhysique to save.
     * @param groupClientPhysique the groupClientPhysique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupClientPhysique,
     * or with status {@code 400 (Bad Request)} if the groupClientPhysique is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupClientPhysique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-client-physiques/{id}")
    public ResponseEntity<GroupClientPhysique> updateGroupClientPhysique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupClientPhysique groupClientPhysique
    ) throws URISyntaxException {
        log.debug("REST request to update GroupClientPhysique : {}, {}", id, groupClientPhysique);
        if (groupClientPhysique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupClientPhysique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupClientPhysiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupClientPhysique result = groupClientPhysiqueRepository.save(groupClientPhysique);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupClientPhysique.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /group-client-physiques/:id} : Partial updates given fields of an existing groupClientPhysique, field will ignore if it is null
     *
     * @param id the id of the groupClientPhysique to save.
     * @param groupClientPhysique the groupClientPhysique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupClientPhysique,
     * or with status {@code 400 (Bad Request)} if the groupClientPhysique is not valid,
     * or with status {@code 404 (Not Found)} if the groupClientPhysique is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupClientPhysique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/group-client-physiques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupClientPhysique> partialUpdateGroupClientPhysique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupClientPhysique groupClientPhysique
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupClientPhysique partially : {}, {}", id, groupClientPhysique);
        if (groupClientPhysique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupClientPhysique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupClientPhysiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupClientPhysique> result = groupClientPhysiqueRepository
            .findById(groupClientPhysique.getId())
            .map(existingGroupClientPhysique -> {
                if (groupClientPhysique.getNomGroupePhysique() != null) {
                    existingGroupClientPhysique.setNomGroupePhysique(groupClientPhysique.getNomGroupePhysique());
                }
                if (groupClientPhysique.getLienGroupePhysique() != null) {
                    existingGroupClientPhysique.setLienGroupePhysique(groupClientPhysique.getLienGroupePhysique());
                }
                if (groupClientPhysique.getNombrePersonnePhysique() != null) {
                    existingGroupClientPhysique.setNombrePersonnePhysique(groupClientPhysique.getNombrePersonnePhysique());
                }

                return existingGroupClientPhysique;
            })
            .map(groupClientPhysiqueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupClientPhysique.getId().toString())
        );
    }

    /**
     * {@code GET  /group-client-physiques} : get all the groupClientPhysiques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupClientPhysiques in body.
     */
    @GetMapping("/group-client-physiques")
    public List<GroupClientPhysique> getAllGroupClientPhysiques() {
        log.debug("REST request to get all GroupClientPhysiques");
        return groupClientPhysiqueRepository.findAll();
    }

    /**
     * {@code GET  /group-client-physiques/:id} : get the "id" groupClientPhysique.
     *
     * @param id the id of the groupClientPhysique to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupClientPhysique, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-client-physiques/{id}")
    public ResponseEntity<GroupClientPhysique> getGroupClientPhysique(@PathVariable Long id) {
        log.debug("REST request to get GroupClientPhysique : {}", id);
        Optional<GroupClientPhysique> groupClientPhysique = groupClientPhysiqueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupClientPhysique);
    }

    /**
     * {@code DELETE  /group-client-physiques/:id} : delete the "id" groupClientPhysique.
     *
     * @param id the id of the groupClientPhysique to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-client-physiques/{id}")
    public ResponseEntity<Void> deleteGroupClientPhysique(@PathVariable Long id) {
        log.debug("REST request to delete GroupClientPhysique : {}", id);
        groupClientPhysiqueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
