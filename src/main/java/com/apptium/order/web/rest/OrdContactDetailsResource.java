package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdContactDetailsRepository;
import com.apptium.order.service.OrdContactDetailsQueryService;
import com.apptium.order.service.OrdContactDetailsService;
import com.apptium.order.service.criteria.OrdContactDetailsCriteria;
import com.apptium.order.service.dto.OrdContactDetailsDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdContactDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrdContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrdContactDetailsResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdContactDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdContactDetailsService ordContactDetailsService;

    private final OrdContactDetailsRepository ordContactDetailsRepository;

    private final OrdContactDetailsQueryService ordContactDetailsQueryService;

    public OrdContactDetailsResource(
        OrdContactDetailsService ordContactDetailsService,
        OrdContactDetailsRepository ordContactDetailsRepository,
        OrdContactDetailsQueryService ordContactDetailsQueryService
    ) {
        this.ordContactDetailsService = ordContactDetailsService;
        this.ordContactDetailsRepository = ordContactDetailsRepository;
        this.ordContactDetailsQueryService = ordContactDetailsQueryService;
    }

    /**
     * {@code POST  /ord-contact-details} : Create a new ordContactDetails.
     *
     * @param ordContactDetailsDTO the ordContactDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordContactDetailsDTO, or with status {@code 400 (Bad Request)} if the ordContactDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-contact-details")
    public ResponseEntity<OrdContactDetailsDTO> createOrdContactDetails(@RequestBody OrdContactDetailsDTO ordContactDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdContactDetails : {}", ordContactDetailsDTO);
        if (ordContactDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordContactDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdContactDetailsDTO result = ordContactDetailsService.save(ordContactDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/ord-contact-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-contact-details/:id} : Updates an existing ordContactDetails.
     *
     * @param id the id of the ordContactDetailsDTO to save.
     * @param ordContactDetailsDTO the ordContactDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContactDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the ordContactDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordContactDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-contact-details/{id}")
    public ResponseEntity<OrdContactDetailsDTO> updateOrdContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContactDetailsDTO ordContactDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdContactDetails : {}, {}", id, ordContactDetailsDTO);
        if (ordContactDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContactDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdContactDetailsDTO result = ordContactDetailsService.save(ordContactDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContactDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-contact-details/:id} : Partial updates given fields of an existing ordContactDetails, field will ignore if it is null
     *
     * @param id the id of the ordContactDetailsDTO to save.
     * @param ordContactDetailsDTO the ordContactDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContactDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the ordContactDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordContactDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordContactDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-contact-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdContactDetailsDTO> partialUpdateOrdContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContactDetailsDTO ordContactDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdContactDetails partially : {}, {}", id, ordContactDetailsDTO);
        if (ordContactDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContactDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdContactDetailsDTO> result = ordContactDetailsService.partialUpdate(ordContactDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContactDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-contact-details} : get all the ordContactDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordContactDetails in body.
     */
    @GetMapping("/ord-contact-details")
    public ResponseEntity<List<OrdContactDetailsDTO>> getAllOrdContactDetails(OrdContactDetailsCriteria criteria) {
        log.debug("REST request to get OrdContactDetails by criteria: {}", criteria);
        List<OrdContactDetailsDTO> entityList = ordContactDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-contact-details/count} : count all the ordContactDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-contact-details/count")
    public ResponseEntity<Long> countOrdContactDetails(OrdContactDetailsCriteria criteria) {
        log.debug("REST request to count OrdContactDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordContactDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-contact-details/:id} : get the "id" ordContactDetails.
     *
     * @param id the id of the ordContactDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordContactDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-contact-details/{id}")
    public ResponseEntity<OrdContactDetailsDTO> getOrdContactDetails(@PathVariable Long id) {
        log.debug("REST request to get OrdContactDetails : {}", id);
        Optional<OrdContactDetailsDTO> ordContactDetailsDTO = ordContactDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordContactDetailsDTO);
    }

    /**
     * {@code DELETE  /ord-contact-details/:id} : delete the "id" ordContactDetails.
     *
     * @param id the id of the ordContactDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-contact-details/{id}")
    public ResponseEntity<Void> deleteOrdContactDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrdContactDetails : {}", id);
        ordContactDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
