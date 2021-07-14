package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdBillingAccountRef;
import com.apptium.order.repository.OrdBillingAccountRefRepository;
import com.apptium.order.service.OrdBillingAccountRefService;
import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
import com.apptium.order.service.mapper.OrdBillingAccountRefMapper;
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
 * Service Implementation for managing {@link OrdBillingAccountRef}.
 */
@Service
@Transactional
public class OrdBillingAccountRefServiceImpl implements OrdBillingAccountRefService {

    private final Logger log = LoggerFactory.getLogger(OrdBillingAccountRefServiceImpl.class);

    private final OrdBillingAccountRefRepository ordBillingAccountRefRepository;

    private final OrdBillingAccountRefMapper ordBillingAccountRefMapper;

    public OrdBillingAccountRefServiceImpl(
        OrdBillingAccountRefRepository ordBillingAccountRefRepository,
        OrdBillingAccountRefMapper ordBillingAccountRefMapper
    ) {
        this.ordBillingAccountRefRepository = ordBillingAccountRefRepository;
        this.ordBillingAccountRefMapper = ordBillingAccountRefMapper;
    }

    @Override
    public OrdBillingAccountRefDTO save(OrdBillingAccountRefDTO ordBillingAccountRefDTO) {
        log.debug("Request to save OrdBillingAccountRef : {}", ordBillingAccountRefDTO);
        OrdBillingAccountRef ordBillingAccountRef = ordBillingAccountRefMapper.toEntity(ordBillingAccountRefDTO);
        ordBillingAccountRef = ordBillingAccountRefRepository.save(ordBillingAccountRef);
        return ordBillingAccountRefMapper.toDto(ordBillingAccountRef);
    }

    @Override
    public Optional<OrdBillingAccountRefDTO> partialUpdate(OrdBillingAccountRefDTO ordBillingAccountRefDTO) {
        log.debug("Request to partially update OrdBillingAccountRef : {}", ordBillingAccountRefDTO);

        return ordBillingAccountRefRepository
            .findById(ordBillingAccountRefDTO.getId())
            .map(
                existingOrdBillingAccountRef -> {
                    ordBillingAccountRefMapper.partialUpdate(existingOrdBillingAccountRef, ordBillingAccountRefDTO);

                    return existingOrdBillingAccountRef;
                }
            )
            .map(ordBillingAccountRefRepository::save)
            .map(ordBillingAccountRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdBillingAccountRefDTO> findAll() {
        log.debug("Request to get all OrdBillingAccountRefs");
        return ordBillingAccountRefRepository
            .findAll()
            .stream()
            .map(ordBillingAccountRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordBillingAccountRefs where OrdProductOrder is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdBillingAccountRefDTO> findAllWhereOrdProductOrderIsNull() {
        log.debug("Request to get all ordBillingAccountRefs where OrdProductOrder is null");
        return StreamSupport
            .stream(ordBillingAccountRefRepository.findAll().spliterator(), false)
            .filter(ordBillingAccountRef -> ordBillingAccountRef.getOrdProductOrder() == null)
            .map(ordBillingAccountRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdBillingAccountRefDTO> findOne(Long id) {
        log.debug("Request to get OrdBillingAccountRef : {}", id);
        return ordBillingAccountRefRepository.findById(id).map(ordBillingAccountRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdBillingAccountRef : {}", id);
        ordBillingAccountRefRepository.deleteById(id);
    }
}
