package com.icb.ged.web.rest;

import com.icb.ged.domain.Entreprise;
import com.icb.ged.repository.EntrepriseRepository;
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
 * REST controller for managing {@link com.icb.ged.domain.Entreprise}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseResource(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    /**
     * {@code POST  /entreprises} : Create a new entreprise.
     *
     * @param entreprise the entreprise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entreprise, or with status {@code 400 (Bad Request)} if the entreprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entreprises")
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entreprise);
        if (entreprise.getId() != null) {
            throw new BadRequestAlertException("A new entreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity
            .created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entreprises/:id} : Updates an existing entreprise.
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entreprises/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entreprise entreprise
    ) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entreprise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entreprises/:id} : Partial updates given fields of an existing entreprise, field will ignore if it is null
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 404 (Not Found)} if the entreprise is not found,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entreprises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Entreprise> partialUpdateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entreprise entreprise
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entreprise partially : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Entreprise> result = entrepriseRepository
            .findById(entreprise.getId())
            .map(existingEntreprise -> {
                if (entreprise.getEnterpriseID() != null) {
                    existingEntreprise.setEnterpriseID(entreprise.getEnterpriseID());
                }
                if (entreprise.getNomCabinet() != null) {
                    existingEntreprise.setNomCabinet(entreprise.getNomCabinet());
                }
                if (entreprise.getNumeroIdentificationCabinet() != null) {
                    existingEntreprise.setNumeroIdentificationCabinet(entreprise.getNumeroIdentificationCabinet());
                }
                if (entreprise.getEmailCabinet() != null) {
                    existingEntreprise.setEmailCabinet(entreprise.getEmailCabinet());
                }
                if (entreprise.getAdresseCabinet() != null) {
                    existingEntreprise.setAdresseCabinet(entreprise.getAdresseCabinet());
                }
                if (entreprise.getTelephoneCabinet() != null) {
                    existingEntreprise.setTelephoneCabinet(entreprise.getTelephoneCabinet());
                }
                if (entreprise.getVilleCabinet() != null) {
                    existingEntreprise.setVilleCabinet(entreprise.getVilleCabinet());
                }
                if (entreprise.getActiviteCabinet() != null) {
                    existingEntreprise.setActiviteCabinet(entreprise.getActiviteCabinet());
                }

                return existingEntreprise;
            })
            .map(entrepriseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entreprise.getId().toString())
        );
    }

    /**
     * {@code GET  /entreprises} : get all the entreprises.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreprises in body.
     */
    @GetMapping("/entreprises")
    public List<Entreprise> getAllEntreprises(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Entreprises");
        return entrepriseRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /entreprises/:id} : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entreprise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entreprises/{id}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        Optional<Entreprise> entreprise = entrepriseRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(entreprise);
    }

    /**
     * {@code DELETE  /entreprises/:id} : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entreprises/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
