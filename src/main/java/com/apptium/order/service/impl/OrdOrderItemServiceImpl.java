package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.repository.OrdOrderItemRepository;
import com.apptium.order.service.OrdOrderItemService;
import com.apptium.order.service.dto.OrdOrderItemDTO;
import com.apptium.order.service.mapper.OrdOrderItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdOrderItem}.
 */
@Service
@Transactional
public class OrdOrderItemServiceImpl implements OrdOrderItemService {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemServiceImpl.class);

    private final OrdOrderItemRepository ordOrderItemRepository;

    private final OrdOrderItemMapper ordOrderItemMapper;

    public OrdOrderItemServiceImpl(OrdOrderItemRepository ordOrderItemRepository, OrdOrderItemMapper ordOrderItemMapper) {
        this.ordOrderItemRepository = ordOrderItemRepository;
        this.ordOrderItemMapper = ordOrderItemMapper;
    }

    @Override
    public OrdOrderItemDTO save(OrdOrderItemDTO ordOrderItemDTO) {
        log.debug("Request to save OrdOrderItem : {}", ordOrderItemDTO);
        OrdOrderItem ordOrderItem = ordOrderItemMapper.toEntity(ordOrderItemDTO);
        ordOrderItem = ordOrderItemRepository.save(ordOrderItem);
        return ordOrderItemMapper.toDto(ordOrderItem);
    }

    @Override
    public Optional<OrdOrderItemDTO> partialUpdate(OrdOrderItemDTO ordOrderItemDTO) {
        log.debug("Request to partially update OrdOrderItem : {}", ordOrderItemDTO);

        return ordOrderItemRepository
            .findById(ordOrderItemDTO.getId())
            .map(
                existingOrdOrderItem -> {
                    ordOrderItemMapper.partialUpdate(existingOrdOrderItem, ordOrderItemDTO);

                    return existingOrdOrderItem;
                }
            )
            .map(ordOrderItemRepository::save)
            .map(ordOrderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdOrderItemDTO> findAll() {
        log.debug("Request to get all OrdOrderItems");
        return ordOrderItemRepository.findAll().stream().map(ordOrderItemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdOrderItemDTO> findOne(Long id) {
        log.debug("Request to get OrdOrderItem : {}", id);
        return ordOrderItemRepository.findById(id).map(ordOrderItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdOrderItem : {}", id);
        ordOrderItemRepository.deleteById(id);
    }
}
