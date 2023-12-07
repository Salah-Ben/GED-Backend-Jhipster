package com.icb.ged.web.rest;

import com.icb.ged.domain.ClientPhysique;
import com.icb.ged.repository.ClientPhysiqueRepository;
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
 * REST controller for managing {@link com.icb.ged.domain.ClientPhysique}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientPhysiqueResource {

    private final Logger log = LoggerFactory.getLogger(ClientPhysiqueResource.class);

    private static final String ENTITY_NAME = "clientPhysique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientPhysiqueRepository clientPhysiqueRepository;

    public ClientPhysiqueResource(ClientPhysiqueRepository clientPhysiqueRepository) {
        this.clientPhysiqueRepository = clientPhysiqueRepository;
    }

    /**
     * {@code POST  /client-physiques} : Create a new clientPhysique.
     *
     * @param clientPhysique the clientPhysique to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientPhysique, or with status {@code 400 (Bad Request)} if the clientPhysique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-physiques")
    public ResponseEntity<ClientPhysique> createClientPhysique(@RequestBody ClientPhysique clientPhysique) throws URISyntaxException {
        log.debug("REST request to save ClientPhysique : {}", clientPhysique);
        if (clientPhysique.getId() != null) {
            throw new BadRequestAlertException("A new clientPhysique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientPhysique result = clientPhysiqueRepository.save(clientPhysique);
        return ResponseEntity
            .created(new URI("/api/client-physiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-physiques/:id} : Updates an existing clientPhysique.
     *
     * @param id the id of the clientPhysique to save.
     * @param clientPhysique the clientPhysique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPhysique,
     * or with status {@code 400 (Bad Request)} if the clientPhysique is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientPhysique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-physiques/{id}")
    public ResponseEntity<ClientPhysique> updateClientPhysique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientPhysique clientPhysique
    ) throws URISyntaxException {
        log.debug("REST request to update ClientPhysique : {}, {}", id, clientPhysique);
        if (clientPhysique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientPhysique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientPhysiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientPhysique result = clientPhysiqueRepository.save(clientPhysique);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientPhysique.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-physiques/:id} : Partial updates given fields of an existing clientPhysique, field will ignore if it is null
     *
     * @param id the id of the clientPhysique to save.
     * @param clientPhysique the clientPhysique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPhysique,
     * or with status {@code 400 (Bad Request)} if the clientPhysique is not valid,
     * or with status {@code 404 (Not Found)} if the clientPhysique is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientPhysique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-physiques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientPhysique> partialUpdateClientPhysique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientPhysique clientPhysique
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientPhysique partially : {}, {}", id, clientPhysique);
        if (clientPhysique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientPhysique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientPhysiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientPhysique> result = clientPhysiqueRepository
            .findById(clientPhysique.getId())
            .map(existingClientPhysique -> {
                if (clientPhysique.getNomClientPhysisque() != null) {
                    existingClientPhysique.setNomClientPhysisque(clientPhysique.getNomClientPhysisque());
                }
                if (clientPhysique.getPrenomClientPhysique() != null) {
                    existingClientPhysique.setPrenomClientPhysique(clientPhysique.getPrenomClientPhysique());
                }
                if (clientPhysique.getCinClientPhysisque() != null) {
                    existingClientPhysique.setCinClientPhysisque(clientPhysique.getCinClientPhysisque());
                }
                if (clientPhysique.getEmailClientPhysique() != null) {
                    existingClientPhysique.setEmailClientPhysique(clientPhysique.getEmailClientPhysique());
                }
                if (clientPhysique.getTelephoneClientPhysique() != null) {
                    existingClientPhysique.setTelephoneClientPhysique(clientPhysique.getTelephoneClientPhysique());
                }
                if (clientPhysique.getAdresseClientPhysique() != null) {
                    existingClientPhysique.setAdresseClientPhysique(clientPhysique.getAdresseClientPhysique());
                }
                if (clientPhysique.getVilleClientPhysique() != null) {
                    existingClientPhysique.setVilleClientPhysique(clientPhysique.getVilleClientPhysique());
                }

                return existingClientPhysique;
            })
            .map(clientPhysiqueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientPhysique.getId().toString())
        );
    }

    /**
     * {@code GET  /client-physiques} : get all the clientPhysiques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientPhysiques in body.
     */
    @GetMapping("/client-physiques")
    public List<ClientPhysique> getAllClientPhysiques() {
        log.debug("REST request to get all ClientPhysiques");
        return clientPhysiqueRepository.findAll();
    }

    /**
     * {@code GET  /client-physiques/:id} : get the "id" clientPhysique.
     *
     * @param id the id of the clientPhysique to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientPhysique, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-physiques/{id}")
    public ResponseEntity<ClientPhysique> getClientPhysique(@PathVariable Long id) {
        log.debug("REST request to get ClientPhysique : {}", id);
        Optional<ClientPhysique> clientPhysique = clientPhysiqueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientPhysique);
    }

    /**
     * {@code DELETE  /client-physiques/:id} : delete the "id" clientPhysique.
     *
     * @param id the id of the clientPhysique to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-physiques/{id}")
    public ResponseEntity<Void> deleteClientPhysique(@PathVariable Long id) {
        log.debug("REST request to delete ClientPhysique : {}", id);
        clientPhysiqueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
