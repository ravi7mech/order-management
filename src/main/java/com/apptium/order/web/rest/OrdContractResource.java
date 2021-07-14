package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdContractRepository;
import com.apptium.order.service.OrdContractQueryService;
import com.apptium.order.service.OrdContractService;
import com.apptium.order.service.criteria.OrdContractCriteria;
import com.apptium.order.service.dto.OrdContractDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdContract}.
 */
@RestController
@RequestMapping("/api")
public class OrdContractResource {

    private final Logger log = LoggerFactory.getLogger(OrdContractResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdContractService ordContractService;

    private final OrdContractRepository ordContractRepository;

    private final OrdContractQueryService ordContractQueryService;

    public OrdContractResource(
        OrdContractService ordContractService,
        OrdContractRepository ordContractRepository,
        OrdContractQueryService ordContractQueryService
    ) {
        this.ordContractService = ordContractService;
        this.ordContractRepository = ordContractRepository;
        this.ordContractQueryService = ordContractQueryService;
    }

    /**
     * {@code POST  /ord-contracts} : Create a new ordContract.
     *
     * @param ordContractDTO the ordContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordContractDTO, or with status {@code 400 (Bad Request)} if the ordContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-contracts")
    public ResponseEntity<OrdContractDTO> createOrdContract(@RequestBody OrdContractDTO ordContractDTO) throws URISyntaxException {
        log.debug("REST request to save OrdContract : {}", ordContractDTO);
        if (ordContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdContractDTO result = ordContractService.save(ordContractDTO);
        return ResponseEntity
            .created(new URI("/api/ord-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-contracts/:id} : Updates an existing ordContract.
     *
     * @param id the id of the ordContractDTO to save.
     * @param ordContractDTO the ordContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContractDTO,
     * or with status {@code 400 (Bad Request)} if the ordContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-contracts/{id}")
    public ResponseEntity<OrdContractDTO> updateOrdContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContractDTO ordContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdContract : {}, {}", id, ordContractDTO);
        if (ordContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdContractDTO result = ordContractService.save(ordContractDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-contracts/:id} : Partial updates given fields of an existing ordContract, field will ignore if it is null
     *
     * @param id the id of the ordContractDTO to save.
     * @param ordContractDTO the ordContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordContractDTO,
     * or with status {@code 400 (Bad Request)} if the ordContractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordContractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-contracts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdContractDTO> partialUpdateOrdContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdContractDTO ordContractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdContract partially : {}, {}", id, ordContractDTO);
        if (ordContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordContractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdContractDTO> result = ordContractService.partialUpdate(ordContractDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordContractDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-contracts} : get all the ordContracts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordContracts in body.
     */
    @GetMapping("/ord-contracts")
    public ResponseEntity<List<OrdContractDTO>> getAllOrdContracts(OrdContractCriteria criteria) {
        log.debug("REST request to get OrdContracts by criteria: {}", criteria);
        List<OrdContractDTO> entityList = ordContractQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-contracts/count} : count all the ordContracts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-contracts/count")
    public ResponseEntity<Long> countOrdContracts(OrdContractCriteria criteria) {
        log.debug("REST request to count OrdContracts by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordContractQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-contracts/:id} : get the "id" ordContract.
     *
     * @param id the id of the ordContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-contracts/{id}")
    public ResponseEntity<OrdContractDTO> getOrdContract(@PathVariable Long id) {
        log.debug("REST request to get OrdContract : {}", id);
        Optional<OrdContractDTO> ordContractDTO = ordContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordContractDTO);
    }

    /**
     * {@code DELETE  /ord-contracts/:id} : delete the "id" ordContract.
     *
     * @param id the id of the ordContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-contracts/{id}")
    public ResponseEntity<Void> deleteOrdContract(@PathVariable Long id) {
        log.debug("REST request to delete OrdContract : {}", id);
        ordContractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
