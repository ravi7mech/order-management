package com.apptium.order.service;

import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdProductOfferingRef}.
 */
public interface OrdProductOfferingRefService {
    /**
     * Save a ordProductOfferingRef.
     *
     * @param ordProductOfferingRefDTO the entity to save.
     * @return the persisted entity.
     */
    OrdProductOfferingRefDTO save(OrdProductOfferingRefDTO ordProductOfferingRefDTO);

    /**
     * Partially updates a ordProductOfferingRef.
     *
     * @param ordProductOfferingRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdProductOfferingRefDTO> partialUpdate(OrdProductOfferingRefDTO ordProductOfferingRefDTO);

    /**
     * Get all the ordProductOfferingRefs.
     *
     * @return the list of entities.
     */
    List<OrdProductOfferingRefDTO> findAll();
    /**
     * Get all the OrdProductOfferingRefDTO where OrdOrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdProductOfferingRefDTO> findAllWhereOrdOrderItemIsNull();

    /**
     * Get the "id" ordProductOfferingRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdProductOfferingRefDTO> findOne(Long id);

    /**
     * Delete the "id" ordProductOfferingRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
