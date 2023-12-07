package com.icb.ged.web.rest;

import com.icb.ged.domain.ClientMoral;
import com.icb.ged.repository.ClientMoralRepository;
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
 * REST controller for managing {@link com.icb.ged.domain.ClientMoral}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientMoralResource {

    private final Logger log = LoggerFactory.getLogger(ClientMoralResource.class);

    private static final String ENTITY_NAME = "clientMoral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientMoralRepository clientMoralRepository;

    public ClientMoralResource(ClientMoralRepository clientMoralRepository) {
        this.clientMoralRepository = clientMoralRepository;
    }

    /**
     * {@code POST  /client-morals} : Create a new clientMoral.
     *
     * @param clientMoral the clientMoral to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientMoral, or with status {@code 400 (Bad Request)} if the clientMoral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-morals")
    public ResponseEntity<ClientMoral> createClientMoral(@RequestBody ClientMoral clientMoral) throws URISyntaxException {
        log.debug("REST request to save ClientMoral : {}", clientMoral);
        if (clientMoral.getId() != null) {
            throw new BadRequestAlertException("A new clientMoral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientMoral result = clientMoralRepository.save(clientMoral);
        return ResponseEntity
            .created(new URI("/api/client-morals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-morals/:id} : Updates an existing clientMoral.
     *
     * @param id the id of the clientMoral to save.
     * @param clientMoral the clientMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientMoral,
     * or with status {@code 400 (Bad Request)} if the clientMoral is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-morals/{id}")
    public ResponseEntity<ClientMoral> updateClientMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientMoral clientMoral
    ) throws URISyntaxException {
        log.debug("REST request to update ClientMoral : {}, {}", id, clientMoral);
        if (clientMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientMoral result = clientMoralRepository.save(clientMoral);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientMoral.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-morals/:id} : Partial updates given fields of an existing clientMoral, field will ignore if it is null
     *
     * @param id the id of the clientMoral to save.
     * @param clientMoral the clientMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientMoral,
     * or with status {@code 400 (Bad Request)} if the clientMoral is not valid,
     * or with status {@code 404 (Not Found)} if the clientMoral is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-morals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientMoral> partialUpdateClientMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientMoral clientMoral
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientMoral partially : {}, {}", id, clientMoral);
        if (clientMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientMoral> result = clientMoralRepository
            .findById(clientMoral.getId())
            .map(existingClientMoral -> {
                if (clientMoral.getNomClientMoral() != null) {
                    existingClientMoral.setNomClientMoral(clientMoral.getNomClientMoral());
                }
                if (clientMoral.getNumeroIdentificationClientMoral() != null) {
                    existingClientMoral.setNumeroIdentificationClientMoral(clientMoral.getNumeroIdentificationClientMoral());
                }
                if (clientMoral.getAdresseClientMoral() != null) {
                    existingClientMoral.setAdresseClientMoral(clientMoral.getAdresseClientMoral());
                }
                if (clientMoral.getEmailClientMoral() != null) {
                    existingClientMoral.setEmailClientMoral(clientMoral.getEmailClientMoral());
                }
                if (clientMoral.getTelephoneClientMoral() != null) {
                    existingClientMoral.setTelephoneClientMoral(clientMoral.getTelephoneClientMoral());
                }
                if (clientMoral.getVilleClientMoral() != null) {
                    existingClientMoral.setVilleClientMoral(clientMoral.getVilleClientMoral());
                }
                if (clientMoral.getActiviteClientMoral() != null) {
                    existingClientMoral.setActiviteClientMoral(clientMoral.getActiviteClientMoral());
                }

                return existingClientMoral;
            })
            .map(clientMoralRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientMoral.getId().toString())
        );
    }

    /**
     * {@code GET  /client-morals} : get all the clientMorals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientMorals in body.
     */
    @GetMapping("/client-morals")
    public List<ClientMoral> getAllClientMorals() {
        log.debug("REST request to get all ClientMorals");
        return clientMoralRepository.findAll();
    }

    /**
     * {@code GET  /client-morals/:id} : get the "id" clientMoral.
     *
     * @param id the id of the clientMoral to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientMoral, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-morals/{id}")
    public ResponseEntity<ClientMoral> getClientMoral(@PathVariable Long id) {
        log.debug("REST request to get ClientMoral : {}", id);
        Optional<ClientMoral> clientMoral = clientMoralRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientMoral);
    }

    /**
     * {@code DELETE  /client-morals/:id} : delete the "id" clientMoral.
     *
     * @param id the id of the clientMoral to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-morals/{id}")
    public ResponseEntity<Void> deleteClientMoral(@PathVariable Long id) {
        log.debug("REST request to delete ClientMoral : {}", id);
        clientMoralRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
