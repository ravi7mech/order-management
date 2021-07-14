package com.apptium.order.service;

import com.apptium.order.service.dto.OrdOrderPriceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdOrderPrice}.
 */
public interface OrdOrderPriceService {
    /**
     * Save a ordOrderPrice.
     *
     * @param ordOrderPriceDTO the entity to save.
     * @return the persisted entity.
     */
    OrdOrderPriceDTO save(OrdOrderPriceDTO ordOrderPriceDTO);

    /**
     * Partially updates a ordOrderPrice.
     *
     * @param ordOrderPriceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdOrderPriceDTO> partialUpdate(OrdOrderPriceDTO ordOrderPriceDTO);

    /**
     * Get all the ordOrderPrices.
     *
     * @return the list of entities.
     */
    List<OrdOrderPriceDTO> findAll();
    /**
     * Get all the OrdOrderPriceDTO where OrdProductOrder is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdOrderPriceDTO> findAllWhereOrdProductOrderIsNull();
    /**
     * Get all the OrdOrderPriceDTO where OrdOrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdOrderPriceDTO> findAllWhereOrdOrderItemIsNull();

    /**
     * Get the "id" ordOrderPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdOrderPriceDTO> findOne(Long id);

    /**
     * Delete the "id" ordOrderPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
