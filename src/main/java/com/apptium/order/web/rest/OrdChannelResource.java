package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdChannelRepository;
import com.apptium.order.service.OrdChannelQueryService;
import com.apptium.order.service.OrdChannelService;
import com.apptium.order.service.criteria.OrdChannelCriteria;
import com.apptium.order.service.dto.OrdChannelDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdChannel}.
 */
@RestController
@RequestMapping("/api")
public class OrdChannelResource {

    private final Logger log = LoggerFactory.getLogger(OrdChannelResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdChannelService ordChannelService;

    private final OrdChannelRepository ordChannelRepository;

    private final OrdChannelQueryService ordChannelQueryService;

    public OrdChannelResource(
        OrdChannelService ordChannelService,
        OrdChannelRepository ordChannelRepository,
        OrdChannelQueryService ordChannelQueryService
    ) {
        this.ordChannelService = ordChannelService;
        this.ordChannelRepository = ordChannelRepository;
        this.ordChannelQueryService = ordChannelQueryService;
    }

    /**
     * {@code POST  /ord-channels} : Create a new ordChannel.
     *
     * @param ordChannelDTO the ordChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordChannelDTO, or with status {@code 400 (Bad Request)} if the ordChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-channels")
    public ResponseEntity<OrdChannelDTO> createOrdChannel(@RequestBody OrdChannelDTO ordChannelDTO) throws URISyntaxException {
        log.debug("REST request to save OrdChannel : {}", ordChannelDTO);
        if (ordChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdChannelDTO result = ordChannelService.save(ordChannelDTO);
        return ResponseEntity
            .created(new URI("/api/ord-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-channels/:id} : Updates an existing ordChannel.
     *
     * @param id the id of the ordChannelDTO to save.
     * @param ordChannelDTO the ordChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordChannelDTO,
     * or with status {@code 400 (Bad Request)} if the ordChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-channels/{id}")
    public ResponseEntity<OrdChannelDTO> updateOrdChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdChannelDTO ordChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdChannel : {}, {}", id, ordChannelDTO);
        if (ordChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdChannelDTO result = ordChannelService.save(ordChannelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-channels/:id} : Partial updates given fields of an existing ordChannel, field will ignore if it is null
     *
     * @param id the id of the ordChannelDTO to save.
     * @param ordChannelDTO the ordChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordChannelDTO,
     * or with status {@code 400 (Bad Request)} if the ordChannelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordChannelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-channels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdChannelDTO> partialUpdateOrdChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdChannelDTO ordChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdChannel partially : {}, {}", id, ordChannelDTO);
        if (ordChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdChannelDTO> result = ordChannelService.partialUpdate(ordChannelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordChannelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-channels} : get all the ordChannels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordChannels in body.
     */
    @GetMapping("/ord-channels")
    public ResponseEntity<List<OrdChannelDTO>> getAllOrdChannels(OrdChannelCriteria criteria) {
        log.debug("REST request to get OrdChannels by criteria: {}", criteria);
        List<OrdChannelDTO> entityList = ordChannelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-channels/count} : count all the ordChannels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-channels/count")
    public ResponseEntity<Long> countOrdChannels(OrdChannelCriteria criteria) {
        log.debug("REST request to count OrdChannels by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordChannelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-channels/:id} : get the "id" ordChannel.
     *
     * @param id the id of the ordChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-channels/{id}")
    public ResponseEntity<OrdChannelDTO> getOrdChannel(@PathVariable Long id) {
        log.debug("REST request to get OrdChannel : {}", id);
        Optional<OrdChannelDTO> ordChannelDTO = ordChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordChannelDTO);
    }

    /**
     * {@code DELETE  /ord-channels/:id} : delete the "id" ordChannel.
     *
     * @param id the id of the ordChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-channels/{id}")
    public ResponseEntity<Void> deleteOrdChannel(@PathVariable Long id) {
        log.debug("REST request to delete OrdChannel : {}", id);
        ordChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
