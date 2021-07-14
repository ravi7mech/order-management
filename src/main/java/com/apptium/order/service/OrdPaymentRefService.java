package com.apptium.order.service;

import com.apptium.order.service.dto.OrdPaymentRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdPaymentRef}.
 */
public interface OrdPaymentRefService {
    /**
     * Save a ordPaymentRef.
     *
     * @param ordPaymentRefDTO the entity to save.
     * @return the persisted entity.
     */
    OrdPaymentRefDTO save(OrdPaymentRefDTO ordPaymentRefDTO);

    /**
     * Partially updates a ordPaymentRef.
     *
     * @param ordPaymentRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdPaymentRefDTO> partialUpdate(OrdPaymentRefDTO ordPaymentRefDTO);

    /**
     * Get all the ordPaymentRefs.
     *
     * @return the list of entities.
     */
    List<OrdPaymentRefDTO> findAll();

    /**
     * Get the "id" ordPaymentRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdPaymentRefDTO> findOne(Long id);

    /**
     * Delete the "id" ordPaymentRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
