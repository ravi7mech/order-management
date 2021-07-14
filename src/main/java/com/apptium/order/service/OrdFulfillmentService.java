package com.apptium.order.service;

import com.apptium.order.service.dto.OrdFulfillmentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdFulfillment}.
 */
public interface OrdFulfillmentService {
    /**
     * Save a ordFulfillment.
     *
     * @param ordFulfillmentDTO the entity to save.
     * @return the persisted entity.
     */
    OrdFulfillmentDTO save(OrdFulfillmentDTO ordFulfillmentDTO);

    /**
     * Partially updates a ordFulfillment.
     *
     * @param ordFulfillmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdFulfillmentDTO> partialUpdate(OrdFulfillmentDTO ordFulfillmentDTO);

    /**
     * Get all the ordFulfillments.
     *
     * @return the list of entities.
     */
    List<OrdFulfillmentDTO> findAll();

    /**
     * Get the "id" ordFulfillment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdFulfillmentDTO> findOne(Long id);

    /**
     * Delete the "id" ordFulfillment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
