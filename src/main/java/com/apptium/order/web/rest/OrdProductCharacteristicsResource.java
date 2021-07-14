package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdProductCharacteristicsRepository;
import com.apptium.order.service.OrdProductCharacteristicsQueryService;
import com.apptium.order.service.OrdProductCharacteristicsService;
import com.apptium.order.service.criteria.OrdProductCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdProductCharacteristics}.
 */
@RestController
@RequestMapping("/api")
public class OrdProductCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(OrdProductCharacteristicsResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdProductCharacteristics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdProductCharacteristicsService ordProductCharacteristicsService;

    private final OrdProductCharacteristicsRepository ordProductCharacteristicsRepository;

    private final OrdProductCharacteristicsQueryService ordProductCharacteristicsQueryService;

    public OrdProductCharacteristicsResource(
        OrdProductCharacteristicsService ordProductCharacteristicsService,
        OrdProductCharacteristicsRepository ordProductCharacteristicsRepository,
        OrdProductCharacteristicsQueryService ordProductCharacteristicsQueryService
    ) {
        this.ordProductCharacteristicsService = ordProductCharacteristicsService;
        this.ordProductCharacteristicsRepository = ordProductCharacteristicsRepository;
        this.ordProductCharacteristicsQueryService = ordProductCharacteristicsQueryService;
    }

    /**
     * {@code POST  /ord-product-characteristics} : Create a new ordProductCharacteristics.
     *
     * @param ordProductCharacteristicsDTO the ordProductCharacteristicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordProductCharacteristicsDTO, or with status {@code 400 (Bad Request)} if the ordProductCharacteristics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-product-characteristics")
    public ResponseEntity<OrdProductCharacteristicsDTO> createOrdProductCharacteristics(
        @RequestBody OrdProductCharacteristicsDTO ordProductCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrdProductCharacteristics : {}", ordProductCharacteristicsDTO);
        if (ordProductCharacteristicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordProductCharacteristics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdProductCharacteristicsDTO result = ordProductCharacteristicsService.save(ordProductCharacteristicsDTO);
        return ResponseEntity
            .created(new URI("/api/ord-product-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-product-characteristics/:id} : Updates an existing ordProductCharacteristics.
     *
     * @param id the id of the ordProductCharacteristicsDTO to save.
     * @param ordProductCharacteristicsDTO the ordProductCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductCharacteristicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordProductCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-product-characteristics/{id}")
    public ResponseEntity<OrdProductCharacteristicsDTO> updateOrdProductCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductCharacteristicsDTO ordProductCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdProductCharacteristics : {}, {}", id, ordProductCharacteristicsDTO);
        if (ordProductCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdProductCharacteristicsDTO result = ordProductCharacteristicsService.save(ordProductCharacteristicsDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductCharacteristicsDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /ord-product-characteristics/:id} : Partial updates given fields of an existing ordProductCharacteristics, field will ignore if it is null
     *
     * @param id the id of the ordProductCharacteristicsDTO to save.
     * @param ordProductCharacteristicsDTO the ordProductCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductCharacteristicsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordProductCharacteristicsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordProductCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-product-characteristics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdProductCharacteristicsDTO> partialUpdateOrdProductCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductCharacteristicsDTO ordProductCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdProductCharacteristics partially : {}, {}", id, ordProductCharacteristicsDTO);
        if (ordProductCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdProductCharacteristicsDTO> result = ordProductCharacteristicsService.partialUpdate(ordProductCharacteristicsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductCharacteristicsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-product-characteristics} : get all the ordProductCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordProductCharacteristics in body.
     */
    @GetMapping("/ord-product-characteristics")
    public ResponseEntity<List<OrdProductCharacteristicsDTO>> getAllOrdProductCharacteristics(OrdProductCharacteristicsCriteria criteria) {
        log.debug("REST request to get OrdProductCharacteristics by criteria: {}", criteria);
        List<OrdProductCharacteristicsDTO> entityList = ordProductCharacteristicsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-product-characteristics/count} : count all the ordProductCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-product-characteristics/count")
    public ResponseEntity<Long> countOrdProductCharacteristics(OrdProductCharacteristicsCriteria criteria) {
        log.debug("REST request to count OrdProductCharacteristics by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordProductCharacteristicsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-product-characteristics/:id} : get the "id" ordProductCharacteristics.
     *
     * @param id the id of the ordProductCharacteristicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordProductCharacteristicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-product-characteristics/{id}")
    public ResponseEntity<OrdProductCharacteristicsDTO> getOrdProductCharacteristics(@PathVariable Long id) {
        log.debug("REST request to get OrdProductCharacteristics : {}", id);
        Optional<OrdProductCharacteristicsDTO> ordProductCharacteristicsDTO = ordProductCharacteristicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordProductCharacteristicsDTO);
    }

    /**
     * {@code DELETE  /ord-product-characteristics/:id} : delete the "id" ordProductCharacteristics.
     *
     * @param id the id of the ordProductCharacteristicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-product-characteristics/{id}")
    public ResponseEntity<Void> deleteOrdProductCharacteristics(@PathVariable Long id) {
        log.debug("REST request to delete OrdProductCharacteristics : {}", id);
        ordProductCharacteristicsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
