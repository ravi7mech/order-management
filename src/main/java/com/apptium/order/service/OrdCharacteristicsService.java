package com.apptium.order.service;

import com.apptium.order.service.dto.OrdCharacteristicsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdCharacteristics}.
 */
public interface OrdCharacteristicsService {
    /**
     * Save a ordCharacteristics.
     *
     * @param ordCharacteristicsDTO the entity to save.
     * @return the persisted entity.
     */
    OrdCharacteristicsDTO save(OrdCharacteristicsDTO ordCharacteristicsDTO);

    /**
     * Partially updates a ordCharacteristics.
     *
     * @param ordCharacteristicsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdCharacteristicsDTO> partialUpdate(OrdCharacteristicsDTO ordCharacteristicsDTO);

    /**
     * Get all the ordCharacteristics.
     *
     * @return the list of entities.
     */
    List<OrdCharacteristicsDTO> findAll();

    /**
     * Get the "id" ordCharacteristics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdCharacteristicsDTO> findOne(Long id);

    /**
     * Delete the "id" ordCharacteristics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
