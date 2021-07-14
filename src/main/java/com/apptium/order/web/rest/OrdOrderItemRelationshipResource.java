package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdOrderItemRelationshipRepository;
import com.apptium.order.service.OrdOrderItemRelationshipQueryService;
import com.apptium.order.service.OrdOrderItemRelationshipService;
import com.apptium.order.service.criteria.OrdOrderItemRelationshipCriteria;
import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import com.apptium.order.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.order.domain.OrdOrderItemRelationship}.
 */
@RestController
@RequestMapping("/api")
public class OrdOrderItemRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemRelationshipResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdOrderItemRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdOrderItemRelationshipService ordOrderItemRelationshipService;

    private final OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository;

    private final OrdOrderItemRelationshipQueryService ordOrderItemRelationshipQueryService;

    public OrdOrderItemRelationshipResource(
        OrdOrderItemRelationshipService ordOrderItemRelationshipService,
        OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository,
        OrdOrderItemRelationshipQueryService ordOrderItemRelationshipQueryService
    ) {
        this.ordOrderItemRelationshipService = ordOrderItemRelationshipService;
        this.ordOrderItemRelationshipRepository = ordOrderItemRelationshipRepository;
        this.ordOrderItemRelationshipQueryService = ordOrderItemRelationshipQueryService;
    }

    /**
     * {@code POST  /ord-order-item-relationships} : Create a new ordOrderItemRelationship.
     *
     * @param ordOrderItemRelationshipDTO the ordOrderItemRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordOrderItemRelationshipDTO, or with status {@code 400 (Bad Request)} if the ordOrderItemRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-order-item-relationships")
    public ResponseEntity<OrdOrderItemRelationshipDTO> createOrdOrderItemRelationship(
        @RequestBody OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrdOrderItemRelationship : {}", ordOrderItemRelationshipDTO);
        if (ordOrderItemRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordOrderItemRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdOrderItemRelationshipDTO result = ordOrderItemRelationshipService.save(ordOrderItemRelationshipDTO);
        return ResponseEntity
            .created(new URI("/api/ord-order-item-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-order-item-relationships/:id} : Updates an existing ordOrderItemRelationship.
     *
     * @param id the id of the ordOrderItemRelationshipDTO to save.
     * @param ordOrderItemRelationshipDTO the ordOrderItemRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-order-item-relationships/{id}")
    public ResponseEntity<OrdOrderItemRelationshipDTO> updateOrdOrderItemRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdOrderItemRelationship : {}, {}", id, ordOrderItemRelationshipDTO);
        if (ordOrderItemRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdOrderItemRelationshipDTO result = ordOrderItemRelationshipService.save(ordOrderItemRelationshipDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemRelationshipDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /ord-order-item-relationships/:id} : Partial updates given fields of an existing ordOrderItemRelationship, field will ignore if it is null
     *
     * @param id the id of the ordOrderItemRelationshipDTO to save.
     * @param ordOrderItemRelationshipDTO the ordOrderItemRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemRelationshipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordOrderItemRelationshipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-order-item-relationships/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdOrderItemRelationshipDTO> partialUpdateOrdOrderItemRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdOrderItemRelationship partially : {}, {}", id, ordOrderItemRelationshipDTO);
        if (ordOrderItemRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdOrderItemRelationshipDTO> result = ordOrderItemRelationshipService.partialUpdate(ordOrderItemRelationshipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemRelationshipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-order-item-relationships} : get all the ordOrderItemRelationships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordOrderItemRelationships in body.
     */
    @GetMapping("/ord-order-item-relationships")
    public ResponseEntity<List<OrdOrderItemRelationshipDTO>> getAllOrdOrderItemRelationships(OrdOrderItemRelationshipCriteria criteria) {
        log.debug("REST request to get OrdOrderItemRelationships by criteria: {}", criteria);
        List<OrdOrderItemRelationshipDTO> entityList = ordOrderItemRelationshipQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-order-item-relationships/count} : count all the ordOrderItemRelationships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-order-item-relationships/count")
    public ResponseEntity<Long> countOrdOrderItemRelationships(OrdOrderItemRelationshipCriteria criteria) {
        log.debug("REST request to count OrdOrderItemRelationships by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordOrderItemRelationshipQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-order-item-relationships/:id} : get the "id" ordOrderItemRelationship.
     *
     * @param id the id of the ordOrderItemRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordOrderItemRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-order-item-relationships/{id}")
    public ResponseEntity<OrdOrderItemRelationshipDTO> getOrdOrderItemRelationship(@PathVariable Long id) {
        log.debug("REST request to get OrdOrderItemRelationship : {}", id);
        Optional<OrdOrderItemRelationshipDTO> ordOrderItemRelationshipDTO = ordOrderItemRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordOrderItemRelationshipDTO);
    }

    /**
     * {@code DELETE  /ord-order-item-relationships/:id} : delete the "id" ordOrderItemRelationship.
     *
     * @param id the id of the ordOrderItemRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-order-item-relationships/{id}")
    public ResponseEntity<Void> deleteOrdOrderItemRelationship(@PathVariable Long id) {
        log.debug("REST request to delete OrdOrderItemRelationship : {}", id);
        ordOrderItemRelationshipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
