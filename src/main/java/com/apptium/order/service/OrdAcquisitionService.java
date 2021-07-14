package com.apptium.order.service;

import com.apptium.order.service.dto.OrdAcquisitionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdAcquisition}.
 */
public interface OrdAcquisitionService {
    /**
     * Save a ordAcquisition.
     *
     * @param ordAcquisitionDTO the entity to save.
     * @return the persisted entity.
     */
    OrdAcquisitionDTO save(OrdAcquisitionDTO ordAcquisitionDTO);

    /**
     * Partially updates a ordAcquisition.
     *
     * @param ordAcquisitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdAcquisitionDTO> partialUpdate(OrdAcquisitionDTO ordAcquisitionDTO);

    /**
     * Get all the ordAcquisitions.
     *
     * @return the list of entities.
     */
    List<OrdAcquisitionDTO> findAll();

    /**
     * Get the "id" ordAcquisition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdAcquisitionDTO> findOne(Long id);

    /**
     * Delete the "id" ordAcquisition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
