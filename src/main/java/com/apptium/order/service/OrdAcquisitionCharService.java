package com.apptium.order.service;

import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdAcquisitionChar}.
 */
public interface OrdAcquisitionCharService {
    /**
     * Save a ordAcquisitionChar.
     *
     * @param ordAcquisitionCharDTO the entity to save.
     * @return the persisted entity.
     */
    OrdAcquisitionCharDTO save(OrdAcquisitionCharDTO ordAcquisitionCharDTO);

    /**
     * Partially updates a ordAcquisitionChar.
     *
     * @param ordAcquisitionCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdAcquisitionCharDTO> partialUpdate(OrdAcquisitionCharDTO ordAcquisitionCharDTO);

    /**
     * Get all the ordAcquisitionChars.
     *
     * @return the list of entities.
     */
    List<OrdAcquisitionCharDTO> findAll();

    /**
     * Get the "id" ordAcquisitionChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdAcquisitionCharDTO> findOne(Long id);

    /**
     * Delete the "id" ordAcquisitionChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
