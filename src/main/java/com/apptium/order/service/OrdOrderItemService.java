package com.apptium.order.service;

import com.apptium.order.service.dto.OrdOrderItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdOrderItem}.
 */
public interface OrdOrderItemService {
    /**
     * Save a ordOrderItem.
     *
     * @param ordOrderItemDTO the entity to save.
     * @return the persisted entity.
     */
    OrdOrderItemDTO save(OrdOrderItemDTO ordOrderItemDTO);

    /**
     * Partially updates a ordOrderItem.
     *
     * @param ordOrderItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdOrderItemDTO> partialUpdate(OrdOrderItemDTO ordOrderItemDTO);

    /**
     * Get all the ordOrderItems.
     *
     * @return the list of entities.
     */
    List<OrdOrderItemDTO> findAll();

    /**
     * Get the "id" ordOrderItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdOrderItemDTO> findOne(Long id);

    /**
     * Delete the "id" ordOrderItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
