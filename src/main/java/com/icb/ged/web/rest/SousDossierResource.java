package com.icb.ged.web.rest;

import com.icb.ged.domain.SousDossier;
import com.icb.ged.repository.SousDossierRepository;
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
 * REST controller for managing {@link com.icb.ged.domain.SousDossier}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SousDossierResource {

    private final Logger log = LoggerFactory.getLogger(SousDossierResource.class);

    private static final String ENTITY_NAME = "sousDossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousDossierRepository sousDossierRepository;

    public SousDossierResource(SousDossierRepository sousDossierRepository) {
        this.sousDossierRepository = sousDossierRepository;
    }

    /**
     * {@code POST  /sous-dossiers} : Create a new sousDossier.
     *
     * @param sousDossier the sousDossier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousDossier, or with status {@code 400 (Bad Request)} if the sousDossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-dossiers")
    public ResponseEntity<SousDossier> createSousDossier(@RequestBody SousDossier sousDossier) throws URISyntaxException {
        log.debug("REST request to save SousDossier : {}", sousDossier);
        if (sousDossier.getId() != null) {
            throw new BadRequestAlertException("A new sousDossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousDossier result = sousDossierRepository.save(sousDossier);
        return ResponseEntity
            .created(new URI("/api/sous-dossiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-dossiers/:id} : Updates an existing sousDossier.
     *
     * @param id the id of the sousDossier to save.
     * @param sousDossier the sousDossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousDossier,
     * or with status {@code 400 (Bad Request)} if the sousDossier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousDossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-dossiers/{id}")
    public ResponseEntity<SousDossier> updateSousDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousDossier sousDossier
    ) throws URISyntaxException {
        log.debug("REST request to update SousDossier : {}, {}", id, sousDossier);
        if (sousDossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousDossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SousDossier result = sousDossierRepository.save(sousDossier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sousDossier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sous-dossiers/:id} : Partial updates given fields of an existing sousDossier, field will ignore if it is null
     *
     * @param id the id of the sousDossier to save.
     * @param sousDossier the sousDossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousDossier,
     * or with status {@code 400 (Bad Request)} if the sousDossier is not valid,
     * or with status {@code 404 (Not Found)} if the sousDossier is not found,
     * or with status {@code 500 (Internal Server Error)} if the sousDossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sous-dossiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SousDossier> partialUpdateSousDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousDossier sousDossier
    ) throws URISyntaxException {
        log.debug("REST request to partial update SousDossier partially : {}, {}", id, sousDossier);
        if (sousDossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousDossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SousDossier> result = sousDossierRepository
            .findById(sousDossier.getId())
            .map(existingSousDossier -> {
                if (sousDossier.getQrcode() != null) {
                    existingSousDossier.setQrcode(sousDossier.getQrcode());
                }
                if (sousDossier.getTitre() != null) {
                    existingSousDossier.setTitre(sousDossier.getTitre());
                }

                return existingSousDossier;
            })
            .map(sousDossierRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sousDossier.getId().toString())
        );
    }

    /**
     * {@code GET  /sous-dossiers} : get all the sousDossiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousDossiers in body.
     */
    @GetMapping("/sous-dossiers")
    public List<SousDossier> getAllSousDossiers() {
        log.debug("REST request to get all SousDossiers");
        return sousDossierRepository.findAll();
    }

    /**
     * {@code GET  /sous-dossiers/:id} : get the "id" sousDossier.
     *
     * @param id the id of the sousDossier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousDossier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-dossiers/{id}")
    public ResponseEntity<SousDossier> getSousDossier(@PathVariable Long id) {
        log.debug("REST request to get SousDossier : {}", id);
        Optional<SousDossier> sousDossier = sousDossierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sousDossier);
    }

    /**
     * {@code DELETE  /sous-dossiers/:id} : delete the "id" sousDossier.
     *
     * @param id the id of the sousDossier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-dossiers/{id}")
    public ResponseEntity<Void> deleteSousDossier(@PathVariable Long id) {
        log.debug("REST request to delete SousDossier : {}", id);
        sousDossierRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
