package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdCharacteristicsRepository;
import com.apptium.order.service.OrdCharacteristicsQueryService;
import com.apptium.order.service.OrdCharacteristicsService;
import com.apptium.order.service.criteria.OrdCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdCharacteristicsDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdCharacteristics}.
 */
@RestController
@RequestMapping("/api")
public class OrdCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(OrdCharacteristicsResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdCharacteristics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdCharacteristicsService ordCharacteristicsService;

    private final OrdCharacteristicsRepository ordCharacteristicsRepository;

    private final OrdCharacteristicsQueryService ordCharacteristicsQueryService;

    public OrdCharacteristicsResource(
        OrdCharacteristicsService ordCharacteristicsService,
        OrdCharacteristicsRepository ordCharacteristicsRepository,
        OrdCharacteristicsQueryService ordCharacteristicsQueryService
    ) {
        this.ordCharacteristicsService = ordCharacteristicsService;
        this.ordCharacteristicsRepository = ordCharacteristicsRepository;
        this.ordCharacteristicsQueryService = ordCharacteristicsQueryService;
    }

    /**
     * {@code POST  /ord-characteristics} : Create a new ordCharacteristics.
     *
     * @param ordCharacteristicsDTO the ordCharacteristicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordCharacteristicsDTO, or with status {@code 400 (Bad Request)} if the ordCharacteristics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-characteristics")
    public ResponseEntity<OrdCharacteristicsDTO> createOrdCharacteristics(@RequestBody OrdCharacteristicsDTO ordCharacteristicsDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdCharacteristics : {}", ordCharacteristicsDTO);
        if (ordCharacteristicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordCharacteristics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdCharacteristicsDTO result = ordCharacteristicsService.save(ordCharacteristicsDTO);
        return ResponseEntity
            .created(new URI("/api/ord-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-characteristics/:id} : Updates an existing ordCharacteristics.
     *
     * @param id the id of the ordCharacteristicsDTO to save.
     * @param ordCharacteristicsDTO the ordCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordCharacteristicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-characteristics/{id}")
    public ResponseEntity<OrdCharacteristicsDTO> updateOrdCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdCharacteristicsDTO ordCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdCharacteristics : {}, {}", id, ordCharacteristicsDTO);
        if (ordCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdCharacteristicsDTO result = ordCharacteristicsService.save(ordCharacteristicsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordCharacteristicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-characteristics/:id} : Partial updates given fields of an existing ordCharacteristics, field will ignore if it is null
     *
     * @param id the id of the ordCharacteristicsDTO to save.
     * @param ordCharacteristicsDTO the ordCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordCharacteristicsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordCharacteristicsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-characteristics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdCharacteristicsDTO> partialUpdateOrdCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdCharacteristicsDTO ordCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdCharacteristics partially : {}, {}", id, ordCharacteristicsDTO);
        if (ordCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdCharacteristicsDTO> result = ordCharacteristicsService.partialUpdate(ordCharacteristicsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordCharacteristicsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-characteristics} : get all the ordCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordCharacteristics in body.
     */
    @GetMapping("/ord-characteristics")
    public ResponseEntity<List<OrdCharacteristicsDTO>> getAllOrdCharacteristics(OrdCharacteristicsCriteria criteria) {
        log.debug("REST request to get OrdCharacteristics by criteria: {}", criteria);
        List<OrdCharacteristicsDTO> entityList = ordCharacteristicsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-characteristics/count} : count all the ordCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-characteristics/count")
    public ResponseEntity<Long> countOrdCharacteristics(OrdCharacteristicsCriteria criteria) {
        log.debug("REST request to count OrdCharacteristics by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordCharacteristicsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-characteristics/:id} : get the "id" ordCharacteristics.
     *
     * @param id the id of the ordCharacteristicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordCharacteristicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-characteristics/{id}")
    public ResponseEntity<OrdCharacteristicsDTO> getOrdCharacteristics(@PathVariable Long id) {
        log.debug("REST request to get OrdCharacteristics : {}", id);
        Optional<OrdCharacteristicsDTO> ordCharacteristicsDTO = ordCharacteristicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordCharacteristicsDTO);
    }

    /**
     * {@code DELETE  /ord-characteristics/:id} : delete the "id" ordCharacteristics.
     *
     * @param id the id of the ordCharacteristicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-characteristics/{id}")
    public ResponseEntity<Void> deleteOrdCharacteristics(@PathVariable Long id) {
        log.debug("REST request to delete OrdCharacteristics : {}", id);
        ordCharacteristicsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
