package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdCharacteristics;
import com.apptium.order.repository.OrdCharacteristicsRepository;
import com.apptium.order.service.OrdCharacteristicsService;
import com.apptium.order.service.dto.OrdCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdCharacteristicsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdCharacteristics}.
 */
@Service
@Transactional
public class OrdCharacteristicsServiceImpl implements OrdCharacteristicsService {

    private final Logger log = LoggerFactory.getLogger(OrdCharacteristicsServiceImpl.class);

    private final OrdCharacteristicsRepository ordCharacteristicsRepository;

    private final OrdCharacteristicsMapper ordCharacteristicsMapper;

    public OrdCharacteristicsServiceImpl(
        OrdCharacteristicsRepository ordCharacteristicsRepository,
        OrdCharacteristicsMapper ordCharacteristicsMapper
    ) {
        this.ordCharacteristicsRepository = ordCharacteristicsRepository;
        this.ordCharacteristicsMapper = ordCharacteristicsMapper;
    }

    @Override
    public OrdCharacteristicsDTO save(OrdCharacteristicsDTO ordCharacteristicsDTO) {
        log.debug("Request to save OrdCharacteristics : {}", ordCharacteristicsDTO);
        OrdCharacteristics ordCharacteristics = ordCharacteristicsMapper.toEntity(ordCharacteristicsDTO);
        ordCharacteristics = ordCharacteristicsRepository.save(ordCharacteristics);
        return ordCharacteristicsMapper.toDto(ordCharacteristics);
    }

    @Override
    public Optional<OrdCharacteristicsDTO> partialUpdate(OrdCharacteristicsDTO ordCharacteristicsDTO) {
        log.debug("Request to partially update OrdCharacteristics : {}", ordCharacteristicsDTO);

        return ordCharacteristicsRepository
            .findById(ordCharacteristicsDTO.getId())
            .map(
                existingOrdCharacteristics -> {
                    ordCharacteristicsMapper.partialUpdate(existingOrdCharacteristics, ordCharacteristicsDTO);

                    return existingOrdCharacteristics;
                }
            )
            .map(ordCharacteristicsRepository::save)
            .map(ordCharacteristicsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdCharacteristicsDTO> findAll() {
        log.debug("Request to get all OrdCharacteristics");
        return ordCharacteristicsRepository
            .findAll()
            .stream()
            .map(ordCharacteristicsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdCharacteristicsDTO> findOne(Long id) {
        log.debug("Request to get OrdCharacteristics : {}", id);
        return ordCharacteristicsRepository.findById(id).map(ordCharacteristicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdCharacteristics : {}", id);
        ordCharacteristicsRepository.deleteById(id);
    }
}
