package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdContractCharacteristicsRepository;
import com.apptium.order.service.OrdContractCharacteristicsQueryService;
import com.apptium.order.service.OrdContractCharacteristicsService;
import com.apptium.order.service.criteria.OrdContractCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdContractCharacteristics}.
 */
@RestController
@RequestMapping("/api")
public class OrdContractCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(OrdContractCharacteristicsResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdContractCharacteristics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdContractCharacteristicsService ordContractCharacteristicsService;

    private final OrdContractCharacteristicsRepository ordContractCharacteristicsRepository;

    private final OrdContractCharacteristicsQueryService ordContractCharacteristicsQueryService;

    public OrdContractCharacteristicsResource(
        OrdContractCharacteristicsService ordContractCharacteristicsService,
        OrdContractCharacteristicsRepository ordContractCharacteristicsRepository,
        OrdContractCharacteristicsQueryService ordContractCharacteristicsQueryService
    ) {
        this.ordContractCharacteristicsService = ordContractCharacteristicsService;
        this.ordContractCharacteristicsRepository = ordContractCharacteristicsRepository;
        this.ordContractCharacteristicsQueryService = ordContractCharacteristicsQueryService;
    }

    /**
     * {@code POST  /ord-contract-characteristics} : Create a new ordContractCharacteristics.
     *
     * @param ordContractCharacteristicsDTO the ordContractCharacteristicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordContractCharacteristicsDTO, or with status {@code 400 (Bad Request)} if the ordContractCharacteristics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-contract-characteristics")
    public ResponseEntity<OrdContractCharacteristicsDTO> createOrdContractCharacteristics(
        @RequestBody OrdContractCharacteristicsDTO ordContractCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrdContractCharacteristics : {}", ordContractCharacteristicsDTO);
        if (ordContractCharacteristicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordContractCharacteristics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdContractCharacteristicsDTO result = ordContractCharacteristicsService.save(ordContractCharacteristicsDTO);
        return ResponseEntity
            .created(new URI("/api/ord-contract-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-contract-characteristics/:id} : Updates an existing ordContractCharacteristics.
     *
     * @param id the id of the ordContractCharacteristicsDTO to save.
     * @param ordContractCharacteristicsDTO the ordContractCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContractCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordContractCharacteristicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordContractCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-contract-characteristics/{id}")
    public ResponseEntity<OrdContractCharacteristicsDTO> updateOrdContractCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContractCharacteristicsDTO ordContractCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdContractCharacteristics : {}, {}", id, ordContractCharacteristicsDTO);
        if (ordContractCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContractCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContractCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdContractCharacteristicsDTO result = ordContractCharacteristicsService.save(ordContractCharacteristicsDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContractCharacteristicsDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /ord-contract-characteristics/:id} : Partial updates given fields of an existing ordContractCharacteristics, field will ignore if it is null
     *
     * @param id the id of the ordContractCharacteristicsDTO to save.
     * @param ordContractCharacteristicsDTO the ordContractCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContractCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the ordContractCharacteristicsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordContractCharacteristicsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordContractCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-contract-characteristics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdContractCharacteristicsDTO> partialUpdateOrdContractCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContractCharacteristicsDTO ordContractCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdContractCharacteristics partially : {}, {}", id, ordContractCharacteristicsDTO);
        if (ordContractCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContractCharacteristicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContractCharacteristicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdContractCharacteristicsDTO> result = ordContractCharacteristicsService.partialUpdate(ordContractCharacteristicsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContractCharacteristicsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-contract-characteristics} : get all the ordContractCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordContractCharacteristics in body.
     */
    @GetMapping("/ord-contract-characteristics")
    public ResponseEntity<List<OrdContractCharacteristicsDTO>> getAllOrdContractCharacteristics(
        OrdContractCharacteristicsCriteria criteria
    ) {
        log.debug("REST request to get OrdContractCharacteristics by criteria: {}", criteria);
        List<OrdContractCharacteristicsDTO> entityList = ordContractCharacteristicsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-contract-characteristics/count} : count all the ordContractCharacteristics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-contract-characteristics/count")
    public ResponseEntity<Long> countOrdContractCharacteristics(OrdContractCharacteristicsCriteria criteria) {
        log.debug("REST request to count OrdContractCharacteristics by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordContractCharacteristicsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-contract-characteristics/:id} : get the "id" ordContractCharacteristics.
     *
     * @param id the id of the ordContractCharacteristicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordContractCharacteristicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-contract-characteristics/{id}")
    public ResponseEntity<OrdContractCharacteristicsDTO> getOrdContractCharacteristics(@PathVariable Long id) {
        log.debug("REST request to get OrdContractCharacteristics : {}", id);
        Optional<OrdContractCharacteristicsDTO> ordContractCharacteristicsDTO = ordContractCharacteristicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordContractCharacteristicsDTO);
    }

    /**
     * {@code DELETE  /ord-contract-characteristics/:id} : delete the "id" ordContractCharacteristics.
     *
     * @param id the id of the ordContractCharacteristicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-contract-characteristics/{id}")
    public ResponseEntity<Void> deleteOrdContractCharacteristics(@PathVariable Long id) {
        log.debug("REST request to delete OrdContractCharacteristics : {}", id);
        ordContractCharacteristicsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
