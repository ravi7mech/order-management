package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdPlaceRepository;
import com.apptium.order.service.OrdPlaceQueryService;
import com.apptium.order.service.OrdPlaceService;
import com.apptium.order.service.criteria.OrdPlaceCriteria;
import com.apptium.order.service.dto.OrdPlaceDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdPlace}.
 */
@RestController
@RequestMapping("/api")
public class OrdPlaceResource {

    private final Logger log = LoggerFactory.getLogger(OrdPlaceResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdPlaceService ordPlaceService;

    private final OrdPlaceRepository ordPlaceRepository;

    private final OrdPlaceQueryService ordPlaceQueryService;

    public OrdPlaceResource(
        OrdPlaceService ordPlaceService,
        OrdPlaceRepository ordPlaceRepository,
        OrdPlaceQueryService ordPlaceQueryService
    ) {
        this.ordPlaceService = ordPlaceService;
        this.ordPlaceRepository = ordPlaceRepository;
        this.ordPlaceQueryService = ordPlaceQueryService;
    }

    /**
     * {@code POST  /ord-places} : Create a new ordPlace.
     *
     * @param ordPlaceDTO the ordPlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordPlaceDTO, or with status {@code 400 (Bad Request)} if the ordPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-places")
    public ResponseEntity<OrdPlaceDTO> createOrdPlace(@RequestBody OrdPlaceDTO ordPlaceDTO) throws URISyntaxException {
        log.debug("REST request to save OrdPlace : {}", ordPlaceDTO);
        if (ordPlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdPlaceDTO result = ordPlaceService.save(ordPlaceDTO);
        return ResponseEntity
            .created(new URI("/api/ord-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-places/:id} : Updates an existing ordPlace.
     *
     * @param id the id of the ordPlaceDTO to save.
     * @param ordPlaceDTO the ordPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the ordPlaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-places/{id}")
    public ResponseEntity<OrdPlaceDTO> updateOrdPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPlaceDTO ordPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdPlace : {}, {}", id, ordPlaceDTO);
        if (ordPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdPlaceDTO result = ordPlaceService.save(ordPlaceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPlaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-places/:id} : Partial updates given fields of an existing ordPlace, field will ignore if it is null
     *
     * @param id the id of the ordPlaceDTO to save.
     * @param ordPlaceDTO the ordPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the ordPlaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordPlaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-places/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdPlaceDTO> partialUpdateOrdPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdPlaceDTO ordPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdPlace partially : {}, {}", id, ordPlaceDTO);
        if (ordPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdPlaceDTO> result = ordPlaceService.partialUpdate(ordPlaceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordPlaceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-places} : get all the ordPlaces.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordPlaces in body.
     */
    @GetMapping("/ord-places")
    public ResponseEntity<List<OrdPlaceDTO>> getAllOrdPlaces(OrdPlaceCriteria criteria) {
        log.debug("REST request to get OrdPlaces by criteria: {}", criteria);
        List<OrdPlaceDTO> entityList = ordPlaceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-places/count} : count all the ordPlaces.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-places/count")
    public ResponseEntity<Long> countOrdPlaces(OrdPlaceCriteria criteria) {
        log.debug("REST request to count OrdPlaces by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordPlaceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-places/:id} : get the "id" ordPlace.
     *
     * @param id the id of the ordPlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordPlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-places/{id}")
    public ResponseEntity<OrdPlaceDTO> getOrdPlace(@PathVariable Long id) {
        log.debug("REST request to get OrdPlace : {}", id);
        Optional<OrdPlaceDTO> ordPlaceDTO = ordPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordPlaceDTO);
    }

    /**
     * {@code DELETE  /ord-places/:id} : delete the "id" ordPlace.
     *
     * @param id the id of the ordPlaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-places/{id}")
    public ResponseEntity<Void> deleteOrdPlace(@PathVariable Long id) {
        log.debug("REST request to delete OrdPlace : {}", id);
        ordPlaceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
