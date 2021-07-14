package com.apptium.order.web.rest;

import com.apptium.order.repository.OrdNoteRepository;
import com.apptium.order.service.OrdNoteQueryService;
import com.apptium.order.service.OrdNoteService;
import com.apptium.order.service.criteria.OrdNoteCriteria;
import com.apptium.order.service.dto.OrdNoteDTO;
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
 * REST controller for managing {@link com.apptium.order.domain.OrdNote}.
 */
@RestController
@RequestMapping("/api")
public class OrdNoteResource {

    private final Logger log = LoggerFactory.getLogger(OrdNoteResource.class);

    private static final String ENTITY_NAME = "orderManagementOrdNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdNoteService ordNoteService;

    private final OrdNoteRepository ordNoteRepository;

    private final OrdNoteQueryService ordNoteQueryService;

    public OrdNoteResource(OrdNoteService ordNoteService, OrdNoteRepository ordNoteRepository, OrdNoteQueryService ordNoteQueryService) {
        this.ordNoteService = ordNoteService;
        this.ordNoteRepository = ordNoteRepository;
        this.ordNoteQueryService = ordNoteQueryService;
    }

    /**
     * {@code POST  /ord-notes} : Create a new ordNote.
     *
     * @param ordNoteDTO the ordNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordNoteDTO, or with status {@code 400 (Bad Request)} if the ordNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ord-notes")
    public ResponseEntity<OrdNoteDTO> createOrdNote(@RequestBody OrdNoteDTO ordNoteDTO) throws URISyntaxException {
        log.debug("REST request to save OrdNote : {}", ordNoteDTO);
        if (ordNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdNoteDTO result = ordNoteService.save(ordNoteDTO);
        return ResponseEntity
            .created(new URI("/api/ord-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ord-notes/:id} : Updates an existing ordNote.
     *
     * @param id the id of the ordNoteDTO to save.
     * @param ordNoteDTO the ordNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordNoteDTO,
     * or with status {@code 400 (Bad Request)} if the ordNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ord-notes/{id}")
    public ResponseEntity<OrdNoteDTO> updateOrdNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdNoteDTO ordNoteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrdNote : {}, {}", id, ordNoteDTO);
        if (ordNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdNoteDTO result = ordNoteService.save(ordNoteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordNoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ord-notes/:id} : Partial updates given fields of an existing ordNote, field will ignore if it is null
     *
     * @param id the id of the ordNoteDTO to save.
     * @param ordNoteDTO the ordNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordNoteDTO,
     * or with status {@code 400 (Bad Request)} if the ordNoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordNoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ord-notes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrdNoteDTO> partialUpdateOrdNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdNoteDTO ordNoteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdNote partially : {}, {}", id, ordNoteDTO);
        if (ordNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdNoteDTO> result = ordNoteService.partialUpdate(ordNoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordNoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ord-notes} : get all the ordNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordNotes in body.
     */
    @GetMapping("/ord-notes")
    public ResponseEntity<List<OrdNoteDTO>> getAllOrdNotes(OrdNoteCriteria criteria) {
        log.debug("REST request to get OrdNotes by criteria: {}", criteria);
        List<OrdNoteDTO> entityList = ordNoteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ord-notes/count} : count all the ordNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ord-notes/count")
    public ResponseEntity<Long> countOrdNotes(OrdNoteCriteria criteria) {
        log.debug("REST request to count OrdNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ord-notes/:id} : get the "id" ordNote.
     *
     * @param id the id of the ordNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ord-notes/{id}")
    public ResponseEntity<OrdNoteDTO> getOrdNote(@PathVariable Long id) {
        log.debug("REST request to get OrdNote : {}", id);
        Optional<OrdNoteDTO> ordNoteDTO = ordNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordNoteDTO);
    }

    /**
     * {@code DELETE  /ord-notes/:id} : delete the "id" ordNote.
     *
     * @param id the id of the ordNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ord-notes/{id}")
    public ResponseEntity<Void> deleteOrdNote(@PathVariable Long id) {
        log.debug("REST request to delete OrdNote : {}", id);
        ordNoteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
