package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdContractCharacteristics;
import com.apptium.order.repository.OrdContractCharacteristicsRepository;
import com.apptium.order.service.OrdContractCharacteristicsService;
import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdContractCharacteristicsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdContractCharacteristics}.
 */
@Service
@Transactional
public class OrdContractCharacteristicsServiceImpl implements OrdContractCharacteristicsService {

    private final Logger log = LoggerFactory.getLogger(OrdContractCharacteristicsServiceImpl.class);

    private final OrdContractCharacteristicsRepository ordContractCharacteristicsRepository;

    private final OrdContractCharacteristicsMapper ordContractCharacteristicsMapper;

    public OrdContractCharacteristicsServiceImpl(
        OrdContractCharacteristicsRepository ordContractCharacteristicsRepository,
        OrdContractCharacteristicsMapper ordContractCharacteristicsMapper
    ) {
        this.ordContractCharacteristicsRepository = ordContractCharacteristicsRepository;
        this.ordContractCharacteristicsMapper = ordContractCharacteristicsMapper;
    }

    @Override
    public OrdContractCharacteristicsDTO save(OrdContractCharacteristicsDTO ordContractCharacteristicsDTO) {
        log.debug("Request to save OrdContractCharacteristics : {}", ordContractCharacteristicsDTO);
        OrdContractCharacteristics ordContractCharacteristics = ordContractCharacteristicsMapper.toEntity(ordContractCharacteristicsDTO);
        ordContractCharacteristics = ordContractCharacteristicsRepository.save(ordContractCharacteristics);
        return ordContractCharacteristicsMapper.toDto(ordContractCharacteristics);
    }

    @Override
    public Optional<OrdContractCharacteristicsDTO> partialUpdate(OrdContractCharacteristicsDTO ordContractCharacteristicsDTO) {
        log.debug("Request to partially update OrdContractCharacteristics : {}", ordContractCharacteristicsDTO);

        return ordContractCharacteristicsRepository
            .findById(ordContractCharacteristicsDTO.getId())
            .map(
                existingOrdContractCharacteristics -> {
                    ordContractCharacteristicsMapper.partialUpdate(existingOrdContractCharacteristics, ordContractCharacteristicsDTO);

                    return existingOrdContractCharacteristics;
                }
            )
            .map(ordContractCharacteristicsRepository::save)
            .map(ordContractCharacteristicsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdContractCharacteristicsDTO> findAll() {
        log.debug("Request to get all OrdContractCharacteristics");
        return ordContractCharacteristicsRepository
            .findAll()
            .stream()
            .map(ordContractCharacteristicsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdContractCharacteristicsDTO> findOne(Long id) {
        log.debug("Request to get OrdContractCharacteristics : {}", id);
        return ordContractCharacteristicsRepository.findById(id).map(ordContractCharacteristicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdContractCharacteristics : {}", id);
        ordContractCharacteristicsRepository.deleteById(id);
    }
}
