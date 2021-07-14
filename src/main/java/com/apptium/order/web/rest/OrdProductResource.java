package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdProductRepository;
import com.apptium.order.service.OrdProductQueryService;
import com.apptium.order.service.OrdProductService;
import com.apptium.order.service.criteria.OrdProductCriteria;
import com.apptium.order.service.dto.OrdProductDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdProduct}.
 */
@RestController
@RequestMapping("/api")
public class OrdProductResource {

    private final Logger log = LoggerFactory.getLogger(OrdProductResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdProductService ordProductService;

    private final OrdProductRepository ordProductRepository;

    private final OrdProductQueryService ordProductQueryService;

    public OrdProductResource(
        OrdProductService ordProductService,
        OrdProductRepository ordProductRepository,
        OrdProductQueryService ordProductQueryService
    ) {
        this.ordProductService = ordProductService;
        this.ordProductRepository = ordProductRepository;
        this.ordProductQueryService = ordProductQueryService;
    }

    /**
     * {@code POST  /ord-products} : Create a new ordProduct.
     *
     * @param ordProductDTO the ordProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordProductDTO, or with status {@code 400 (Bad Request)} if the ordProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-products")
    public ResponseEntity<OrdProductDTO> createOrdProduct(@RequestBody OrdProductDTO ordProductDTO) throws URISyntaxException {
        log.debug("REST request to save OrdProduct : {}", ordProductDTO);
        if (ordProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdProductDTO result = ordProductService.save(ordProductDTO);
        return ResponseEntity
            .created(new URI("/api/ord-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-products/:id} : Updates an existing ordProduct.
     *
     * @param id the id of the ordProductDTO to save.
     * @param ordProductDTO the ordProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-products/{id}")
    public ResponseEntity<OrdProductDTO> updateOrdProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductDTO ordProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdProduct : {}, {}", id, ordProductDTO);
        if (ordProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdProductDTO result = ordProductService.save(ordProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-products/:id} : Partial updates given fields of an existing ordProduct, field will ignore if it is null
     *
     * @param id the id of the ordProductDTO to save.
     * @param ordProductDTO the ordProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordProductDTO,
     * or with status {@code 400 (Bad Request)} if the ordProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-products/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdProductDTO> partialUpdateOrdProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdProductDTO ordProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdProduct partially : {}, {}", id, ordProductDTO);
        if (ordProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdProductDTO> result = ordProductService.partialUpdate(ordProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-products} : get all the ordProducts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordProducts in body.
     */
    @GetMapping("/ord-products")
    public ResponseEntity<List<OrdProductDTO>> getAllOrdProducts(OrdProductCriteria criteria) {
        log.debug("REST request to get OrdProducts by criteria: {}", criteria);
        List<OrdProductDTO> entityList = ordProductQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-products/count} : count all the ordProducts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-products/count")
    public ResponseEntity<Long> countOrdProducts(OrdProductCriteria criteria) {
        log.debug("REST request to count OrdProducts by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordProductQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-products/:id} : get the "id" ordProduct.
     *
     * @param id the id of the ordProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-products/{id}")
    public ResponseEntity<OrdProductDTO> getOrdProduct(@PathVariable Long id) {
        log.debug("REST request to get OrdProduct : {}", id);
        Optional<OrdProductDTO> ordProductDTO = ordProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordProductDTO);
    }

    /**
     * {@code DELETE  /ord-products/:id} : delete the "id" ordProduct.
     *
     * @param id the id of the ordProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-products/{id}")
    public ResponseEntity<Void> deleteOrdProduct(@PathVariable Long id) {
        log.debug("REST request to delete OrdProduct : {}", id);
        ordProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
