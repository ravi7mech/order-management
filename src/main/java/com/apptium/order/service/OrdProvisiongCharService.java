package com.apptium.order.service;

import com.apptium.order.service.dto.OrdProvisiongCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdProvisiongChar}.
 */
public interface OrdProvisiongCharService {
    /**
     * Save a ordProvisiongChar.
     *
     * @param ordProvisiongCharDTO the entity to save.
     * @return the persisted entity.
     */
    OrdProvisiongCharDTO save(OrdProvisiongCharDTO ordProvisiongCharDTO);

    /**
     * Partially updates a ordProvisiongChar.
     *
     * @param ordProvisiongCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdProvisiongCharDTO> partialUpdate(OrdProvisiongCharDTO ordProvisiongCharDTO);

    /**
     * Get all the ordProvisiongChars.
     *
     * @return the list of entities.
     */
    List<OrdProvisiongCharDTO> findAll();

    /**
     * Get the "id" ordProvisiongChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdProvisiongCharDTO> findOne(Long id);

    /**
     * Delete the "id" ordProvisiongChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
