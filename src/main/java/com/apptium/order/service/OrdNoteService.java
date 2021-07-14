package com.apptium.order.service;

import com.apptium.order.service.dto.OrdNoteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdNote}.
 */
public interface OrdNoteService {
    /**
     * Save a ordNote.
     *
     * @param ordNoteDTO the entity to save.
     * @return the persisted entity.
     */
    OrdNoteDTO save(OrdNoteDTO ordNoteDTO);

    /**
     * Partially updates a ordNote.
     *
     * @param ordNoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdNoteDTO> partialUpdate(OrdNoteDTO ordNoteDTO);

    /**
     * Get all the ordNotes.
     *
     * @return the list of entities.
     */
    List<OrdNoteDTO> findAll();
    /**
     * Get all the OrdNoteDTO where OrdProductOrder is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdNoteDTO> findAllWhereOrdProductOrderIsNull();

    /**
     * Get the "id" ordNote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdNoteDTO> findOne(Long id);

    /**
     * Delete the "id" ordNote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
