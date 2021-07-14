package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdPaymentRef;
import com.apptium.order.repository.OrdPaymentRefRepository;
import com.apptium.order.service.OrdPaymentRefService;
import com.apptium.order.service.dto.OrdPaymentRefDTO;
import com.apptium.order.service.mapper.OrdPaymentRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdPaymentRef}.
 */
@Service
@Transactional
public class OrdPaymentRefServiceImpl implements OrdPaymentRefService {

    private final Logger log = LoggerFactory.getLogger(OrdPaymentRefServiceImpl.class);

    private final OrdPaymentRefRepository ordPaymentRefRepository;

    private final OrdPaymentRefMapper ordPaymentRefMapper;

    public OrdPaymentRefServiceImpl(OrdPaymentRefRepository ordPaymentRefRepository, OrdPaymentRefMapper ordPaymentRefMapper) {
        this.ordPaymentRefRepository = ordPaymentRefRepository;
        this.ordPaymentRefMapper = ordPaymentRefMapper;
    }

    @Override
    public OrdPaymentRefDTO save(OrdPaymentRefDTO ordPaymentRefDTO) {
        log.debug("Request to save OrdPaymentRef : {}", ordPaymentRefDTO);
        OrdPaymentRef ordPaymentRef = ordPaymentRefMapper.toEntity(ordPaymentRefDTO);
        ordPaymentRef = ordPaymentRefRepository.save(ordPaymentRef);
        return ordPaymentRefMapper.toDto(ordPaymentRef);
    }

    @Override
    public Optional<OrdPaymentRefDTO> partialUpdate(OrdPaymentRefDTO ordPaymentRefDTO) {
        log.debug("Request to partially update OrdPaymentRef : {}", ordPaymentRefDTO);

        return ordPaymentRefRepository
            .findById(ordPaymentRefDTO.getId())
            .map(
                existingOrdPaymentRef -> {
                    ordPaymentRefMapper.partialUpdate(existingOrdPaymentRef, ordPaymentRefDTO);

                    return existingOrdPaymentRef;
                }
            )
            .map(ordPaymentRefRepository::save)
            .map(ordPaymentRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdPaymentRefDTO> findAll() {
        log.debug("Request to get all OrdPaymentRefs");
        return ordPaymentRefRepository.findAll().stream().map(ordPaymentRefMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdPaymentRefDTO> findOne(Long id) {
        log.debug("Request to get OrdPaymentRef : {}", id);
        return ordPaymentRefRepository.findById(id).map(ordPaymentRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdPaymentRef : {}", id);
        ordPaymentRefRepository.deleteById(id);
    }
}
