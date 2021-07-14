package com.apptium.order.service;

import com.apptium.order.service.dto.OrdOrderItemCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdOrderItemChar}.
 */
public interface OrdOrderItemCharService {
    /**
     * Save a ordOrderItemChar.
     *
     * @param ordOrderItemCharDTO the entity to save.
     * @return the persisted entity.
     */
    OrdOrderItemCharDTO save(OrdOrderItemCharDTO ordOrderItemCharDTO);

    /**
     * Partially updates a ordOrderItemChar.
     *
     * @param ordOrderItemCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdOrderItemCharDTO> partialUpdate(OrdOrderItemCharDTO ordOrderItemCharDTO);

    /**
     * Get all the ordOrderItemChars.
     *
     * @return the list of entities.
     */
    List<OrdOrderItemCharDTO> findAll();

    /**
     * Get the "id" ordOrderItemChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdOrderItemCharDTO> findOne(Long id);

    /**
     * Delete the "id" ordOrderItemChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
