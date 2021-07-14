package com.apptium.order.service;

import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdOrderItemProvisioning}.
 */
public interface OrdOrderItemProvisioningService {
    /**
     * Save a ordOrderItemProvisioning.
     *
     * @param ordOrderItemProvisioningDTO the entity to save.
     * @return the persisted entity.
     */
    OrdOrderItemProvisioningDTO save(OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO);

    /**
     * Partially updates a ordOrderItemProvisioning.
     *
     * @param ordOrderItemProvisioningDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdOrderItemProvisioningDTO> partialUpdate(OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO);

    /**
     * Get all the ordOrderItemProvisionings.
     *
     * @return the list of entities.
     */
    List<OrdOrderItemProvisioningDTO> findAll();
    /**
     * Get all the OrdOrderItemProvisioningDTO where OrdOrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdOrderItemProvisioningDTO> findAllWhereOrdOrderItemIsNull();

    /**
     * Get the "id" ordOrderItemProvisioning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdOrderItemProvisioningDTO> findOne(Long id);

    /**
     * Delete the "id" ordOrderItemProvisioning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
