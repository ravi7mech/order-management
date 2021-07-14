package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdFulfillment;
import com.apptium.order.repository.OrdFulfillmentRepository;
import com.apptium.order.service.OrdFulfillmentService;
import com.apptium.order.service.dto.OrdFulfillmentDTO;
import com.apptium.order.service.mapper.OrdFulfillmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdFulfillment}.
 */
@Service
@Transactional
public class OrdFulfillmentServiceImpl implements OrdFulfillmentService {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentServiceImpl.class);

    private final OrdFulfillmentRepository ordFulfillmentRepository;

    private final OrdFulfillmentMapper ordFulfillmentMapper;

    public OrdFulfillmentServiceImpl(OrdFulfillmentRepository ordFulfillmentRepository, OrdFulfillmentMapper ordFulfillmentMapper) {
        this.ordFulfillmentRepository = ordFulfillmentRepository;
        this.ordFulfillmentMapper = ordFulfillmentMapper;
    }

    @Override
    public OrdFulfillmentDTO save(OrdFulfillmentDTO ordFulfillmentDTO) {
        log.debug("Request to save OrdFulfillment : {}", ordFulfillmentDTO);
        OrdFulfillment ordFulfillment = ordFulfillmentMapper.toEntity(ordFulfillmentDTO);
        ordFulfillment = ordFulfillmentRepository.save(ordFulfillment);
        return ordFulfillmentMapper.toDto(ordFulfillment);
    }

    @Override
    public Optional<OrdFulfillmentDTO> partialUpdate(OrdFulfillmentDTO ordFulfillmentDTO) {
        log.debug("Request to partially update OrdFulfillment : {}", ordFulfillmentDTO);

        return ordFulfillmentRepository
            .findById(ordFulfillmentDTO.getId())
            .map(
                existingOrdFulfillment -> {
                    ordFulfillmentMapper.partialUpdate(existingOrdFulfillment, ordFulfillmentDTO);

                    return existingOrdFulfillment;
                }
            )
            .map(ordFulfillmentRepository::save)
            .map(ordFulfillmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdFulfillmentDTO> findAll() {
        log.debug("Request to get all OrdFulfillments");
        return ordFulfillmentRepository
            .findAll()
            .stream()
            .map(ordFulfillmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdFulfillmentDTO> findOne(Long id) {
        log.debug("Request to get OrdFulfillment : {}", id);
        return ordFulfillmentRepository.findById(id).map(ordFulfillmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdFulfillment : {}", id);
        ordFulfillmentRepository.deleteById(id);
    }
}
