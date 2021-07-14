package com.apptium.order.service;

import com.apptium.order.service.dto.OrdContractDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdContract}.
 */
public interface OrdContractService {
    /**
     * Save a ordContract.
     *
     * @param ordContractDTO the entity to save.
     * @return the persisted entity.
     */
    OrdContractDTO save(OrdContractDTO ordContractDTO);

    /**
     * Partially updates a ordContract.
     *
     * @param ordContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdContractDTO> partialUpdate(OrdContractDTO ordContractDTO);

    /**
     * Get all the ordContracts.
     *
     * @return the list of entities.
     */
    List<OrdContractDTO> findAll();

    /**
     * Get the "id" ordContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdContractDTO> findOne(Long id);

    /**
     * Delete the "id" ordContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
