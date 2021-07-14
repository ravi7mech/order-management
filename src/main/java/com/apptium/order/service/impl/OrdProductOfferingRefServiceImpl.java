package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdProductOfferingRef;
import com.apptium.order.repository.OrdProductOfferingRefRepository;
import com.apptium.order.service.OrdProductOfferingRefService;
import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
import com.apptium.order.service.mapper.OrdProductOfferingRefMapper;
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
 * Service Implementation for managing {@link OrdProductOfferingRef}.
 */
@Service
@Transactional
public class OrdProductOfferingRefServiceImpl implements OrdProductOfferingRefService {

    private final Logger log = LoggerFactory.getLogger(OrdProductOfferingRefServiceImpl.class);

    private final OrdProductOfferingRefRepository ordProductOfferingRefRepository;

    private final OrdProductOfferingRefMapper ordProductOfferingRefMapper;

    public OrdProductOfferingRefServiceImpl(
        OrdProductOfferingRefRepository ordProductOfferingRefRepository,
        OrdProductOfferingRefMapper ordProductOfferingRefMapper
    ) {
        this.ordProductOfferingRefRepository = ordProductOfferingRefRepository;
        this.ordProductOfferingRefMapper = ordProductOfferingRefMapper;
    }

    @Override
    public OrdProductOfferingRefDTO save(OrdProductOfferingRefDTO ordProductOfferingRefDTO) {
        log.debug("Request to save OrdProductOfferingRef : {}", ordProductOfferingRefDTO);
        OrdProductOfferingRef ordProductOfferingRef = ordProductOfferingRefMapper.toEntity(ordProductOfferingRefDTO);
        ordProductOfferingRef = ordProductOfferingRefRepository.save(ordProductOfferingRef);
        return ordProductOfferingRefMapper.toDto(ordProductOfferingRef);
    }

    @Override
    public Optional<OrdProductOfferingRefDTO> partialUpdate(OrdProductOfferingRefDTO ordProductOfferingRefDTO) {
        log.debug("Request to partially update OrdProductOfferingRef : {}", ordProductOfferingRefDTO);

        return ordProductOfferingRefRepository
            .findById(ordProductOfferingRefDTO.getId())
            .map(
                existingOrdProductOfferingRef -> {
                    ordProductOfferingRefMapper.partialUpdate(existingOrdProductOfferingRef, ordProductOfferingRefDTO);

                    return existingOrdProductOfferingRef;
                }
            )
            .map(ordProductOfferingRefRepository::save)
            .map(ordProductOfferingRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdProductOfferingRefDTO> findAll() {
        log.debug("Request to get all OrdProductOfferingRefs");
        return ordProductOfferingRefRepository
            .findAll()
            .stream()
            .map(ordProductOfferingRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordProductOfferingRefs where OrdOrderItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductOfferingRefDTO> findAllWhereOrdOrderItemIsNull() {
        log.debug("Request to get all ordProductOfferingRefs where OrdOrderItem is null");
        return StreamSupport
            .stream(ordProductOfferingRefRepository.findAll().spliterator(), false)
            .filter(ordProductOfferingRef -> ordProductOfferingRef.getOrdOrderItem() == null)
            .map(ordProductOfferingRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdProductOfferingRefDTO> findOne(Long id) {
        log.debug("Request to get OrdProductOfferingRef : {}", id);
        return ordProductOfferingRefRepository.findById(id).map(ordProductOfferingRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdProductOfferingRef : {}", id);
        ordProductOfferingRefRepository.deleteById(id);
    }
}
