package com.apptium.order.service;

import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdFulfillmentChar}.
 */
public interface OrdFulfillmentCharService {
    /**
     * Save a ordFulfillmentChar.
     *
     * @param ordFulfillmentCharDTO the entity to save.
     * @return the persisted entity.
     */
    OrdFulfillmentCharDTO save(OrdFulfillmentCharDTO ordFulfillmentCharDTO);

    /**
     * Partially updates a ordFulfillmentChar.
     *
     * @param ordFulfillmentCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdFulfillmentCharDTO> partialUpdate(OrdFulfillmentCharDTO ordFulfillmentCharDTO);

    /**
     * Get all the ordFulfillmentChars.
     *
     * @return the list of entities.
     */
    List<OrdFulfillmentCharDTO> findAll();

    /**
     * Get the "id" ordFulfillmentChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdFulfillmentCharDTO> findOne(Long id);

    /**
     * Delete the "id" ordFulfillmentChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
