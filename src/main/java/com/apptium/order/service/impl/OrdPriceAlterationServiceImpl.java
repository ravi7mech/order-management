package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdPriceAlteration;
import com.apptium.order.repository.OrdPriceAlterationRepository;
import com.apptium.order.service.OrdPriceAlterationService;
import com.apptium.order.service.dto.OrdPriceAlterationDTO;
import com.apptium.order.service.mapper.OrdPriceAlterationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdPriceAlteration}.
 */
@Service
@Transactional
public class OrdPriceAlterationServiceImpl implements OrdPriceAlterationService {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAlterationServiceImpl.class);

    private final OrdPriceAlterationRepository ordPriceAlterationRepository;

    private final OrdPriceAlterationMapper ordPriceAlterationMapper;

    public OrdPriceAlterationServiceImpl(
        OrdPriceAlterationRepository ordPriceAlterationRepository,
        OrdPriceAlterationMapper ordPriceAlterationMapper
    ) {
        this.ordPriceAlterationRepository = ordPriceAlterationRepository;
        this.ordPriceAlterationMapper = ordPriceAlterationMapper;
    }

    @Override
    public OrdPriceAlterationDTO save(OrdPriceAlterationDTO ordPriceAlterationDTO) {
        log.debug("Request to save OrdPriceAlteration : {}", ordPriceAlterationDTO);
        OrdPriceAlteration ordPriceAlteration = ordPriceAlterationMapper.toEntity(ordPriceAlterationDTO);
        ordPriceAlteration = ordPriceAlterationRepository.save(ordPriceAlteration);
        return ordPriceAlterationMapper.toDto(ordPriceAlteration);
    }

    @Override
    public Optional<OrdPriceAlterationDTO> partialUpdate(OrdPriceAlterationDTO ordPriceAlterationDTO) {
        log.debug("Request to partially update OrdPriceAlteration : {}", ordPriceAlterationDTO);

        return ordPriceAlterationRepository
            .findById(ordPriceAlterationDTO.getId())
            .map(
                existingOrdPriceAlteration -> {
                    ordPriceAlterationMapper.partialUpdate(existingOrdPriceAlteration, ordPriceAlterationDTO);

                    return existingOrdPriceAlteration;
                }
            )
            .map(ordPriceAlterationRepository::save)
            .map(ordPriceAlterationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdPriceAlterationDTO> findAll() {
        log.debug("Request to get all OrdPriceAlterations");
        return ordPriceAlterationRepository
            .findAll()
            .stream()
            .map(ordPriceAlterationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdPriceAlterationDTO> findOne(Long id) {
        log.debug("Request to get OrdPriceAlteration : {}", id);
        return ordPriceAlterationRepository.findById(id).map(ordPriceAlterationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdPriceAlteration : {}", id);
        ordPriceAlterationRepository.deleteById(id);
    }
}
