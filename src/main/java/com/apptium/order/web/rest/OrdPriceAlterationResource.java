package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdPriceAlterationRepository;
import com.apptium.order.service.OrdPriceAlterationQueryService;
import com.apptium.order.service.OrdPriceAlterationService;
import com.apptium.order.service.criteria.OrdPriceAlterationCriteria;
import com.apptium.order.service.dto.OrdPriceAlterationDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdPriceAlteration}.
 */
@RestController
@RequestMapping("/api")
public class OrdPriceAlterationResource {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAlterationResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdPriceAlteration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdPriceAlterationService ordPriceAlterationService;

    private final OrdPriceAlterationRepository ordPriceAlterationRepository;

    private final OrdPriceAlterationQueryService ordPriceAlterationQueryService;

    public OrdPriceAlterationResource(
        OrdPriceAlterationService ordPriceAlterationService,
        OrdPriceAlterationRepository ordPriceAlterationRepository,
        OrdPriceAlterationQueryService ordPriceAlterationQueryService
    ) {
        this.ordPriceAlterationService = ordPriceAlterationService;
        this.ordPriceAlterationRepository = ordPriceAlterationRepository;
        this.ordPriceAlterationQueryService = ordPriceAlterationQueryService;
    }

    /**
     * {@code POST  /ord-price-alterations} : Create a new ordPriceAlteration.
     *
     * @param ordPriceAlterationDTO the ordPriceAlterationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordPriceAlterationDTO, or with status {@code 400 (Bad Request)} if the ordPriceAlteration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-price-alterations")
    public ResponseEntity<OrdPriceAlterationDTO> createOrdPriceAlteration(@RequestBody OrdPriceAlterationDTO ordPriceAlterationDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdPriceAlteration : {}", ordPriceAlterationDTO);
        if (ordPriceAlterationDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordPriceAlteration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdPriceAlterationDTO result = ordPriceAlterationService.save(ordPriceAlterationDTO);
        return ResponseEntity
            .created(new URI("/api/ord-price-alterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-price-alterations/:id} : Updates an existing ordPriceAlteration.
     *
     * @param id the id of the ordPriceAlterationDTO to save.
     * @param ordPriceAlterationDTO the ordPriceAlterationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPriceAlterationDTO,
     * or with status {@code 400 (Bad Request)} if the ordPriceAlterationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordPriceAlterationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-price-alterations/{id}")
    public ResponseEntity<OrdPriceAlterationDTO> updateOrdPriceAlteration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPriceAlterationDTO ordPriceAlterationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdPriceAlteration : {}, {}", id, ordPriceAlterationDTO);
        if (ordPriceAlterationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPriceAlterationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPriceAlterationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdPriceAlterationDTO result = ordPriceAlterationService.save(ordPriceAlterationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPriceAlterationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-price-alterations/:id} : Partial updates given fields of an existing ordPriceAlteration, field will ignore if it is null
     *
     * @param id the id of the ordPriceAlterationDTO to save.
     * @param ordPriceAlterationDTO the ordPriceAlterationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPriceAlterationDTO,
     * or with status {@code 400 (Bad Request)} if the ordPriceAlterationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordPriceAlterationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordPriceAlterationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-price-alterations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdPriceAlterationDTO> partialUpdateOrdPriceAlteration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPriceAlterationDTO ordPriceAlterationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdPriceAlteration partially : {}, {}", id, ordPriceAlterationDTO);
        if (ordPriceAlterationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPriceAlterationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPriceAlterationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdPriceAlterationDTO> result = ordPriceAlterationService.partialUpdate(ordPriceAlterationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPriceAlterationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-price-alterations} : get all the ordPriceAlterations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordPriceAlterations in body.
     */
    @GetMapping("/ord-price-alterations")
    public ResponseEntity<List<OrdPriceAlterationDTO>> getAllOrdPriceAlterations(OrdPriceAlterationCriteria criteria) {
        log.debug("REST request to get OrdPriceAlterations by criteria: {}", criteria);
        List<OrdPriceAlterationDTO> entityList = ordPriceAlterationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-price-alterations/count} : count all the ordPriceAlterations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-price-alterations/count")
    public ResponseEntity<Long> countOrdPriceAlterations(OrdPriceAlterationCriteria criteria) {
        log.debug("REST request to count OrdPriceAlterations by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordPriceAlterationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-price-alterations/:id} : get the "id" ordPriceAlteration.
     *
     * @param id the id of the ordPriceAlterationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordPriceAlterationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-price-alterations/{id}")
    public ResponseEntity<OrdPriceAlterationDTO> getOrdPriceAlteration(@PathVariable Long id) {
        log.debug("REST request to get OrdPriceAlteration : {}", id);
        Optional<OrdPriceAlterationDTO> ordPriceAlterationDTO = ordPriceAlterationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordPriceAlterationDTO);
    }

    /**
     * {@code DELETE  /ord-price-alterations/:id} : delete the "id" ordPriceAlteration.
     *
     * @param id the id of the ordPriceAlterationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-price-alterations/{id}")
    public ResponseEntity<Void> deleteOrdPriceAlteration(@PathVariable Long id) {
        log.debug("REST request to delete OrdPriceAlteration : {}", id);
        ordPriceAlterationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
