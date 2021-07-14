package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdOrderItemProvisioningRepository;
import com.apptium.order.service.OrdOrderItemProvisioningQueryService;
import com.apptium.order.service.OrdOrderItemProvisioningService;
import com.apptium.order.service.criteria.OrdOrderItemProvisioningCriteria;
import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdOrderItemProvisioning}.
 */
@RestController
@RequestMapping("/api")
public class OrdOrderItemProvisioningResource {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemProvisioningResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdOrderItemProvisioning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdOrderItemProvisioningService ordOrderItemProvisioningService;

    private final OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository;

    private final OrdOrderItemProvisioningQueryService ordOrderItemProvisioningQueryService;

    public OrdOrderItemProvisioningResource(
        OrdOrderItemProvisioningService ordOrderItemProvisioningService,
        OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository,
        OrdOrderItemProvisioningQueryService ordOrderItemProvisioningQueryService
    ) {
        this.ordOrderItemProvisioningService = ordOrderItemProvisioningService;
        this.ordOrderItemProvisioningRepository = ordOrderItemProvisioningRepository;
        this.ordOrderItemProvisioningQueryService = ordOrderItemProvisioningQueryService;
    }

    /**
     * {@code POST  /ord-order-item-provisionings} : Create a new ordOrderItemProvisioning.
     *
     * @param ordOrderItemProvisioningDTO the ordOrderItemProvisioningDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordOrderItemProvisioningDTO, or with status {@code 400 (Bad Request)} if the ordOrderItemProvisioning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-order-item-provisionings")
    public ResponseEntity<OrdOrderItemProvisioningDTO> createOrdOrderItemProvisioning(
        @RequestBody OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrdOrderItemProvisioning : {}", ordOrderItemProvisioningDTO);
        if (ordOrderItemProvisioningDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordOrderItemProvisioning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdOrderItemProvisioningDTO result = ordOrderItemProvisioningService.save(ordOrderItemProvisioningDTO);
        return ResponseEntity
            .created(new URI("/api/ord-order-item-provisionings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-order-item-provisionings/:id} : Updates an existing ordOrderItemProvisioning.
     *
     * @param id the id of the ordOrderItemProvisioningDTO to save.
     * @param ordOrderItemProvisioningDTO the ordOrderItemProvisioningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemProvisioningDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemProvisioningDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemProvisioningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-order-item-provisionings/{id}")
    public ResponseEntity<OrdOrderItemProvisioningDTO> updateOrdOrderItemProvisioning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdOrderItemProvisioning : {}, {}", id, ordOrderItemProvisioningDTO);
        if (ordOrderItemProvisioningDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemProvisioningDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemProvisioningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdOrderItemProvisioningDTO result = ordOrderItemProvisioningService.save(ordOrderItemProvisioningDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemProvisioningDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /ord-order-item-provisionings/:id} : Partial updates given fields of an existing ordOrderItemProvisioning, field will ignore if it is null
     *
     * @param id the id of the ordOrderItemProvisioningDTO to save.
     * @param ordOrderItemProvisioningDTO the ordOrderItemProvisioningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordOrderItemProvisioningDTO,
     * or with status {@code 400 (Bad Request)} if the ordOrderItemProvisioningDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordOrderItemProvisioningDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordOrderItemProvisioningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-order-item-provisionings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdOrderItemProvisioningDTO> partialUpdateOrdOrderItemProvisioning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdOrderItemProvisioning partially : {}, {}", id, ordOrderItemProvisioningDTO);
        if (ordOrderItemProvisioningDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordOrderItemProvisioningDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordOrderItemProvisioningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdOrderItemProvisioningDTO> result = ordOrderItemProvisioningService.partialUpdate(ordOrderItemProvisioningDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordOrderItemProvisioningDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-order-item-provisionings} : get all the ordOrderItemProvisionings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordOrderItemProvisionings in body.
     */
    @GetMapping("/ord-order-item-provisionings")
    public ResponseEntity<List<OrdOrderItemProvisioningDTO>> getAllOrdOrderItemProvisionings(OrdOrderItemProvisioningCriteria criteria) {
        log.debug("REST request to get OrdOrderItemProvisionings by criteria: {}", criteria);
        List<OrdOrderItemProvisioningDTO> entityList = ordOrderItemProvisioningQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-order-item-provisionings/count} : count all the ordOrderItemProvisionings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-order-item-provisionings/count")
    public ResponseEntity<Long> countOrdOrderItemProvisionings(OrdOrderItemProvisioningCriteria criteria) {
        log.debug("REST request to count OrdOrderItemProvisionings by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordOrderItemProvisioningQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-order-item-provisionings/:id} : get the "id" ordOrderItemProvisioning.
     *
     * @param id the id of the ordOrderItemProvisioningDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordOrderItemProvisioningDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-order-item-provisionings/{id}")
    public ResponseEntity<OrdOrderItemProvisioningDTO> getOrdOrderItemProvisioning(@PathVariable Long id) {
        log.debug("REST request to get OrdOrderItemProvisioning : {}", id);
        Optional<OrdOrderItemProvisioningDTO> ordOrderItemProvisioningDTO = ordOrderItemProvisioningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordOrderItemProvisioningDTO);
    }

    /**
     * {@code DELETE  /ord-order-item-provisionings/:id} : delete the "id" ordOrderItemProvisioning.
     *
     * @param id the id of the ordOrderItemProvisioningDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-order-item-provisionings/{id}")
    public ResponseEntity<Void> deleteOrdOrderItemProvisioning(@PathVariable Long id) {
        log.debug("REST request to delete OrdOrderItemProvisioning : {}", id);
        ordOrderItemProvisioningService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
