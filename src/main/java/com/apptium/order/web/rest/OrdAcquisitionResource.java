package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdAcquisitionRepository;
import com.apptium.order.service.OrdAcquisitionQueryService;
import com.apptium.order.service.OrdAcquisitionService;
import com.apptium.order.service.criteria.OrdAcquisitionCriteria;
import com.apptium.order.service.dto.OrdAcquisitionDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdAcquisition}.
 */
@RestController
@RequestMapping("/api")
public class OrdAcquisitionResource {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdAcquisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdAcquisitionService ordAcquisitionService;

    private final OrdAcquisitionRepository ordAcquisitionRepository;

    private final OrdAcquisitionQueryService ordAcquisitionQueryService;

    public OrdAcquisitionResource(
        OrdAcquisitionService ordAcquisitionService,
        OrdAcquisitionRepository ordAcquisitionRepository,
        OrdAcquisitionQueryService ordAcquisitionQueryService
    ) {
        this.ordAcquisitionService = ordAcquisitionService;
        this.ordAcquisitionRepository = ordAcquisitionRepository;
        this.ordAcquisitionQueryService = ordAcquisitionQueryService;
    }

    /**
     * {@code POST  /ord-acquisitions} : Create a new ordAcquisition.
     *
     * @param ordAcquisitionDTO the ordAcquisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordAcquisitionDTO, or with status {@code 400 (Bad Request)} if the ordAcquisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-acquisitions")
    public ResponseEntity<OrdAcquisitionDTO> createOrdAcquisition(@RequestBody OrdAcquisitionDTO ordAcquisitionDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdAcquisition : {}", ordAcquisitionDTO);
        if (ordAcquisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordAcquisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdAcquisitionDTO result = ordAcquisitionService.save(ordAcquisitionDTO);
        return ResponseEntity
            .created(new URI("/api/ord-acquisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-acquisitions/:id} : Updates an existing ordAcquisition.
     *
     * @param id the id of the ordAcquisitionDTO to save.
     * @param ordAcquisitionDTO the ordAcquisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordAcquisitionDTO,
     * or with status {@code 400 (Bad Request)} if the ordAcquisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordAcquisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-acquisitions/{id}")
    public ResponseEntity<OrdAcquisitionDTO> updateOrdAcquisition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdAcquisitionDTO ordAcquisitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdAcquisition : {}, {}", id, ordAcquisitionDTO);
        if (ordAcquisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordAcquisitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordAcquisitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdAcquisitionDTO result = ordAcquisitionService.save(ordAcquisitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordAcquisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-acquisitions/:id} : Partial updates given fields of an existing ordAcquisition, field will ignore if it is null
     *
     * @param id the id of the ordAcquisitionDTO to save.
     * @param ordAcquisitionDTO the ordAcquisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordAcquisitionDTO,
     * or with status {@code 400 (Bad Request)} if the ordAcquisitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordAcquisitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordAcquisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-acquisitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdAcquisitionDTO> partialUpdateOrdAcquisition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdAcquisitionDTO ordAcquisitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdAcquisition partially : {}, {}", id, ordAcquisitionDTO);
        if (ordAcquisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordAcquisitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordAcquisitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdAcquisitionDTO> result = ordAcquisitionService.partialUpdate(ordAcquisitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordAcquisitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-acquisitions} : get all the ordAcquisitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordAcquisitions in body.
     */
    @GetMapping("/ord-acquisitions")
    public ResponseEntity<List<OrdAcquisitionDTO>> getAllOrdAcquisitions(OrdAcquisitionCriteria criteria) {
        log.debug("REST request to get OrdAcquisitions by criteria: {}", criteria);
        List<OrdAcquisitionDTO> entityList = ordAcquisitionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-acquisitions/count} : count all the ordAcquisitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-acquisitions/count")
    public ResponseEntity<Long> countOrdAcquisitions(OrdAcquisitionCriteria criteria) {
        log.debug("REST request to count OrdAcquisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordAcquisitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-acquisitions/:id} : get the "id" ordAcquisition.
     *
     * @param id the id of the ordAcquisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordAcquisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-acquisitions/{id}")
    public ResponseEntity<OrdAcquisitionDTO> getOrdAcquisition(@PathVariable Long id) {
        log.debug("REST request to get OrdAcquisition : {}", id);
        Optional<OrdAcquisitionDTO> ordAcquisitionDTO = ordAcquisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordAcquisitionDTO);
    }

    /**
     * {@code DELETE  /ord-acquisitions/:id} : delete the "id" ordAcquisition.
     *
     * @param id the id of the ordAcquisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-acquisitions/{id}")
    public ResponseEntity<Void> deleteOrdAcquisition(@PathVariable Long id) {
        log.debug("REST request to delete OrdAcquisition : {}", id);
        ordAcquisitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
