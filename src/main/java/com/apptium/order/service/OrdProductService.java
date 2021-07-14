package com.apptium.order.service;

import com.apptium.order.service.dto.OrdProductDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdProduct}.
 */
public interface OrdProductService {
    /**
     * Save a ordProduct.
     *
     * @param ordProductDTO the entity to save.
     * @return the persisted entity.
     */
    OrdProductDTO save(OrdProductDTO ordProductDTO);

    /**
     * Partially updates a ordProduct.
     *
     * @param ordProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdProductDTO> partialUpdate(OrdProductDTO ordProductDTO);

    /**
     * Get all the ordProducts.
     *
     * @return the list of entities.
     */
    List<OrdProductDTO> findAll();
    /**
     * Get all the OrdProductDTO where OrdOrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdProductDTO> findAllWhereOrdOrderItemIsNull();

    /**
     * Get the "id" ordProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdProductDTO> findOne(Long id);

    /**
     * Delete the "id" ordProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
