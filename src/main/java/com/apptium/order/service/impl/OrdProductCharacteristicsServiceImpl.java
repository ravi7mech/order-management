package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdProductCharacteristics;
import com.apptium.order.repository.OrdProductCharacteristicsRepository;
import com.apptium.order.service.OrdProductCharacteristicsService;
import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdProductCharacteristicsMapper;
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
 * Service Implementation for managing {@link OrdProductCharacteristics}.
 */
@Service
@Transactional
public class OrdProductCharacteristicsServiceImpl implements OrdProductCharacteristicsService {

    private final Logger log = LoggerFactory.getLogger(OrdProductCharacteristicsServiceImpl.class);

    private final OrdProductCharacteristicsRepository ordProductCharacteristicsRepository;

    private final OrdProductCharacteristicsMapper ordProductCharacteristicsMapper;

    public OrdProductCharacteristicsServiceImpl(
        OrdProductCharacteristicsRepository ordProductCharacteristicsRepository,
        OrdProductCharacteristicsMapper ordProductCharacteristicsMapper
    ) {
        this.ordProductCharacteristicsRepository = ordProductCharacteristicsRepository;
        this.ordProductCharacteristicsMapper = ordProductCharacteristicsMapper;
    }

    @Override
    public OrdProductCharacteristicsDTO save(OrdProductCharacteristicsDTO ordProductCharacteristicsDTO) {
        log.debug("Request to save OrdProductCharacteristics : {}", ordProductCharacteristicsDTO);
        OrdProductCharacteristics ordProductCharacteristics = ordProductCharacteristicsMapper.toEntity(ordProductCharacteristicsDTO);
        ordProductCharacteristics = ordProductCharacteristicsRepository.save(ordProductCharacteristics);
        return ordProductCharacteristicsMapper.toDto(ordProductCharacteristics);
    }

    @Override
    public Optional<OrdProductCharacteristicsDTO> partialUpdate(OrdProductCharacteristicsDTO ordProductCharacteristicsDTO) {
        log.debug("Request to partially update OrdProductCharacteristics : {}", ordProductCharacteristicsDTO);

        return ordProductCharacteristicsRepository
            .findById(ordProductCharacteristicsDTO.getId())
            .map(
                existingOrdProductCharacteristics -> {
                    ordProductCharacteristicsMapper.partialUpdate(existingOrdProductCharacteristics, ordProductCharacteristicsDTO);

                    return existingOrdProductCharacteristics;
                }
            )
            .map(ordProductCharacteristicsRepository::save)
            .map(ordProductCharacteristicsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdProductCharacteristicsDTO> findAll() {
        log.debug("Request to get all OrdProductCharacteristics");
        return ordProductCharacteristicsRepository
            .findAll()
            .stream()
            .map(ordProductCharacteristicsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordProductCharacteristics where OrdProduct is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductCharacteristicsDTO> findAllWhereOrdProductIsNull() {
        log.debug("Request to get all ordProductCharacteristics where OrdProduct is null");
        return StreamSupport
            .stream(ordProductCharacteristicsRepository.findAll().spliterator(), false)
            .filter(ordProductCharacteristics -> ordProductCharacteristics.getOrdProduct() == null)
            .map(ordProductCharacteristicsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdProductCharacteristicsDTO> findOne(Long id) {
        log.debug("Request to get OrdProductCharacteristics : {}", id);
        return ordProductCharacteristicsRepository.findById(id).map(ordProductCharacteristicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdProductCharacteristics : {}", id);
        ordProductCharacteristicsRepository.deleteById(id);
    }
}
