package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdOrderItemProvisioning;
import com.apptium.order.repository.OrdOrderItemProvisioningRepository;
import com.apptium.order.service.OrdOrderItemProvisioningService;
import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
import com.apptium.order.service.mapper.OrdOrderItemProvisioningMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdOrderItemProvisioning}.
 */
@Service
@Transactional
public class OrdOrderItemProvisioningServiceImpl implements OrdOrderItemProvisioningService {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemProvisioningServiceImpl.class);

    private final OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository;

    private final OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper;

    public OrdOrderItemProvisioningServiceImpl(
        OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository,
        OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper
    ) {
        this.ordOrderItemProvisioningRepository = ordOrderItemProvisioningRepository;
        this.ordOrderItemProvisioningMapper = ordOrderItemProvisioningMapper;
    }

    @Override
    public OrdOrderItemProvisioningDTO save(OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO) {
        log.debug("Request to save OrdOrderItemProvisioning : {}", ordOrderItemProvisioningDTO);
        OrdOrderItemProvisioning ordOrderItemProvisioning = ordOrderItemProvisioningMapper.toEntity(ordOrderItemProvisioningDTO);
        ordOrderItemProvisioning = ordOrderItemProvisioningRepository.save(ordOrderItemProvisioning);
        return ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioning);
    }

    @Override
    public Optional<OrdOrderItemProvisioningDTO> partialUpdate(OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO) {
        log.debug("Request to partially update OrdOrderItemProvisioning : {}", ordOrderItemProvisioningDTO);

        return ordOrderItemProvisioningRepository
            .findById(ordOrderItemProvisioningDTO.getId())
            .map(
                existingOrdOrderItemProvisioning -> {
                    ordOrderItemProvisioningMapper.partialUpdate(existingOrdOrderItemProvisioning, ordOrderItemProvisioningDTO);

                    return existingOrdOrderItemProvisioning;
                }
            )
            .map(ordOrderItemProvisioningRepository::save)
            .map(ordOrderItemProvisioningMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdOrderItemProvisioningDTO> findAll() {
        log.debug("Request to get all OrdOrderItemProvisionings");
        return ordOrderItemProvisioningRepository
            .findAll()
            .stream()
            .map(ordOrderItemProvisioningMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordOrderItemProvisionings where OrdOrderItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemProvisioningDTO> findAllWhereOrdOrderItemIsNull() {
        log.debug("Request to get all ordOrderItemProvisionings where OrdOrderItem is null");
        return StreamSupport
            .stream(ordOrderItemProvisioningRepository.findAll().spliterator(), false)
            .filter(ordOrderItemProvisioning -> ordOrderItemProvisioning.getOrdOrderItem() == null)
            .map(ordOrderItemProvisioningMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdOrderItemProvisioningDTO> findOne(Long id) {
        log.debug("Request to get OrdOrderItemProvisioning : {}", id);
        return ordOrderItemProvisioningRepository.findById(id).map(ordOrderItemProvisioningMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdOrderItemProvisioning : {}", id);
        ordOrderItemProvisioningRepository.deleteById(id);
    }
}
