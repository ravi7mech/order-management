package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdFulfillmentCharRepository;
import com.apptium.order.service.OrdFulfillmentCharQueryService;
import com.apptium.order.service.OrdFulfillmentCharService;
import com.apptium.order.service.criteria.OrdFulfillmentCharCriteria;
import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdFulfillmentChar}.
 */
@RestController
@RequestMapping("/api")
public class OrdFulfillmentCharResource {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentCharResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdFulfillmentChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdFulfillmentCharService ordFulfillmentCharService;

    private final OrdFulfillmentCharRepository ordFulfillmentCharRepository;

    private final OrdFulfillmentCharQueryService ordFulfillmentCharQueryService;

    public OrdFulfillmentCharResource(
        OrdFulfillmentCharService ordFulfillmentCharService,
        OrdFulfillmentCharRepository ordFulfillmentCharRepository,
        OrdFulfillmentCharQueryService ordFulfillmentCharQueryService
    ) {
        this.ordFulfillmentCharService = ordFulfillmentCharService;
        this.ordFulfillmentCharRepository = ordFulfillmentCharRepository;
        this.ordFulfillmentCharQueryService = ordFulfillmentCharQueryService;
    }

    /**
     * {@code POST  /ord-fulfillment-chars} : Create a new ordFulfillmentChar.
     *
     * @param ordFulfillmentCharDTO the ordFulfillmentCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordFulfillmentCharDTO, or with status {@code 400 (Bad Request)} if the ordFulfillmentChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-fulfillment-chars")
    public ResponseEntity<OrdFulfillmentCharDTO> createOrdFulfillmentChar(@RequestBody OrdFulfillmentCharDTO ordFulfillmentCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdFulfillmentChar : {}", ordFulfillmentCharDTO);
        if (ordFulfillmentCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordFulfillmentChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdFulfillmentCharDTO result = ordFulfillmentCharService.save(ordFulfillmentCharDTO);
        return ResponseEntity
            .created(new URI("/api/ord-fulfillment-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-fulfillment-chars/:id} : Updates an existing ordFulfillmentChar.
     *
     * @param id the id of the ordFulfillmentCharDTO to save.
     * @param ordFulfillmentCharDTO the ordFulfillmentCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordFulfillmentCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordFulfillmentCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordFulfillmentCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-fulfillment-chars/{id}")
    public ResponseEntity<OrdFulfillmentCharDTO> updateOrdFulfillmentChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdFulfillmentCharDTO ordFulfillmentCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdFulfillmentChar : {}, {}", id, ordFulfillmentCharDTO);
        if (ordFulfillmentCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordFulfillmentCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordFulfillmentCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdFulfillmentCharDTO result = ordFulfillmentCharService.save(ordFulfillmentCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordFulfillmentCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-fulfillment-chars/:id} : Partial updates given fields of an existing ordFulfillmentChar, field will ignore if it is null
     *
     * @param id the id of the ordFulfillmentCharDTO to save.
     * @param ordFulfillmentCharDTO the ordFulfillmentCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordFulfillmentCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordFulfillmentCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordFulfillmentCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordFulfillmentCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-fulfillment-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdFulfillmentCharDTO> partialUpdateOrdFulfillmentChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdFulfillmentCharDTO ordFulfillmentCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdFulfillmentChar partially : {}, {}", id, ordFulfillmentCharDTO);
        if (ordFulfillmentCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordFulfillmentCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordFulfillmentCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdFulfillmentCharDTO> result = ordFulfillmentCharService.partialUpdate(ordFulfillmentCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordFulfillmentCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-fulfillment-chars} : get all the ordFulfillmentChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordFulfillmentChars in body.
     */
    @GetMapping("/ord-fulfillment-chars")
    public ResponseEntity<List<OrdFulfillmentCharDTO>> getAllOrdFulfillmentChars(OrdFulfillmentCharCriteria criteria) {
        log.debug("REST request to get OrdFulfillmentChars by criteria: {}", criteria);
        List<OrdFulfillmentCharDTO> entityList = ordFulfillmentCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-fulfillment-chars/count} : count all the ordFulfillmentChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-fulfillment-chars/count")
    public ResponseEntity<Long> countOrdFulfillmentChars(OrdFulfillmentCharCriteria criteria) {
        log.debug("REST request to count OrdFulfillmentChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordFulfillmentCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-fulfillment-chars/:id} : get the "id" ordFulfillmentChar.
     *
     * @param id the id of the ordFulfillmentCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordFulfillmentCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-fulfillment-chars/{id}")
    public ResponseEntity<OrdFulfillmentCharDTO> getOrdFulfillmentChar(@PathVariable Long id) {
        log.debug("REST request to get OrdFulfillmentChar : {}", id);
        Optional<OrdFulfillmentCharDTO> ordFulfillmentCharDTO = ordFulfillmentCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordFulfillmentCharDTO);
    }

    /**
     * {@code DELETE  /ord-fulfillment-chars/:id} : delete the "id" ordFulfillmentChar.
     *
     * @param id the id of the ordFulfillmentCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-fulfillment-chars/{id}")
    public ResponseEntity<Void> deleteOrdFulfillmentChar(@PathVariable Long id) {
        log.debug("REST request to delete OrdFulfillmentChar : {}", id);
        ordFulfillmentCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
