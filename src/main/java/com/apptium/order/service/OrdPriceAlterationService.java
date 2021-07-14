package com.apptium.order.service;

import com.apptium.order.service.dto.OrdPriceAlterationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdPriceAlteration}.
 */
public interface OrdPriceAlterationService {
    /**
     * Save a ordPriceAlteration.
     *
     * @param ordPriceAlterationDTO the entity to save.
     * @return the persisted entity.
     */
    OrdPriceAlterationDTO save(OrdPriceAlterationDTO ordPriceAlterationDTO);

    /**
     * Partially updates a ordPriceAlteration.
     *
     * @param ordPriceAlterationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdPriceAlterationDTO> partialUpdate(OrdPriceAlterationDTO ordPriceAlterationDTO);

    /**
     * Get all the ordPriceAlterations.
     *
     * @return the list of entities.
     */
    List<OrdPriceAlterationDTO> findAll();

    /**
     * Get the "id" ordPriceAlteration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdPriceAlterationDTO> findOne(Long id);

    /**
     * Delete the "id" ordPriceAlteration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
