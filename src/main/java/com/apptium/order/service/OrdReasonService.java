package com.apptium.order.service;

import com.apptium.order.service.dto.OrdReasonDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdReason}.
 */
public interface OrdReasonService {
    /**
     * Save a ordReason.
     *
     * @param ordReasonDTO the entity to save.
     * @return the persisted entity.
     */
    OrdReasonDTO save(OrdReasonDTO ordReasonDTO);

    /**
     * Partially updates a ordReason.
     *
     * @param ordReasonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdReasonDTO> partialUpdate(OrdReasonDTO ordReasonDTO);

    /**
     * Get all the ordReasons.
     *
     * @return the list of entities.
     */
    List<OrdReasonDTO> findAll();

    /**
     * Get the "id" ordReason.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdReasonDTO> findOne(Long id);

    /**
     * Delete the "id" ordReason.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
