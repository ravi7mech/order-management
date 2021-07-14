package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdProductOrderRepository;
import com.apptium.order.service.OrdProductOrderService;
import com.apptium.order.service.dto.OrdProductOrderDTO;
import com.apptium.order.service.mapper.OrdProductOrderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdProductOrder}.
 */
@Service
@Transactional
public class OrdProductOrderServiceImpl implements OrdProductOrderService {

    private final Logger log = LoggerFactory.getLogger(OrdProductOrderServiceImpl.class);

    private final OrdProductOrderRepository ordProductOrderRepository;

    private final OrdProductOrderMapper ordProductOrderMapper;

    public OrdProductOrderServiceImpl(OrdProductOrderRepository ordProductOrderRepository, OrdProductOrderMapper ordProductOrderMapper) {
        this.ordProductOrderRepository = ordProductOrderRepository;
        this.ordProductOrderMapper = ordProductOrderMapper;
    }

    @Override
    public OrdProductOrderDTO save(OrdProductOrderDTO ordProductOrderDTO) {
        log.debug("Request to save OrdProductOrder : {}", ordProductOrderDTO);
        OrdProductOrder ordProductOrder = ordProductOrderMapper.toEntity(ordProductOrderDTO);
        ordProductOrder = ordProductOrderRepository.save(ordProductOrder);
        return ordProductOrderMapper.toDto(ordProductOrder);
    }

    @Override
    public Optional<OrdProductOrderDTO> partialUpdate(OrdProductOrderDTO ordProductOrderDTO) {
        log.debug("Request to partially update OrdProductOrder : {}", ordProductOrderDTO);

        return ordProductOrderRepository
            .findById(ordProductOrderDTO.getId())
            .map(
                existingOrdProductOrder -> {
                    ordProductOrderMapper.partialUpdate(existingOrdProductOrder, ordProductOrderDTO);

                    return existingOrdProductOrder;
                }
            )
            .map(ordProductOrderRepository::save)
            .map(ordProductOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdProductOrderDTO> findAll() {
        log.debug("Request to get all OrdProductOrders");
        return ordProductOrderRepository
            .findAll()
            .stream()
            .map(ordProductOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdProductOrderDTO> findOne(Long id) {
        log.debug("Request to get OrdProductOrder : {}", id);
        return ordProductOrderRepository.findById(id).map(ordProductOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdProductOrder : {}", id);
        ordProductOrderRepository.deleteById(id);
    }
}
