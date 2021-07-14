package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdPriceAmountRepository;
import com.apptium.order.service.OrdPriceAmountQueryService;
import com.apptium.order.service.OrdPriceAmountService;
import com.apptium.order.service.criteria.OrdPriceAmountCriteria;
import com.apptium.order.service.dto.OrdPriceAmountDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdPriceAmount}.
 */
@RestController
@RequestMapping("/api")
public class OrdPriceAmountResource {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAmountResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdPriceAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdPriceAmountService ordPriceAmountService;

    private final OrdPriceAmountRepository ordPriceAmountRepository;

    private final OrdPriceAmountQueryService ordPriceAmountQueryService;

    public OrdPriceAmountResource(
        OrdPriceAmountService ordPriceAmountService,
        OrdPriceAmountRepository ordPriceAmountRepository,
        OrdPriceAmountQueryService ordPriceAmountQueryService
    ) {
        this.ordPriceAmountService = ordPriceAmountService;
        this.ordPriceAmountRepository = ordPriceAmountRepository;
        this.ordPriceAmountQueryService = ordPriceAmountQueryService;
    }

    /**
     * {@code POST  /ord-price-amounts} : Create a new ordPriceAmount.
     *
     * @param ordPriceAmountDTO the ordPriceAmountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordPriceAmountDTO, or with status {@code 400 (Bad Request)} if the ordPriceAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-price-amounts")
    public ResponseEntity<OrdPriceAmountDTO> createOrdPriceAmount(@RequestBody OrdPriceAmountDTO ordPriceAmountDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdPriceAmount : {}", ordPriceAmountDTO);
        if (ordPriceAmountDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordPriceAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdPriceAmountDTO result = ordPriceAmountService.save(ordPriceAmountDTO);
        return ResponseEntity
            .created(new URI("/api/ord-price-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-price-amounts/:id} : Updates an existing ordPriceAmount.
     *
     * @param id the id of the ordPriceAmountDTO to save.
     * @param ordPriceAmountDTO the ordPriceAmountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPriceAmountDTO,
     * or with status {@code 400 (Bad Request)} if the ordPriceAmountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordPriceAmountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-price-amounts/{id}")
    public ResponseEntity<OrdPriceAmountDTO> updateOrdPriceAmount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPriceAmountDTO ordPriceAmountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdPriceAmount : {}, {}", id, ordPriceAmountDTO);
        if (ordPriceAmountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPriceAmountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPriceAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdPriceAmountDTO result = ordPriceAmountService.save(ordPriceAmountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPriceAmountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-price-amounts/:id} : Partial updates given fields of an existing ordPriceAmount, field will ignore if it is null
     *
     * @param id the id of the ordPriceAmountDTO to save.
     * @param ordPriceAmountDTO the ordPriceAmountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPriceAmountDTO,
     * or with status {@code 400 (Bad Request)} if the ordPriceAmountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordPriceAmountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordPriceAmountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-price-amounts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdPriceAmountDTO> partialUpdateOrdPriceAmount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPriceAmountDTO ordPriceAmountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdPriceAmount partially : {}, {}", id, ordPriceAmountDTO);
        if (ordPriceAmountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPriceAmountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPriceAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdPriceAmountDTO> result = ordPriceAmountService.partialUpdate(ordPriceAmountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPriceAmountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-price-amounts} : get all the ordPriceAmounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordPriceAmounts in body.
     */
    @GetMapping("/ord-price-amounts")
    public ResponseEntity<List<OrdPriceAmountDTO>> getAllOrdPriceAmounts(OrdPriceAmountCriteria criteria) {
        log.debug("REST request to get OrdPriceAmounts by criteria: {}", criteria);
        List<OrdPriceAmountDTO> entityList = ordPriceAmountQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-price-amounts/count} : count all the ordPriceAmounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-price-amounts/count")
    public ResponseEntity<Long> countOrdPriceAmounts(OrdPriceAmountCriteria criteria) {
        log.debug("REST request to count OrdPriceAmounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordPriceAmountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-price-amounts/:id} : get the "id" ordPriceAmount.
     *
     * @param id the id of the ordPriceAmountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordPriceAmountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-price-amounts/{id}")
    public ResponseEntity<OrdPriceAmountDTO> getOrdPriceAmount(@PathVariable Long id) {
        log.debug("REST request to get OrdPriceAmount : {}", id);
        Optional<OrdPriceAmountDTO> ordPriceAmountDTO = ordPriceAmountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordPriceAmountDTO);
    }

    /**
     * {@code DELETE  /ord-price-amounts/:id} : delete the "id" ordPriceAmount.
     *
     * @param id the id of the ordPriceAmountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-price-amounts/{id}")
    public ResponseEntity<Void> deleteOrdPriceAmount(@PathVariable Long id) {
        log.debug("REST request to delete OrdPriceAmount : {}", id);
        ordPriceAmountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
