package com.apptium.order.service;

import com.apptium.order.service.dto.OrdContactDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdContactDetails}.
 */
public interface OrdContactDetailsService {
    /**
     * Save a ordContactDetails.
     *
     * @param ordContactDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    OrdContactDetailsDTO save(OrdContactDetailsDTO ordContactDetailsDTO);

    /**
     * Partially updates a ordContactDetails.
     *
     * @param ordContactDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdContactDetailsDTO> partialUpdate(OrdContactDetailsDTO ordContactDetailsDTO);

    /**
     * Get all the ordContactDetails.
     *
     * @return the list of entities.
     */
    List<OrdContactDetailsDTO> findAll();
    /**
     * Get all the OrdContactDetailsDTO where OrdProductOrder is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdContactDetailsDTO> findAllWhereOrdProductOrderIsNull();

    /**
     * Get the "id" ordContactDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdContactDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" ordContactDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
