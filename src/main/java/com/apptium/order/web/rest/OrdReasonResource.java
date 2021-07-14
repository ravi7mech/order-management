package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdReasonRepository;
import com.apptium.order.service.OrdReasonQueryService;
import com.apptium.order.service.OrdReasonService;
import com.apptium.order.service.criteria.OrdReasonCriteria;
import com.apptium.order.service.dto.OrdReasonDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdReason}.
 */
@RestController
@RequestMapping("/api")
public class OrdReasonResource {

    private final Logger log = LoggerFactory.getLogger(OrdReasonResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdReason";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdReasonService ordReasonService;

    private final OrdReasonRepository ordReasonRepository;

    private final OrdReasonQueryService ordReasonQueryService;

    public OrdReasonResource(
        OrdReasonService ordReasonService,
        OrdReasonRepository ordReasonRepository,
        OrdReasonQueryService ordReasonQueryService
    ) {
        this.ordReasonService = ordReasonService;
        this.ordReasonRepository = ordReasonRepository;
        this.ordReasonQueryService = ordReasonQueryService;
    }

    /**
     * {@code POST  /ord-reasons} : Create a new ordReason.
     *
     * @param ordReasonDTO the ordReasonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordReasonDTO, or with status {@code 400 (Bad Request)} if the ordReason has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-reasons")
    public ResponseEntity<OrdReasonDTO> createOrdReason(@RequestBody OrdReasonDTO ordReasonDTO) throws URISyntaxException {
        log.debug("REST request to save OrdReason : {}", ordReasonDTO);
        if (ordReasonDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordReason cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdReasonDTO result = ordReasonService.save(ordReasonDTO);
        return ResponseEntity
            .created(new URI("/api/ord-reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-reasons/:id} : Updates an existing ordReason.
     *
     * @param id the id of the ordReasonDTO to save.
     * @param ordReasonDTO the ordReasonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordReasonDTO,
     * or with status {@code 400 (Bad Request)} if the ordReasonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordReasonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-reasons/{id}")
    public ResponseEntity<OrdReasonDTO> updateOrdReason(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdReasonDTO ordReasonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdReason : {}, {}", id, ordReasonDTO);
        if (ordReasonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordReasonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordReasonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdReasonDTO result = ordReasonService.save(ordReasonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordReasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-reasons/:id} : Partial updates given fields of an existing ordReason, field will ignore if it is null
     *
     * @param id the id of the ordReasonDTO to save.
     * @param ordReasonDTO the ordReasonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordReasonDTO,
     * or with status {@code 400 (Bad Request)} if the ordReasonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordReasonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordReasonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-reasons/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdReasonDTO> partialUpdateOrdReason(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdReasonDTO ordReasonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdReason partially : {}, {}", id, ordReasonDTO);
        if (ordReasonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordReasonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordReasonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdReasonDTO> result = ordReasonService.partialUpdate(ordReasonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordReasonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-reasons} : get all the ordReasons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordReasons in body.
     */
    @GetMapping("/ord-reasons")
    public ResponseEntity<List<OrdReasonDTO>> getAllOrdReasons(OrdReasonCriteria criteria) {
        log.debug("REST request to get OrdReasons by criteria: {}", criteria);
        List<OrdReasonDTO> entityList = ordReasonQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-reasons/count} : count all the ordReasons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-reasons/count")
    public ResponseEntity<Long> countOrdReasons(OrdReasonCriteria criteria) {
        log.debug("REST request to count OrdReasons by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordReasonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-reasons/:id} : get the "id" ordReason.
     *
     * @param id the id of the ordReasonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordReasonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-reasons/{id}")
    public ResponseEntity<OrdReasonDTO> getOrdReason(@PathVariable Long id) {
        log.debug("REST request to get OrdReason : {}", id);
        Optional<OrdReasonDTO> ordReasonDTO = ordReasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordReasonDTO);
    }

    /**
     * {@code DELETE  /ord-reasons/:id} : delete the "id" ordReason.
     *
     * @param id the id of the ordReasonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-reasons/{id}")
    public ResponseEntity<Void> deleteOrdReason(@PathVariable Long id) {
        log.debug("REST request to delete OrdReason : {}", id);
        ordReasonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
