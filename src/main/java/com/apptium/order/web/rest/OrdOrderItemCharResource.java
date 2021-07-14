package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdOrderItemCharRepository;
import com.apptium.order.service.OrdOrderItemCharQueryService;
import com.apptium.order.service.OrdOrderItemCharService;
import com.apptium.order.service.criteria.OrdOrderItemCharCriteria;
import com.apptium.order.service.dto.OrdOrderItemCharDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdOrderItemChar}.
 */
@RestController
@RequestMapping("/api")
public class OrdOrderItemCharResource {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemCharResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdOrderItemChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdOrderItemCharService ordOrderItemCharService;

    private final OrdOrderItemCharRepository ordOrderItemCharRepository;

    private final OrdOrderItemCharQueryService ordOrderItemCharQueryService;

    public OrdOrderItemCharResource(
        OrdOrderItemCharService ordOrderItemCharService,
        OrdOrderItemCharRepository ordOrderItemCharRepository,
        OrdOrderItemCharQueryService ordOrderItemCharQueryService
    ) {
        this.ordOrderItemCharService = ordOrderItemCharService;
        this.ordOrderItemCharRepository = ordOrderItemCharRepository;
        this.ordOrderItemCharQueryService = ordOrderItemCharQueryService;
    }

    /**
     * {@code POST  /ord-order-item-chars} : Create a new ordOrderItemChar.
     *
     * @param ordOrderItemCharDTO the ordOrderItemCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordOrderItemCharDTO, or with status {@code 400 (Bad Request)} if the ordOrderItemChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-order-item-chars")
    public ResponseEntity<OrdOrderItemCharDTO> createOrdOrderItemChar(@RequestBody OrdOrderItemCharDTO ordOrderItemCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdOrderItemChar : {}", ordOrderItemCharDTO);
        if (ordOrderItemCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordOrderItemChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdOrderItemCharDTO result = ordOrderItemCharService.save(ordOrderItemCharDTO);
        return ResponseEntity
            .created(new URI("/api/ord-order-item-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-order-item-chars/:id} : Updates an existing ordOrderItemChar.
     *
     * @param id the id of the ordOrderItemCharDTO to save.
     * @param ordOrderItemCharDTO the ordOrderItemCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-order-item-chars/{id}")
    public ResponseEntity<OrdOrderItemCharDTO> updateOrdOrderItemChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemCharDTO ordOrderItemCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdOrderItemChar : {}, {}", id, ordOrderItemCharDTO);
        if (ordOrderItemCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdOrderItemCharDTO result = ordOrderItemCharService.save(ordOrderItemCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-order-item-chars/:id} : Partial updates given fields of an existing ordOrderItemChar, field will ignore if it is null
     *
     * @param id the id of the ordOrderItemCharDTO to save.
     * @param ordOrderItemCharDTO the ordOrderItemCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordOrderItemCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-order-item-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdOrderItemCharDTO> partialUpdateOrdOrderItemChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemCharDTO ordOrderItemCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdOrderItemChar partially : {}, {}", id, ordOrderItemCharDTO);
        if (ordOrderItemCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdOrderItemCharDTO> result = ordOrderItemCharService.partialUpdate(ordOrderItemCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-order-item-chars} : get all the ordOrderItemChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordOrderItemChars in body.
     */
    @GetMapping("/ord-order-item-chars")
    public ResponseEntity<List<OrdOrderItemCharDTO>> getAllOrdOrderItemChars(OrdOrderItemCharCriteria criteria) {
        log.debug("REST request to get OrdOrderItemChars by criteria: {}", criteria);
        List<OrdOrderItemCharDTO> entityList = ordOrderItemCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-order-item-chars/count} : count all the ordOrderItemChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-order-item-chars/count")
    public ResponseEntity<Long> countOrdOrderItemChars(OrdOrderItemCharCriteria criteria) {
        log.debug("REST request to count OrdOrderItemChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordOrderItemCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-order-item-chars/:id} : get the "id" ordOrderItemChar.
     *
     * @param id the id of the ordOrderItemCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordOrderItemCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-order-item-chars/{id}")
    public ResponseEntity<OrdOrderItemCharDTO> getOrdOrderItemChar(@PathVariable Long id) {
        log.debug("REST request to get OrdOrderItemChar : {}", id);
        Optional<OrdOrderItemCharDTO> ordOrderItemCharDTO = ordOrderItemCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordOrderItemCharDTO);
    }

    /**
     * {@code DELETE  /ord-order-item-chars/:id} : delete the "id" ordOrderItemChar.
     *
     * @param id the id of the ordOrderItemCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-order-item-chars/{id}")
    public ResponseEntity<Void> deleteOrdOrderItemChar(@PathVariable Long id) {
        log.debug("REST request to delete OrdOrderItemChar : {}", id);
        ordOrderItemCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
