package com.apptium.order.service;

import com.apptium.order.service.dto.OrdPriceAmountDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdPriceAmount}.
 */
public interface OrdPriceAmountService {
    /**
     * Save a ordPriceAmount.
     *
     * @param ordPriceAmountDTO the entity to save.
     * @return the persisted entity.
     */
    OrdPriceAmountDTO save(OrdPriceAmountDTO ordPriceAmountDTO);

    /**
     * Partially updates a ordPriceAmount.
     *
     * @param ordPriceAmountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdPriceAmountDTO> partialUpdate(OrdPriceAmountDTO ordPriceAmountDTO);

    /**
     * Get all the ordPriceAmounts.
     *
     * @return the list of entities.
     */
    List<OrdPriceAmountDTO> findAll();
    /**
     * Get all the OrdPriceAmountDTO where OrdOrderPrice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdPriceAmountDTO> findAllWhereOrdOrderPriceIsNull();
    /**
     * Get all the OrdPriceAmountDTO where OrdPriceAlteration is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdPriceAmountDTO> findAllWhereOrdPriceAlterationIsNull();

    /**
     * Get the "id" ordPriceAmount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdPriceAmountDTO> findOne(Long id);

    /**
     * Delete the "id" ordPriceAmount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
