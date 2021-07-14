package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdOrderItemRepository;
import com.apptium.order.service.OrdOrderItemQueryService;
import com.apptium.order.service.OrdOrderItemService;
import com.apptium.order.service.criteria.OrdOrderItemCriteria;
import com.apptium.order.service.dto.OrdOrderItemDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdOrderItem}.
 */
@RestController
@RequestMapping("/api")
public class OrdOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdOrderItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdOrderItemService ordOrderItemService;

    private final OrdOrderItemRepository ordOrderItemRepository;

    private final OrdOrderItemQueryService ordOrderItemQueryService;

    public OrdOrderItemResource(
        OrdOrderItemService ordOrderItemService,
        OrdOrderItemRepository ordOrderItemRepository,
        OrdOrderItemQueryService ordOrderItemQueryService
    ) {
        this.ordOrderItemService = ordOrderItemService;
        this.ordOrderItemRepository = ordOrderItemRepository;
        this.ordOrderItemQueryService = ordOrderItemQueryService;
    }

    /**
     * {@code POST  /ord-order-items} : Create a new ordOrderItem.
     *
     * @param ordOrderItemDTO the ordOrderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordOrderItemDTO, or with status {@code 400 (Bad Request)} if the ordOrderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-order-items")
    public ResponseEntity<OrdOrderItemDTO> createOrdOrderItem(@RequestBody OrdOrderItemDTO ordOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save OrdOrderItem : {}", ordOrderItemDTO);
        if (ordOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdOrderItemDTO result = ordOrderItemService.save(ordOrderItemDTO);
        return ResponseEntity
            .created(new URI("/api/ord-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-order-items/:id} : Updates an existing ordOrderItem.
     *
     * @param id the id of the ordOrderItemDTO to save.
     * @param ordOrderItemDTO the ordOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-order-items/{id}")
    public ResponseEntity<OrdOrderItemDTO> updateOrdOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemDTO ordOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdOrderItem : {}, {}", id, ordOrderItemDTO);
        if (ordOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdOrderItemDTO result = ordOrderItemService.save(ordOrderItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-order-items/:id} : Partial updates given fields of an existing ordOrderItem, field will ignore if it is null
     *
     * @param id the id of the ordOrderItemDTO to save.
     * @param ordOrderItemDTO the ordOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordOrderItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-order-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdOrderItemDTO> partialUpdateOrdOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemDTO ordOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdOrderItem partially : {}, {}", id, ordOrderItemDTO);
        if (ordOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdOrderItemDTO> result = ordOrderItemService.partialUpdate(ordOrderItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-order-items} : get all the ordOrderItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordOrderItems in body.
     */
    @GetMapping("/ord-order-items")
    public ResponseEntity<List<OrdOrderItemDTO>> getAllOrdOrderItems(OrdOrderItemCriteria criteria) {
        log.debug("REST request to get OrdOrderItems by criteria: {}", criteria);
        List<OrdOrderItemDTO> entityList = ordOrderItemQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-order-items/count} : count all the ordOrderItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-order-items/count")
    public ResponseEntity<Long> countOrdOrderItems(OrdOrderItemCriteria criteria) {
        log.debug("REST request to count OrdOrderItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordOrderItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-order-items/:id} : get the "id" ordOrderItem.
     *
     * @param id the id of the ordOrderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordOrderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-order-items/{id}")
    public ResponseEntity<OrdOrderItemDTO> getOrdOrderItem(@PathVariable Long id) {
        log.debug("REST request to get OrdOrderItem : {}", id);
        Optional<OrdOrderItemDTO> ordOrderItemDTO = ordOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordOrderItemDTO);
    }

    /**
     * {@code DELETE  /ord-order-items/:id} : delete the "id" ordOrderItem.
     *
     * @param id the id of the ordOrderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-order-items/{id}")
    public ResponseEntity<Void> deleteOrdOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete OrdOrderItem : {}", id);
        ordOrderItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
