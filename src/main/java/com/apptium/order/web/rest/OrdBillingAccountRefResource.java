package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdBillingAccountRefRepository;
import com.apptium.order.service.OrdBillingAccountRefQueryService;
import com.apptium.order.service.OrdBillingAccountRefService;
import com.apptium.order.service.criteria.OrdBillingAccountRefCriteria;
import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdBillingAccountRef}.
 */
@RestController
@RequestMapping("/api")
public class OrdBillingAccountRefResource {

    private final Logger log = LoggerFactory.getLogger(OrdBillingAccountRefResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdBillingAccountRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdBillingAccountRefService ordBillingAccountRefService;

    private final OrdBillingAccountRefRepository ordBillingAccountRefRepository;

    private final OrdBillingAccountRefQueryService ordBillingAccountRefQueryService;

    public OrdBillingAccountRefResource(
        OrdBillingAccountRefService ordBillingAccountRefService,
        OrdBillingAccountRefRepository ordBillingAccountRefRepository,
        OrdBillingAccountRefQueryService ordBillingAccountRefQueryService
    ) {
        this.ordBillingAccountRefService = ordBillingAccountRefService;
        this.ordBillingAccountRefRepository = ordBillingAccountRefRepository;
        this.ordBillingAccountRefQueryService = ordBillingAccountRefQueryService;
    }

    /**
     * {@code POST  /ord-billing-account-refs} : Create a new ordBillingAccountRef.
     *
     * @param ordBillingAccountRefDTO the ordBillingAccountRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordBillingAccountRefDTO, or with status {@code 400 (Bad Request)} if the ordBillingAccountRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-billing-account-refs")
    public ResponseEntity<OrdBillingAccountRefDTO> createOrdBillingAccountRef(@RequestBody OrdBillingAccountRefDTO ordBillingAccountRefDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdBillingAccountRef : {}", ordBillingAccountRefDTO);
        if (ordBillingAccountRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordBillingAccountRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdBillingAccountRefDTO result = ordBillingAccountRefService.save(ordBillingAccountRefDTO);
        return ResponseEntity
            .created(new URI("/api/ord-billing-account-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-billing-account-refs/:id} : Updates an existing ordBillingAccountRef.
     *
     * @param id the id of the ordBillingAccountRefDTO to save.
     * @param ordBillingAccountRefDTO the ordBillingAccountRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordBillingAccountRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordBillingAccountRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordBillingAccountRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-billing-account-refs/{id}")
    public ResponseEntity<OrdBillingAccountRefDTO> updateOrdBillingAccountRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdBillingAccountRefDTO ordBillingAccountRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdBillingAccountRef : {}, {}", id, ordBillingAccountRefDTO);
        if (ordBillingAccountRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordBillingAccountRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordBillingAccountRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdBillingAccountRefDTO result = ordBillingAccountRefService.save(ordBillingAccountRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordBillingAccountRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-billing-account-refs/:id} : Partial updates given fields of an existing ordBillingAccountRef, field will ignore if it is null
     *
     * @param id the id of the ordBillingAccountRefDTO to save.
     * @param ordBillingAccountRefDTO the ordBillingAccountRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordBillingAccountRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordBillingAccountRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordBillingAccountRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordBillingAccountRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-billing-account-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdBillingAccountRefDTO> partialUpdateOrdBillingAccountRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdBillingAccountRefDTO ordBillingAccountRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdBillingAccountRef partially : {}, {}", id, ordBillingAccountRefDTO);
        if (ordBillingAccountRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordBillingAccountRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordBillingAccountRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdBillingAccountRefDTO> result = ordBillingAccountRefService.partialUpdate(ordBillingAccountRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordBillingAccountRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-billing-account-refs} : get all the ordBillingAccountRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordBillingAccountRefs in body.
     */
    @GetMapping("/ord-billing-account-refs")
    public ResponseEntity<List<OrdBillingAccountRefDTO>> getAllOrdBillingAccountRefs(OrdBillingAccountRefCriteria criteria) {
        log.debug("REST request to get OrdBillingAccountRefs by criteria: {}", criteria);
        List<OrdBillingAccountRefDTO> entityList = ordBillingAccountRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-billing-account-refs/count} : count all the ordBillingAccountRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-billing-account-refs/count")
    public ResponseEntity<Long> countOrdBillingAccountRefs(OrdBillingAccountRefCriteria criteria) {
        log.debug("REST request to count OrdBillingAccountRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordBillingAccountRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-billing-account-refs/:id} : get the "id" ordBillingAccountRef.
     *
     * @param id the id of the ordBillingAccountRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordBillingAccountRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-billing-account-refs/{id}")
    public ResponseEntity<OrdBillingAccountRefDTO> getOrdBillingAccountRef(@PathVariable Long id) {
        log.debug("REST request to get OrdBillingAccountRef : {}", id);
        Optional<OrdBillingAccountRefDTO> ordBillingAccountRefDTO = ordBillingAccountRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordBillingAccountRefDTO);
    }

    /**
     * {@code DELETE  /ord-billing-account-refs/:id} : delete the "id" ordBillingAccountRef.
     *
     * @param id the id of the ordBillingAccountRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-billing-account-refs/{id}")
    public ResponseEntity<Void> deleteOrdBillingAccountRef(@PathVariable Long id) {
        log.debug("REST request to delete OrdBillingAccountRef : {}", id);
        ordBillingAccountRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
