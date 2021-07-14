package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdPaymentRefRepository;
import com.apptium.order.service.OrdPaymentRefQueryService;
import com.apptium.order.service.OrdPaymentRefService;
import com.apptium.order.service.criteria.OrdPaymentRefCriteria;
import com.apptium.order.service.dto.OrdPaymentRefDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdPaymentRef}.
 */
@RestController
@RequestMapping("/api")
public class OrdPaymentRefResource {

    private final Logger log = LoggerFactory.getLogger(OrdPaymentRefResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdPaymentRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdPaymentRefService ordPaymentRefService;

    private final OrdPaymentRefRepository ordPaymentRefRepository;

    private final OrdPaymentRefQueryService ordPaymentRefQueryService;

    public OrdPaymentRefResource(
        OrdPaymentRefService ordPaymentRefService,
        OrdPaymentRefRepository ordPaymentRefRepository,
        OrdPaymentRefQueryService ordPaymentRefQueryService
    ) {
        this.ordPaymentRefService = ordPaymentRefService;
        this.ordPaymentRefRepository = ordPaymentRefRepository;
        this.ordPaymentRefQueryService = ordPaymentRefQueryService;
    }

    /**
     * {@code POST  /ord-payment-refs} : Create a new ordPaymentRef.
     *
     * @param ordPaymentRefDTO the ordPaymentRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordPaymentRefDTO, or with status {@code 400 (Bad Request)} if the ordPaymentRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-payment-refs")
    public ResponseEntity<OrdPaymentRefDTO> createOrdPaymentRef(@RequestBody OrdPaymentRefDTO ordPaymentRefDTO) throws URISyntaxException {
        log.debug("REST request to save OrdPaymentRef : {}", ordPaymentRefDTO);
        if (ordPaymentRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordPaymentRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdPaymentRefDTO result = ordPaymentRefService.save(ordPaymentRefDTO);
        return ResponseEntity
            .created(new URI("/api/ord-payment-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-payment-refs/:id} : Updates an existing ordPaymentRef.
     *
     * @param id the id of the ordPaymentRefDTO to save.
     * @param ordPaymentRefDTO the ordPaymentRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPaymentRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordPaymentRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordPaymentRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-payment-refs/{id}")
    public ResponseEntity<OrdPaymentRefDTO> updateOrdPaymentRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPaymentRefDTO ordPaymentRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdPaymentRef : {}, {}", id, ordPaymentRefDTO);
        if (ordPaymentRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPaymentRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPaymentRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdPaymentRefDTO result = ordPaymentRefService.save(ordPaymentRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPaymentRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-payment-refs/:id} : Partial updates given fields of an existing ordPaymentRef, field will ignore if it is null
     *
     * @param id the id of the ordPaymentRefDTO to save.
     * @param ordPaymentRefDTO the ordPaymentRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPaymentRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordPaymentRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordPaymentRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordPaymentRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-payment-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdPaymentRefDTO> partialUpdateOrdPaymentRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPaymentRefDTO ordPaymentRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdPaymentRef partially : {}, {}", id, ordPaymentRefDTO);
        if (ordPaymentRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPaymentRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPaymentRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdPaymentRefDTO> result = ordPaymentRefService.partialUpdate(ordPaymentRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPaymentRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-payment-refs} : get all the ordPaymentRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordPaymentRefs in body.
     */
    @GetMapping("/ord-payment-refs")
    public ResponseEntity<List<OrdPaymentRefDTO>> getAllOrdPaymentRefs(OrdPaymentRefCriteria criteria) {
        log.debug("REST request to get OrdPaymentRefs by criteria: {}", criteria);
        List<OrdPaymentRefDTO> entityList = ordPaymentRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-payment-refs/count} : count all the ordPaymentRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-payment-refs/count")
    public ResponseEntity<Long> countOrdPaymentRefs(OrdPaymentRefCriteria criteria) {
        log.debug("REST request to count OrdPaymentRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordPaymentRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-payment-refs/:id} : get the "id" ordPaymentRef.
     *
     * @param id the id of the ordPaymentRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordPaymentRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-payment-refs/{id}")
    public ResponseEntity<OrdPaymentRefDTO> getOrdPaymentRef(@PathVariable Long id) {
        log.debug("REST request to get OrdPaymentRef : {}", id);
        Optional<OrdPaymentRefDTO> ordPaymentRefDTO = ordPaymentRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordPaymentRefDTO);
    }

    /**
     * {@code DELETE  /ord-payment-refs/:id} : delete the "id" ordPaymentRef.
     *
     * @param id the id of the ordPaymentRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-payment-refs/{id}")
    public ResponseEntity<Void> deleteOrdPaymentRef(@PathVariable Long id) {
        log.debug("REST request to delete OrdPaymentRef : {}", id);
        ordPaymentRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
