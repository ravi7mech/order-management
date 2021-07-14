package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdProductOfferingRefRepository;
import com.apptium.order.service.OrdProductOfferingRefQueryService;
import com.apptium.order.service.OrdProductOfferingRefService;
import com.apptium.order.service.criteria.OrdProductOfferingRefCriteria;
import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdProductOfferingRef}.
 */
@RestController
@RequestMapping("/api")
public class OrdProductOfferingRefResource {

    private final Logger log = LoggerFactory.getLogger(OrdProductOfferingRefResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdProductOfferingRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdProductOfferingRefService ordProductOfferingRefService;

    private final OrdProductOfferingRefRepository ordProductOfferingRefRepository;

    private final OrdProductOfferingRefQueryService ordProductOfferingRefQueryService;

    public OrdProductOfferingRefResource(
        OrdProductOfferingRefService ordProductOfferingRefService,
        OrdProductOfferingRefRepository ordProductOfferingRefRepository,
        OrdProductOfferingRefQueryService ordProductOfferingRefQueryService
    ) {
        this.ordProductOfferingRefService = ordProductOfferingRefService;
        this.ordProductOfferingRefRepository = ordProductOfferingRefRepository;
        this.ordProductOfferingRefQueryService = ordProductOfferingRefQueryService;
    }

    /**
     * {@code POST  /ord-product-offering-refs} : Create a new ordProductOfferingRef.
     *
     * @param ordProductOfferingRefDTO the ordProductOfferingRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordProductOfferingRefDTO, or with status {@code 400 (Bad Request)} if the ordProductOfferingRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-product-offering-refs")
    public ResponseEntity<OrdProductOfferingRefDTO> createOrdProductOfferingRef(
        @RequestBody OrdProductOfferingRefDTO ordProductOfferingRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrdProductOfferingRef : {}", ordProductOfferingRefDTO);
        if (ordProductOfferingRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordProductOfferingRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdProductOfferingRefDTO result = ordProductOfferingRefService.save(ordProductOfferingRefDTO);
        return ResponseEntity
            .created(new URI("/api/ord-product-offering-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-product-offering-refs/:id} : Updates an existing ordProductOfferingRef.
     *
     * @param id the id of the ordProductOfferingRefDTO to save.
     * @param ordProductOfferingRefDTO the ordProductOfferingRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductOfferingRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductOfferingRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordProductOfferingRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-product-offering-refs/{id}")
    public ResponseEntity<OrdProductOfferingRefDTO> updateOrdProductOfferingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductOfferingRefDTO ordProductOfferingRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdProductOfferingRef : {}, {}", id, ordProductOfferingRefDTO);
        if (ordProductOfferingRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductOfferingRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductOfferingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdProductOfferingRefDTO result = ordProductOfferingRefService.save(ordProductOfferingRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductOfferingRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-product-offering-refs/:id} : Partial updates given fields of an existing ordProductOfferingRef, field will ignore if it is null
     *
     * @param id the id of the ordProductOfferingRefDTO to save.
     * @param ordProductOfferingRefDTO the ordProductOfferingRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductOfferingRefDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductOfferingRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordProductOfferingRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordProductOfferingRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-product-offering-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdProductOfferingRefDTO> partialUpdateOrdProductOfferingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductOfferingRefDTO ordProductOfferingRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdProductOfferingRef partially : {}, {}", id, ordProductOfferingRefDTO);
        if (ordProductOfferingRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductOfferingRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductOfferingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdProductOfferingRefDTO> result = ordProductOfferingRefService.partialUpdate(ordProductOfferingRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductOfferingRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-product-offering-refs} : get all the ordProductOfferingRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordProductOfferingRefs in body.
     */
    @GetMapping("/ord-product-offering-refs")
    public ResponseEntity<List<OrdProductOfferingRefDTO>> getAllOrdProductOfferingRefs(OrdProductOfferingRefCriteria criteria) {
        log.debug("REST request to get OrdProductOfferingRefs by criteria: {}", criteria);
        List<OrdProductOfferingRefDTO> entityList = ordProductOfferingRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-product-offering-refs/count} : count all the ordProductOfferingRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-product-offering-refs/count")
    public ResponseEntity<Long> countOrdProductOfferingRefs(OrdProductOfferingRefCriteria criteria) {
        log.debug("REST request to count OrdProductOfferingRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordProductOfferingRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-product-offering-refs/:id} : get the "id" ordProductOfferingRef.
     *
     * @param id the id of the ordProductOfferingRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordProductOfferingRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-product-offering-refs/{id}")
    public ResponseEntity<OrdProductOfferingRefDTO> getOrdProductOfferingRef(@PathVariable Long id) {
        log.debug("REST request to get OrdProductOfferingRef : {}", id);
        Optional<OrdProductOfferingRefDTO> ordProductOfferingRefDTO = ordProductOfferingRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordProductOfferingRefDTO);
    }

    /**
     * {@code DELETE  /ord-product-offering-refs/:id} : delete the "id" ordProductOfferingRef.
     *
     * @param id the id of the ordProductOfferingRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-product-offering-refs/{id}")
    public ResponseEntity<Void> deleteOrdProductOfferingRef(@PathVariable Long id) {
        log.debug("REST request to delete OrdProductOfferingRef : {}", id);
        ordProductOfferingRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
