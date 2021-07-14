package com.apptium.order.service;

import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdBillingAccountRef}.
 */
public interface OrdBillingAccountRefService {
    /**
     * Save a ordBillingAccountRef.
     *
     * @param ordBillingAccountRefDTO the entity to save.
     * @return the persisted entity.
     */
    OrdBillingAccountRefDTO save(OrdBillingAccountRefDTO ordBillingAccountRefDTO);

    /**
     * Partially updates a ordBillingAccountRef.
     *
     * @param ordBillingAccountRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdBillingAccountRefDTO> partialUpdate(OrdBillingAccountRefDTO ordBillingAccountRefDTO);

    /**
     * Get all the ordBillingAccountRefs.
     *
     * @return the list of entities.
     */
    List<OrdBillingAccountRefDTO> findAll();
    /**
     * Get all the OrdBillingAccountRefDTO where OrdProductOrder is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdBillingAccountRefDTO> findAllWhereOrdProductOrderIsNull();

    /**
     * Get the "id" ordBillingAccountRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdBillingAccountRefDTO> findOne(Long id);

    /**
     * Delete the "id" ordBillingAccountRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
