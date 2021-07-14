package com.apptium.order.service;

import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdContractCharacteristics}.
 */
public interface OrdContractCharacteristicsService {
    /**
     * Save a ordContractCharacteristics.
     *
     * @param ordContractCharacteristicsDTO the entity to save.
     * @return the persisted entity.
     */
    OrdContractCharacteristicsDTO save(OrdContractCharacteristicsDTO ordContractCharacteristicsDTO);

    /**
     * Partially updates a ordContractCharacteristics.
     *
     * @param ordContractCharacteristicsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdContractCharacteristicsDTO> partialUpdate(OrdContractCharacteristicsDTO ordContractCharacteristicsDTO);

    /**
     * Get all the ordContractCharacteristics.
     *
     * @return the list of entities.
     */
    List<OrdContractCharacteristicsDTO> findAll();

    /**
     * Get the "id" ordContractCharacteristics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdContractCharacteristicsDTO> findOne(Long id);

    /**
     * Delete the "id" ordContractCharacteristics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
