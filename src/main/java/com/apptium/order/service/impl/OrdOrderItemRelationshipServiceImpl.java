package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdOrderItemRelationship;
import com.apptium.order.repository.OrdOrderItemRelationshipRepository;
import com.apptium.order.service.OrdOrderItemRelationshipService;
import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import com.apptium.order.service.mapper.OrdOrderItemRelationshipMapper;
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
 * Service Implementation for managing {@link OrdOrderItemRelationship}.
 */
@Service
@Transactional
public class OrdOrderItemRelationshipServiceImpl implements OrdOrderItemRelationshipService {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemRelationshipServiceImpl.class);

    private final OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository;

    private final OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper;

    public OrdOrderItemRelationshipServiceImpl(
        OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository,
        OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper
    ) {
        this.ordOrderItemRelationshipRepository = ordOrderItemRelationshipRepository;
        this.ordOrderItemRelationshipMapper = ordOrderItemRelationshipMapper;
    }

    @Override
    public OrdOrderItemRelationshipDTO save(OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO) {
        log.debug("Request to save OrdOrderItemRelationship : {}", ordOrderItemRelationshipDTO);
        OrdOrderItemRelationship ordOrderItemRelationship = ordOrderItemRelationshipMapper.toEntity(ordOrderItemRelationshipDTO);
        ordOrderItemRelationship = ordOrderItemRelationshipRepository.save(ordOrderItemRelationship);
        return ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationship);
    }

    @Override
    public Optional<OrdOrderItemRelationshipDTO> partialUpdate(OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO) {
        log.debug("Request to partially update OrdOrderItemRelationship : {}", ordOrderItemRelationshipDTO);

        return ordOrderItemRelationshipRepository
            .findById(ordOrderItemRelationshipDTO.getId())
            .map(
                existingOrdOrderItemRelationship -> {
                    ordOrderItemRelationshipMapper.partialUpdate(existingOrdOrderItemRelationship, ordOrderItemRelationshipDTO);

                    return existingOrdOrderItemRelationship;
                }
            )
            .map(ordOrderItemRelationshipRepository::save)
            .map(ordOrderItemRelationshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdOrderItemRelationshipDTO> findAll() {
        log.debug("Request to get all OrdOrderItemRelationships");
        return ordOrderItemRelationshipRepository
            .findAll()
            .stream()
            .map(ordOrderItemRelationshipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordOrderItemRelationships where OrdOrderItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemRelationshipDTO> findAllWhereOrdOrderItemIsNull() {
        log.debug("Request to get all ordOrderItemRelationships where OrdOrderItem is null");
        return StreamSupport
            .stream(ordOrderItemRelationshipRepository.findAll().spliterator(), false)
            .filter(ordOrderItemRelationship -> ordOrderItemRelationship.getOrdOrderItem() == null)
            .map(ordOrderItemRelationshipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdOrderItemRelationshipDTO> findOne(Long id) {
        log.debug("Request to get OrdOrderItemRelationship : {}", id);
        return ordOrderItemRelationshipRepository.findById(id).map(ordOrderItemRelationshipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdOrderItemRelationship : {}", id);
        ordOrderItemRelationshipRepository.deleteById(id);
    }
}
