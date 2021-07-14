package com.apptium.order.service;

import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdProductCharacteristics}.
 */
public interface OrdProductCharacteristicsService {
    /**
     * Save a ordProductCharacteristics.
     *
     * @param ordProductCharacteristicsDTO the entity to save.
     * @return the persisted entity.
     */
    OrdProductCharacteristicsDTO save(OrdProductCharacteristicsDTO ordProductCharacteristicsDTO);

    /**
     * Partially updates a ordProductCharacteristics.
     *
     * @param ordProductCharacteristicsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdProductCharacteristicsDTO> partialUpdate(OrdProductCharacteristicsDTO ordProductCharacteristicsDTO);

    /**
     * Get all the ordProductCharacteristics.
     *
     * @return the list of entities.
     */
    List<OrdProductCharacteristicsDTO> findAll();
    /**
     * Get all the OrdProductCharacteristicsDTO where OrdProduct is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdProductCharacteristicsDTO> findAllWhereOrdProductIsNull();

    /**
     * Get the "id" ordProductCharacteristics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdProductCharacteristicsDTO> findOne(Long id);

    /**
     * Delete the "id" ordProductCharacteristics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
