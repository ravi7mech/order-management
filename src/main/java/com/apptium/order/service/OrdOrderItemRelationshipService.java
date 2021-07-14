package com.apptium.order.service;

import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdOrderItemRelationship}.
 */
public interface OrdOrderItemRelationshipService {
    /**
     * Save a ordOrderItemRelationship.
     *
     * @param ordOrderItemRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    OrdOrderItemRelationshipDTO save(OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO);

    /**
     * Partially updates a ordOrderItemRelationship.
     *
     * @param ordOrderItemRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdOrderItemRelationshipDTO> partialUpdate(OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO);

    /**
     * Get all the ordOrderItemRelationships.
     *
     * @return the list of entities.
     */
    List<OrdOrderItemRelationshipDTO> findAll();
    /**
     * Get all the OrdOrderItemRelationshipDTO where OrdOrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdOrderItemRelationshipDTO> findAllWhereOrdOrderItemIsNull();

    /**
     * Get the "id" ordOrderItemRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdOrderItemRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" ordOrderItemRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
