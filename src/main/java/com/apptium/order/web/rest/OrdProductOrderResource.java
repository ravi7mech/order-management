package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdProductOrderRepository;
import com.apptium.order.service.OrdProductOrderQueryService;
import com.apptium.order.service.OrdProductOrderService;
import com.apptium.order.service.criteria.OrdProductOrderCriteria;
import com.apptium.order.service.dto.OrdProductOrderDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdProductOrder}.
 */
@RestController
@RequestMapping("/api")
public class OrdProductOrderResource {

    private final Logger log = LoggerFactory.getLogger(OrdProductOrderResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdProductOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdProductOrderService ordProductOrderService;

    private final OrdProductOrderRepository ordProductOrderRepository;

    private final OrdProductOrderQueryService ordProductOrderQueryService;

    public OrdProductOrderResource(
        OrdProductOrderService ordProductOrderService,
        OrdProductOrderRepository ordProductOrderRepository,
        OrdProductOrderQueryService ordProductOrderQueryService
    ) {
        this.ordProductOrderService = ordProductOrderService;
        this.ordProductOrderRepository = ordProductOrderRepository;
        this.ordProductOrderQueryService = ordProductOrderQueryService;
    }

    /**
     * {@code POST  /ord-product-orders} : Create a new ordProductOrder.
     *
     * @param ordProductOrderDTO the ordProductOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordProductOrderDTO, or with status {@code 400 (Bad Request)} if the ordProductOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-product-orders")
    public ResponseEntity<OrdProductOrderDTO> createOrdProductOrder(@RequestBody OrdProductOrderDTO ordProductOrderDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrdProductOrder : {}", ordProductOrderDTO);
        if (ordProductOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordProductOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdProductOrderDTO result = ordProductOrderService.save(ordProductOrderDTO);
        return ResponseEntity
            .created(new URI("/api/ord-product-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-product-orders/:id} : Updates an existing ordProductOrder.
     *
     * @param id the id of the ordProductOrderDTO to save.
     * @param ordProductOrderDTO the ordProductOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductOrderDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordProductOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-product-orders/{id}")
    public ResponseEntity<OrdProductOrderDTO> updateOrdProductOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductOrderDTO ordProductOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdProductOrder : {}, {}", id, ordProductOrderDTO);
        if (ordProductOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdProductOrderDTO result = ordProductOrderService.save(ordProductOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-product-orders/:id} : Partial updates given fields of an existing ordProductOrder, field will ignore if it is null
     *
     * @param id the id of the ordProductOrderDTO to save.
     * @param ordProductOrderDTO the ordProductOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductOrderDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordProductOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordProductOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-product-orders/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdProductOrderDTO> partialUpdateOrdProductOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductOrderDTO ordProductOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdProductOrder partially : {}, {}", id, ordProductOrderDTO);
        if (ordProductOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdProductOrderDTO> result = ordProductOrderService.partialUpdate(ordProductOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-product-orders} : get all the ordProductOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordProductOrders in body.
     */
    @GetMapping("/ord-product-orders")
    public ResponseEntity<List<OrdProductOrderDTO>> getAllOrdProductOrders(OrdProductOrderCriteria criteria) {
        log.debug("REST request to get OrdProductOrders by criteria: {}", criteria);
        List<OrdProductOrderDTO> entityList = ordProductOrderQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-product-orders/count} : count all the ordProductOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-product-orders/count")
    public ResponseEntity<Long> countOrdProductOrders(OrdProductOrderCriteria criteria) {
        log.debug("REST request to count OrdProductOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordProductOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-product-orders/:id} : get the "id" ordProductOrder.
     *
     * @param id the id of the ordProductOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordProductOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-product-orders/{id}")
    public ResponseEntity<OrdProductOrderDTO> getOrdProductOrder(@PathVariable Long id) {
        log.debug("REST request to get OrdProductOrder : {}", id);
        Optional<OrdProductOrderDTO> ordProductOrderDTO = ordProductOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordProductOrderDTO);
    }

    /**
     * {@code DELETE  /ord-product-orders/:id} : delete the "id" ordProductOrder.
     *
     * @param id the id of the ordProductOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-product-orders/{id}")
    public ResponseEntity<Void> deleteOrdProductOrder(@PathVariable Long id) {
        log.debug("REST request to delete OrdProductOrder : {}", id);
        ordProductOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
