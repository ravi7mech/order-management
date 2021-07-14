package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.repository.OrdOrderPriceRepository;
import com.apptium.order.service.OrdOrderPriceService;
import com.apptium.order.service.dto.OrdOrderPriceDTO;
import com.apptium.order.service.mapper.OrdOrderPriceMapper;
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
 * Service Implementation for managing {@link OrdOrderPrice}.
 */
@Service
@Transactional
public class OrdOrderPriceServiceImpl implements OrdOrderPriceService {

    private final Logger log = LoggerFactory.getLogger(OrdOrderPriceServiceImpl.class);

    private final OrdOrderPriceRepository ordOrderPriceRepository;

    private final OrdOrderPriceMapper ordOrderPriceMapper;

    public OrdOrderPriceServiceImpl(OrdOrderPriceRepository ordOrderPriceRepository, OrdOrderPriceMapper ordOrderPriceMapper) {
        this.ordOrderPriceRepository = ordOrderPriceRepository;
        this.ordOrderPriceMapper = ordOrderPriceMapper;
    }

    @Override
    public OrdOrderPriceDTO save(OrdOrderPriceDTO ordOrderPriceDTO) {
        log.debug("Request to save OrdOrderPrice : {}", ordOrderPriceDTO);
        OrdOrderPrice ordOrderPrice = ordOrderPriceMapper.toEntity(ordOrderPriceDTO);
        ordOrderPrice = ordOrderPriceRepository.save(ordOrderPrice);
        return ordOrderPriceMapper.toDto(ordOrderPrice);
    }

    @Override
    public Optional<OrdOrderPriceDTO> partialUpdate(OrdOrderPriceDTO ordOrderPriceDTO) {
        log.debug("Request to partially update OrdOrderPrice : {}", ordOrderPriceDTO);

        return ordOrderPriceRepository
            .findById(ordOrderPriceDTO.getId())
            .map(
                existingOrdOrderPrice -> {
                    ordOrderPriceMapper.partialUpdate(existingOrdOrderPrice, ordOrderPriceDTO);

                    return existingOrdOrderPrice;
                }
            )
            .map(ordOrderPriceRepository::save)
            .map(ordOrderPriceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdOrderPriceDTO> findAll() {
        log.debug("Request to get all OrdOrderPrices");
        return ordOrderPriceRepository.findAll().stream().map(ordOrderPriceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordOrderPrices where OrdProductOrder is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderPriceDTO> findAllWhereOrdProductOrderIsNull() {
        log.debug("Request to get all ordOrderPrices where OrdProductOrder is null");
        return StreamSupport
            .stream(ordOrderPriceRepository.findAll().spliterator(), false)
            .filter(ordOrderPrice -> ordOrderPrice.getOrdProductOrder() == null)
            .map(ordOrderPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordOrderPrices where OrdOrderItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderPriceDTO> findAllWhereOrdOrderItemIsNull() {
        log.debug("Request to get all ordOrderPrices where OrdOrderItem is null");
        return StreamSupport
            .stream(ordOrderPriceRepository.findAll().spliterator(), false)
            .filter(ordOrderPrice -> ordOrderPrice.getOrdOrderItem() == null)
            .map(ordOrderPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdOrderPriceDTO> findOne(Long id) {
        log.debug("Request to get OrdOrderPrice : {}", id);
        return ordOrderPriceRepository.findById(id).map(ordOrderPriceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdOrderPrice : {}", id);
        ordOrderPriceRepository.deleteById(id);
    }
}
