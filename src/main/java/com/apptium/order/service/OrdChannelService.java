package com.apptium.order.service;

import com.apptium.order.service.dto.OrdChannelDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.order.domain.OrdChannel}.
 */
public interface OrdChannelService {
    /**
     * Save a ordChannel.
     *
     * @param ordChannelDTO the entity to save.
     * @return the persisted entity.
     */
    OrdChannelDTO save(OrdChannelDTO ordChannelDTO);

    /**
     * Partially updates a ordChannel.
     *
     * @param ordChannelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdChannelDTO> partialUpdate(OrdChannelDTO ordChannelDTO);

    /**
     * Get all the ordChannels.
     *
     * @return the list of entities.
     */
    List<OrdChannelDTO> findAll();
    /**
     * Get all the OrdChannelDTO where OrdProductOrder is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrdChannelDTO> findAllWhereOrdProductOrderIsNull();

    /**
     * Get the "id" ordChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdChannelDTO> findOne(Long id);

    /**
     * Delete the "id" ordChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
