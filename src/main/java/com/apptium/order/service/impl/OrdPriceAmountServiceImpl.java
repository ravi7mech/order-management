package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdPriceAmount;
import com.apptium.order.repository.OrdPriceAmountRepository;
import com.apptium.order.service.OrdPriceAmountService;
import com.apptium.order.service.dto.OrdPriceAmountDTO;
import com.apptium.order.service.mapper.OrdPriceAmountMapper;
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
 * Service Implementation for managing {@link OrdPriceAmount}.
 */
@Service
@Transactional
public class OrdPriceAmountServiceImpl implements OrdPriceAmountService {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAmountServiceImpl.class);

    private final OrdPriceAmountRepository ordPriceAmountRepository;

    private final OrdPriceAmountMapper ordPriceAmountMapper;

    public OrdPriceAmountServiceImpl(OrdPriceAmountRepository ordPriceAmountRepository, OrdPriceAmountMapper ordPriceAmountMapper) {
        this.ordPriceAmountRepository = ordPriceAmountRepository;
        this.ordPriceAmountMapper = ordPriceAmountMapper;
    }

    @Override
    public OrdPriceAmountDTO save(OrdPriceAmountDTO ordPriceAmountDTO) {
        log.debug("Request to save OrdPriceAmount : {}", ordPriceAmountDTO);
        OrdPriceAmount ordPriceAmount = ordPriceAmountMapper.toEntity(ordPriceAmountDTO);
        ordPriceAmount = ordPriceAmountRepository.save(ordPriceAmount);
        return ordPriceAmountMapper.toDto(ordPriceAmount);
    }

    @Override
    public Optional<OrdPriceAmountDTO> partialUpdate(OrdPriceAmountDTO ordPriceAmountDTO) {
        log.debug("Request to partially update OrdPriceAmount : {}", ordPriceAmountDTO);

        return ordPriceAmountRepository
            .findById(ordPriceAmountDTO.getId())
            .map(
                existingOrdPriceAmount -> {
                    ordPriceAmountMapper.partialUpdate(existingOrdPriceAmount, ordPriceAmountDTO);

                    return existingOrdPriceAmount;
                }
            )
            .map(ordPriceAmountRepository::save)
            .map(ordPriceAmountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdPriceAmountDTO> findAll() {
        log.debug("Request to get all OrdPriceAmounts");
        return ordPriceAmountRepository
            .findAll()
            .stream()
            .map(ordPriceAmountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordPriceAmounts where OrdOrderPrice is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPriceAmountDTO> findAllWhereOrdOrderPriceIsNull() {
        log.debug("Request to get all ordPriceAmounts where OrdOrderPrice is null");
        return StreamSupport
            .stream(ordPriceAmountRepository.findAll().spliterator(), false)
            .filter(ordPriceAmount -> ordPriceAmount.getOrdOrderPrice() == null)
            .map(ordPriceAmountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordPriceAmounts where OrdPriceAlteration is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPriceAmountDTO> findAllWhereOrdPriceAlterationIsNull() {
        log.debug("Request to get all ordPriceAmounts where OrdPriceAlteration is null");
        return StreamSupport
            .stream(ordPriceAmountRepository.findAll().spliterator(), false)
            .filter(ordPriceAmount -> ordPriceAmount.getOrdPriceAlteration() == null)
            .map(ordPriceAmountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdPriceAmountDTO> findOne(Long id) {
        log.debug("Request to get OrdPriceAmount : {}", id);
        return ordPriceAmountRepository.findById(id).map(ordPriceAmountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdPriceAmount : {}", id);
        ordPriceAmountRepository.deleteById(id);
    }
}
