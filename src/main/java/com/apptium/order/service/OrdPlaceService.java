package com.apptium.order.service;

import com.apptium.order.service.dto.OrdPlaceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdPlace}.
 */
public interface OrdPlaceService {
    /**
     * Save a ordPlace.
     *
     * @param ordPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    OrdPlaceDTO save(OrdPlaceDTO ordPlaceDTO);

    /**
     * Partially updates a ordPlace.
     *
     * @param ordPlaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdPlaceDTO> partialUpdate(OrdPlaceDTO ordPlaceDTO);

    /**
     * Get all the ordPlaces.
     *
     * @return the list of entities.
     */
    List<OrdPlaceDTO> findAll();

    /**
     * Get the "id" ordPlace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdPlaceDTO> findOne(Long id);

    /**
     * Delete the "id" ordPlace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
