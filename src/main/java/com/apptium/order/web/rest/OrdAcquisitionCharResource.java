package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdAcquisitionCharRepository;
import com.apptium.order.service.OrdAcquisitionCharQueryService;
import com.apptium.order.service.OrdAcquisitionCharService;
import com.apptium.order.service.criteria.OrdAcquisitionCharCriteria;
import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdAcquisitionChar}.
 */
@RestController
@RequestMapping("/api")
public class OrdAcquisitionCharResource {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionCharResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdAcquisitionChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdAcquisitionCharService ordAcquisitionCharService;

    private final OrdAcquisitionCharRepository ordAcquisitionCharRepository;

    private final OrdAcquisitionCharQueryService ordAcquisitionCharQueryService;

    public OrdAcquisitionCharResource(
        OrdAcquisitionCharService ordAcquisitionCharService,
        OrdAcquisitionCharRepository ordAcquisitionCharRepository,
        OrdAcquisitionCharQueryService ordAcquisitionCharQueryService
    ) {
        this.ordAcquisitionCharService = ordAcquisitionCharService;
        this.ordAcquisitionCharRepository = ordAcquisitionCharRepository;
        this.ordAcquisitionCharQueryService = ordAcquisitionCharQueryService;
    }

    /**
     * {@code POST  /ord-acquisition-chars} : Create a new ordAcquisitionChar.
     *
     * @param ordAcquisitionCharDTO the ordAcquisitionCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordAcquisitionCharDTO, or with status {@code 400 (Bad Request)} if the ordAcquisitionChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-acquisition-chars")
    public ResponseEntity<OrdAcquisitionCharDTO> createOrdAcquisitionChar(@RequestBody OrdAcquisitionCharDTO ordAcquisitionCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdAcquisitionChar : {}", ordAcquisitionCharDTO);
        if (ordAcquisitionCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordAcquisitionChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdAcquisitionCharDTO result = ordAcquisitionCharService.save(ordAcquisitionCharDTO);
        return ResponseEntity
            .created(new URI("/api/ord-acquisition-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-acquisition-chars/:id} : Updates an existing ordAcquisitionChar.
     *
     * @param id the id of the ordAcquisitionCharDTO to save.
     * @param ordAcquisitionCharDTO the ordAcquisitionCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordAcquisitionCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordAcquisitionCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordAcquisitionCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-acquisition-chars/{id}")
    public ResponseEntity<OrdAcquisitionCharDTO> updateOrdAcquisitionChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdAcquisitionCharDTO ordAcquisitionCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdAcquisitionChar : {}, {}", id, ordAcquisitionCharDTO);
        if (ordAcquisitionCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordAcquisitionCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordAcquisitionCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdAcquisitionCharDTO result = ordAcquisitionCharService.save(ordAcquisitionCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordAcquisitionCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-acquisition-chars/:id} : Partial updates given fields of an existing ordAcquisitionChar, field will ignore if it is null
     *
     * @param id the id of the ordAcquisitionCharDTO to save.
     * @param ordAcquisitionCharDTO the ordAcquisitionCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordAcquisitionCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordAcquisitionCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordAcquisitionCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordAcquisitionCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-acquisition-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdAcquisitionCharDTO> partialUpdateOrdAcquisitionChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdAcquisitionCharDTO ordAcquisitionCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdAcquisitionChar partially : {}, {}", id, ordAcquisitionCharDTO);
        if (ordAcquisitionCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordAcquisitionCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordAcquisitionCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdAcquisitionCharDTO> result = ordAcquisitionCharService.partialUpdate(ordAcquisitionCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordAcquisitionCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-acquisition-chars} : get all the ordAcquisitionChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordAcquisitionChars in body.
     */
    @GetMapping("/ord-acquisition-chars")
    public ResponseEntity<List<OrdAcquisitionCharDTO>> getAllOrdAcquisitionChars(OrdAcquisitionCharCriteria criteria) {
        log.debug("REST request to get OrdAcquisitionChars by criteria: {}", criteria);
        List<OrdAcquisitionCharDTO> entityList = ordAcquisitionCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-acquisition-chars/count} : count all the ordAcquisitionChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-acquisition-chars/count")
    public ResponseEntity<Long> countOrdAcquisitionChars(OrdAcquisitionCharCriteria criteria) {
        log.debug("REST request to count OrdAcquisitionChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordAcquisitionCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-acquisition-chars/:id} : get the "id" ordAcquisitionChar.
     *
     * @param id the id of the ordAcquisitionCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordAcquisitionCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-acquisition-chars/{id}")
    public ResponseEntity<OrdAcquisitionCharDTO> getOrdAcquisitionChar(@PathVariable Long id) {
        log.debug("REST request to get OrdAcquisitionChar : {}", id);
        Optional<OrdAcquisitionCharDTO> ordAcquisitionCharDTO = ordAcquisitionCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordAcquisitionCharDTO);
    }

    /**
     * {@code DELETE  /ord-acquisition-chars/:id} : delete the "id" ordAcquisitionChar.
     *
     * @param id the id of the ordAcquisitionCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-acquisition-chars/{id}")
    public ResponseEntity<Void> deleteOrdAcquisitionChar(@PathVariable Long id) {
        log.debug("REST request to delete OrdAcquisitionChar : {}", id);
        ordAcquisitionCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
