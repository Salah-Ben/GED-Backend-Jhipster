package com.icb.ged.web.rest;

import com.icb.ged.domain.GroupClientMoral;
import com.icb.ged.repository.GroupClientMoralRepository;
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
 * REST controller for managing {@link com.icb.ged.domain.GroupClientMoral}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupClientMoralResource {

    private final Logger log = LoggerFactory.getLogger(GroupClientMoralResource.class);

    private static final String ENTITY_NAME = "groupClientMoral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupClientMoralRepository groupClientMoralRepository;

    public GroupClientMoralResource(GroupClientMoralRepository groupClientMoralRepository) {
        this.groupClientMoralRepository = groupClientMoralRepository;
    }

    /**
     * {@code POST  /group-client-morals} : Create a new groupClientMoral.
     *
     * @param groupClientMoral the groupClientMoral to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupClientMoral, or with status {@code 400 (Bad Request)} if the groupClientMoral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-client-morals")
    public ResponseEntity<GroupClientMoral> createGroupClientMoral(@RequestBody GroupClientMoral groupClientMoral)
        throws URISyntaxException {
        log.debug("REST request to save GroupClientMoral : {}", groupClientMoral);
        if (groupClientMoral.getId() != null) {
            throw new BadRequestAlertException("A new groupClientMoral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupClientMoral result = groupClientMoralRepository.save(groupClientMoral);
        return ResponseEntity
            .created(new URI("/api/group-client-morals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-client-morals/:id} : Updates an existing groupClientMoral.
     *
     * @param id the id of the groupClientMoral to save.
     * @param groupClientMoral the groupClientMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupClientMoral,
     * or with status {@code 400 (Bad Request)} if the groupClientMoral is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupClientMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-client-morals/{id}")
    public ResponseEntity<GroupClientMoral> updateGroupClientMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupClientMoral groupClientMoral
    ) throws URISyntaxException {
        log.debug("REST request to update GroupClientMoral : {}, {}", id, groupClientMoral);
        if (groupClientMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupClientMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupClientMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupClientMoral result = groupClientMoralRepository.save(groupClientMoral);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupClientMoral.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /group-client-morals/:id} : Partial updates given fields of an existing groupClientMoral, field will ignore if it is null
     *
     * @param id the id of the groupClientMoral to save.
     * @param groupClientMoral the groupClientMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupClientMoral,
     * or with status {@code 400 (Bad Request)} if the groupClientMoral is not valid,
     * or with status {@code 404 (Not Found)} if the groupClientMoral is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupClientMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/group-client-morals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupClientMoral> partialUpdateGroupClientMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupClientMoral groupClientMoral
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupClientMoral partially : {}, {}", id, groupClientMoral);
        if (groupClientMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupClientMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupClientMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupClientMoral> result = groupClientMoralRepository
            .findById(groupClientMoral.getId())
            .map(existingGroupClientMoral -> {
                if (groupClientMoral.getNomGroupeClientMoral() != null) {
                    existingGroupClientMoral.setNomGroupeClientMoral(groupClientMoral.getNomGroupeClientMoral());
                }
                if (groupClientMoral.getLienGroupeClientMoral() != null) {
                    existingGroupClientMoral.setLienGroupeClientMoral(groupClientMoral.getLienGroupeClientMoral());
                }
                if (groupClientMoral.getNombreClientMoral() != null) {
                    existingGroupClientMoral.setNombreClientMoral(groupClientMoral.getNombreClientMoral());
                }

                return existingGroupClientMoral;
            })
            .map(groupClientMoralRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupClientMoral.getId().toString())
        );
    }

    /**
     * {@code GET  /group-client-morals} : get all the groupClientMorals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupClientMorals in body.
     */
    @GetMapping("/group-client-morals")
    public List<GroupClientMoral> getAllGroupClientMorals() {
        log.debug("REST request to get all GroupClientMorals");
        return groupClientMoralRepository.findAll();
    }

    /**
     * {@code GET  /group-client-morals/:id} : get the "id" groupClientMoral.
     *
     * @param id the id of the groupClientMoral to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupClientMoral, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-client-morals/{id}")
    public ResponseEntity<GroupClientMoral> getGroupClientMoral(@PathVariable Long id) {
        log.debug("REST request to get GroupClientMoral : {}", id);
        Optional<GroupClientMoral> groupClientMoral = groupClientMoralRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupClientMoral);
    }

    /**
     * {@code DELETE  /group-client-morals/:id} : delete the "id" groupClientMoral.
     *
     * @param id the id of the groupClientMoral to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-client-morals/{id}")
    public ResponseEntity<Void> deleteGroupClientMoral(@PathVariable Long id) {
        log.debug("REST request to delete GroupClientMoral : {}", id);
        groupClientMoralRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
