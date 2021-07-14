package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdOrderPriceRepository;
import com.apptium.order.service.OrdOrderPriceQueryService;
import com.apptium.order.service.OrdOrderPriceService;
import com.apptium.order.service.criteria.OrdOrderPriceCriteria;
import com.apptium.order.service.dto.OrdOrderPriceDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdOrderPrice}.
 */
@RestController
@RequestMapping("/api")
public class OrdOrderPriceResource {

    private final Logger log = LoggerFactory.getLogger(OrdOrderPriceResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdOrderPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdOrderPriceService ordOrderPriceService;

    private final OrdOrderPriceRepository ordOrderPriceRepository;

    private final OrdOrderPriceQueryService ordOrderPriceQueryService;

    public OrdOrderPriceResource(
        OrdOrderPriceService ordOrderPriceService,
        OrdOrderPriceRepository ordOrderPriceRepository,
        OrdOrderPriceQueryService ordOrderPriceQueryService
    ) {
        this.ordOrderPriceService = ordOrderPriceService;
        this.ordOrderPriceRepository = ordOrderPriceRepository;
        this.ordOrderPriceQueryService = ordOrderPriceQueryService;
    }

    /**
     * {@code POST  /ord-order-prices} : Create a new ordOrderPrice.
     *
     * @param ordOrderPriceDTO the ordOrderPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordOrderPriceDTO, or with status {@code 400 (Bad Request)} if the ordOrderPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-order-prices")
    public ResponseEntity<OrdOrderPriceDTO> createOrdOrderPrice(@RequestBody OrdOrderPriceDTO ordOrderPriceDTO) throws URISyntaxException {
        log.debug("REST request to save OrdOrderPrice : {}", ordOrderPriceDTO);
        if (ordOrderPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordOrderPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdOrderPriceDTO result = ordOrderPriceService.save(ordOrderPriceDTO);
        return ResponseEntity
            .created(new URI("/api/ord-order-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-order-prices/:id} : Updates an existing ordOrderPrice.
     *
     * @param id the id of the ordOrderPriceDTO to save.
     * @param ordOrderPriceDTO the ordOrderPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderPriceDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-order-prices/{id}")
    public ResponseEntity<OrdOrderPriceDTO> updateOrdOrderPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderPriceDTO ordOrderPriceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdOrderPrice : {}, {}", id, ordOrderPriceDTO);
        if (ordOrderPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderPriceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdOrderPriceDTO result = ordOrderPriceService.save(ordOrderPriceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-order-prices/:id} : Partial updates given fields of an existing ordOrderPrice, field will ignore if it is null
     *
     * @param id the id of the ordOrderPriceDTO to save.
     * @param ordOrderPriceDTO the ordOrderPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderPriceDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderPriceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordOrderPriceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-order-prices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdOrderPriceDTO> partialUpdateOrdOrderPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderPriceDTO ordOrderPriceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdOrderPrice partially : {}, {}", id, ordOrderPriceDTO);
        if (ordOrderPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderPriceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdOrderPriceDTO> result = ordOrderPriceService.partialUpdate(ordOrderPriceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderPriceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-order-prices} : get all the ordOrderPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordOrderPrices in body.
     */
    @GetMapping("/ord-order-prices")
    public ResponseEntity<List<OrdOrderPriceDTO>> getAllOrdOrderPrices(OrdOrderPriceCriteria criteria) {
        log.debug("REST request to get OrdOrderPrices by criteria: {}", criteria);
        List<OrdOrderPriceDTO> entityList = ordOrderPriceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-order-prices/count} : count all the ordOrderPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-order-prices/count")
    public ResponseEntity<Long> countOrdOrderPrices(OrdOrderPriceCriteria criteria) {
        log.debug("REST request to count OrdOrderPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordOrderPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-order-prices/:id} : get the "id" ordOrderPrice.
     *
     * @param id the id of the ordOrderPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordOrderPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-order-prices/{id}")
    public ResponseEntity<OrdOrderPriceDTO> getOrdOrderPrice(@PathVariable Long id) {
        log.debug("REST request to get OrdOrderPrice : {}", id);
        Optional<OrdOrderPriceDTO> ordOrderPriceDTO = ordOrderPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordOrderPriceDTO);
    }

    /**
     * {@code DELETE  /ord-order-prices/:id} : delete the "id" ordOrderPrice.
     *
     * @param id the id of the ordOrderPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-order-prices/{id}")
    public ResponseEntity<Void> deleteOrdOrderPrice(@PathVariable Long id) {
        log.debug("REST request to delete OrdOrderPrice : {}", id);
        ordOrderPriceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
