package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdProvisiongCharRepository;
import com.apptium.order.service.OrdProvisiongCharQueryService;
import com.apptium.order.service.OrdProvisiongCharService;
import com.apptium.order.service.criteria.OrdProvisiongCharCriteria;
import com.apptium.order.service.dto.OrdProvisiongCharDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdProvisiongChar}.
 */
@RestController
@RequestMapping("/api")
public class OrdProvisiongCharResource {

    private final Logger log = LoggerFactory.getLogger(OrdProvisiongCharResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdProvisiongChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdProvisiongCharService ordProvisiongCharService;

    private final OrdProvisiongCharRepository ordProvisiongCharRepository;

    private final OrdProvisiongCharQueryService ordProvisiongCharQueryService;

    public OrdProvisiongCharResource(
        OrdProvisiongCharService ordProvisiongCharService,
        OrdProvisiongCharRepository ordProvisiongCharRepository,
        OrdProvisiongCharQueryService ordProvisiongCharQueryService
    ) {
        this.ordProvisiongCharService = ordProvisiongCharService;
        this.ordProvisiongCharRepository = ordProvisiongCharRepository;
        this.ordProvisiongCharQueryService = ordProvisiongCharQueryService;
    }

    /**
     * {@code POST  /ord-provisiong-chars} : Create a new ordProvisiongChar.
     *
     * @param ordProvisiongCharDTO the ordProvisiongCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordProvisiongCharDTO, or with status {@code 400 (Bad Request)} if the ordProvisiongChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-provisiong-chars")
    public ResponseEntity<OrdProvisiongCharDTO> createOrdProvisiongChar(@RequestBody OrdProvisiongCharDTO ordProvisiongCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdProvisiongChar : {}", ordProvisiongCharDTO);
        if (ordProvisiongCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordProvisiongChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdProvisiongCharDTO result = ordProvisiongCharService.save(ordProvisiongCharDTO);
        return ResponseEntity
            .created(new URI("/api/ord-provisiong-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-provisiong-chars/:id} : Updates an existing ordProvisiongChar.
     *
     * @param id the id of the ordProvisiongCharDTO to save.
     * @param ordProvisiongCharDTO the ordProvisiongCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProvisiongCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordProvisiongCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordProvisiongCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-provisiong-chars/{id}")
    public ResponseEntity<OrdProvisiongCharDTO> updateOrdProvisiongChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProvisiongCharDTO ordProvisiongCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdProvisiongChar : {}, {}", id, ordProvisiongCharDTO);
        if (ordProvisiongCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProvisiongCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProvisiongCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdProvisiongCharDTO result = ordProvisiongCharService.save(ordProvisiongCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProvisiongCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-provisiong-chars/:id} : Partial updates given fields of an existing ordProvisiongChar, field will ignore if it is null
     *
     * @param id the id of the ordProvisiongCharDTO to save.
     * @param ordProvisiongCharDTO the ordProvisiongCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProvisiongCharDTO,
     * or with status {@code 400 (Bad Request)} if the ordProvisiongCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordProvisiongCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordProvisiongCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-provisiong-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdProvisiongCharDTO> partialUpdateOrdProvisiongChar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProvisiongCharDTO ordProvisiongCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdProvisiongChar partially : {}, {}", id, ordProvisiongCharDTO);
        if (ordProvisiongCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProvisiongCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProvisiongCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdProvisiongCharDTO> result = ordProvisiongCharService.partialUpdate(ordProvisiongCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProvisiongCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-provisiong-chars} : get all the ordProvisiongChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordProvisiongChars in body.
     */
    @GetMapping("/ord-provisiong-chars")
    public ResponseEntity<List<OrdProvisiongCharDTO>> getAllOrdProvisiongChars(OrdProvisiongCharCriteria criteria) {
        log.debug("REST request to get OrdProvisiongChars by criteria: {}", criteria);
        List<OrdProvisiongCharDTO> entityList = ordProvisiongCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-provisiong-chars/count} : count all the ordProvisiongChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-provisiong-chars/count")
    public ResponseEntity<Long> countOrdProvisiongChars(OrdProvisiongCharCriteria criteria) {
        log.debug("REST request to count OrdProvisiongChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordProvisiongCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-provisiong-chars/:id} : get the "id" ordProvisiongChar.
     *
     * @param id the id of the ordProvisiongCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordProvisiongCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-provisiong-chars/{id}")
    public ResponseEntity<OrdProvisiongCharDTO> getOrdProvisiongChar(@PathVariable Long id) {
        log.debug("REST request to get OrdProvisiongChar : {}", id);
        Optional<OrdProvisiongCharDTO> ordProvisiongCharDTO = ordProvisiongCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordProvisiongCharDTO);
    }

    /**
     * {@code DELETE  /ord-provisiong-chars/:id} : delete the "id" ordProvisiongChar.
     *
     * @param id the id of the ordProvisiongCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-provisiong-chars/{id}")
    public ResponseEntity<Void> deleteOrdProvisiongChar(@PathVariable Long id) {
        log.debug("REST request to delete OrdProvisiongChar : {}", id);
        ordProvisiongCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
