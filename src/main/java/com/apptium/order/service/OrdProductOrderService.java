package com.apptium.order.service;

import com.apptium.order.service.dto.OrdProductOrderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdProductOrder}.
 */
public interface OrdProductOrderService {
    /**
     * Save a ordProductOrder.
     *
     * @param ordProductOrderDTO the entity to save.
     * @return the persisted entity.
     */
    OrdProductOrderDTO save(OrdProductOrderDTO ordProductOrderDTO);

    /**
     * Partially updates a ordProductOrder.
     *
     * @param ordProductOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdProductOrderDTO> partialUpdate(OrdProductOrderDTO ordProductOrderDTO);

    /**
     * Get all the ordProductOrders.
     *
     * @return the list of entities.
     */
    List<OrdProductOrderDTO> findAll();

    /**
     * Get the "id" ordProductOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdProductOrderDTO> findOne(Long id);

    /**
     * Delete the "id" ordProductOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
