package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdFulfillmentRepository;
import com.apptium.order.service.OrdFulfillmentQueryService;
import com.apptium.order.service.OrdFulfillmentService;
import com.apptium.order.service.criteria.OrdFulfillmentCriteria;
import com.apptium.order.service.dto.OrdFulfillmentDTO;
import com.apptium.order.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.order.domain.OrdFulfillment}.
 */
@RestController
@RequestMapping("/api")
public class OrdFulfillmentResource {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdFulfillment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdFulfillmentService ordFulfillmentService;

    private final OrdFulfillmentRepository ordFulfillmentRepository;

    private final OrdFulfillmentQueryService ordFulfillmentQueryService;

    public OrdFulfillmentResource(
        OrdFulfillmentService ordFulfillmentService,
        OrdFulfillmentRepository ordFulfillmentRepository,
        OrdFulfillmentQueryService ordFulfillmentQueryService
    ) {
        this.ordFulfillmentService = ordFulfillmentService;
        this.ordFulfillmentRepository = ordFulfillmentRepository;
        this.ordFulfillmentQueryService = ordFulfillmentQueryService;
    }

    /**
     * {@code POST  /ord-fulfillments} : Create a new ordFulfillment.
     *
     * @param ordFulfillmentDTO the ordFulfillmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordFulfillmentDTO, or with status {@code 400 (Bad Request)} if the ordFulfillment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-fulfillments")
    public ResponseEntity<OrdFulfillmentDTO> createOrdFulfillment(@RequestBody OrdFulfillmentDTO ordFulfillmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdFulfillment : {}", ordFulfillmentDTO);
        if (ordFulfillmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordFulfillment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdFulfillmentDTO result = ordFulfillmentService.save(ordFulfillmentDTO);
        return ResponseEntity
            .created(new URI("/api/ord-fulfillments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-fulfillments/:id} : Updates an existing ordFulfillment.
     *
     * @param id the id of the ordFulfillmentDTO to save.
     * @param ordFulfillmentDTO the ordFulfillmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordFulfillmentDTO,
     * or with status {@code 400 (Bad Request)} if the ordFulfillmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordFulfillmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-fulfillments/{id}")
    public ResponseEntity<OrdFulfillmentDTO> updateOrdFulfillment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdFulfillmentDTO ordFulfillmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdFulfillment : {}, {}", id, ordFulfillmentDTO);
        if (ordFulfillmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordFulfillmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordFulfillmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdFulfillmentDTO result = ordFulfillmentService.save(ordFulfillmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordFulfillmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-fulfillments/:id} : Partial updates given fields of an existing ordFulfillment, field will ignore if it is null
     *
     * @param id the id of the ordFulfillmentDTO to save.
     * @param ordFulfillmentDTO the ordFulfillmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordFulfillmentDTO,
     * or with status {@code 400 (Bad Request)} if the ordFulfillmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordFulfillmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordFulfillmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-fulfillments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdFulfillmentDTO> partialUpdateOrdFulfillment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdFulfillmentDTO ordFulfillmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdFulfillment partially : {}, {}", id, ordFulfillmentDTO);
        if (ordFulfillmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordFulfillmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordFulfillmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdFulfillmentDTO> result = ordFulfillmentService.partialUpdate(ordFulfillmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordFulfillmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-fulfillments} : get all the ordFulfillments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordFulfillments in body.
     */
    @GetMapping("/ord-fulfillments")
    public ResponseEntity<List<OrdFulfillmentDTO>> getAllOrdFulfillments(OrdFulfillmentCriteria criteria) {
        log.debug("REST request to get OrdFulfillments by criteria: {}", criteria);
        List<OrdFulfillmentDTO> entityList = ordFulfillmentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-fulfillments/count} : count all the ordFulfillments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-fulfillments/count")
    public ResponseEntity<Long> countOrdFulfillments(OrdFulfillmentCriteria criteria) {
        log.debug("REST request to count OrdFulfillments by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordFulfillmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-fulfillments/:id} : get the "id" ordFulfillment.
     *
     * @param id the id of the ordFulfillmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordFulfillmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-fulfillments/{id}")
    public ResponseEntity<OrdFulfillmentDTO> getOrdFulfillment(@PathVariable Long id) {
        log.debug("REST request to get OrdFulfillment : {}", id);
        Optional<OrdFulfillmentDTO> ordFulfillmentDTO = ordFulfillmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordFulfillmentDTO);
    }

    /**
     * {@code DELETE  /ord-fulfillments/:id} : delete the "id" ordFulfillment.
     *
     * @param id the id of the ordFulfillmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-fulfillments/{id}")
    public ResponseEntity<Void> deleteOrdFulfillment(@PathVariable Long id) {
        log.debug("REST request to delete OrdFulfillment : {}", id);
        ordFulfillmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
