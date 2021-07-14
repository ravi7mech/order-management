package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdFulfillmentChar;
import com.apptium.order.repository.OrdFulfillmentCharRepository;
import com.apptium.order.service.OrdFulfillmentCharService;
import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
import com.apptium.order.service.mapper.OrdFulfillmentCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdFulfillmentChar}.
 */
@Service
@Transactional
public class OrdFulfillmentCharServiceImpl implements OrdFulfillmentCharService {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentCharServiceImpl.class);

    private final OrdFulfillmentCharRepository ordFulfillmentCharRepository;

    private final OrdFulfillmentCharMapper ordFulfillmentCharMapper;

    public OrdFulfillmentCharServiceImpl(
        OrdFulfillmentCharRepository ordFulfillmentCharRepository,
        OrdFulfillmentCharMapper ordFulfillmentCharMapper
    ) {
        this.ordFulfillmentCharRepository = ordFulfillmentCharRepository;
        this.ordFulfillmentCharMapper = ordFulfillmentCharMapper;
    }

    @Override
    public OrdFulfillmentCharDTO save(OrdFulfillmentCharDTO ordFulfillmentCharDTO) {
        log.debug("Request to save OrdFulfillmentChar : {}", ordFulfillmentCharDTO);
        OrdFulfillmentChar ordFulfillmentChar = ordFulfillmentCharMapper.toEntity(ordFulfillmentCharDTO);
        ordFulfillmentChar = ordFulfillmentCharRepository.save(ordFulfillmentChar);
        return ordFulfillmentCharMapper.toDto(ordFulfillmentChar);
    }

    @Override
    public Optional<OrdFulfillmentCharDTO> partialUpdate(OrdFulfillmentCharDTO ordFulfillmentCharDTO) {
        log.debug("Request to partially update OrdFulfillmentChar : {}", ordFulfillmentCharDTO);

        return ordFulfillmentCharRepository
            .findById(ordFulfillmentCharDTO.getId())
            .map(
                existingOrdFulfillmentChar -> {
                    ordFulfillmentCharMapper.partialUpdate(existingOrdFulfillmentChar, ordFulfillmentCharDTO);

                    return existingOrdFulfillmentChar;
                }
            )
            .map(ordFulfillmentCharRepository::save)
            .map(ordFulfillmentCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdFulfillmentCharDTO> findAll() {
        log.debug("Request to get all OrdFulfillmentChars");
        return ordFulfillmentCharRepository
            .findAll()
            .stream()
            .map(ordFulfillmentCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdFulfillmentCharDTO> findOne(Long id) {
        log.debug("Request to get OrdFulfillmentChar : {}", id);
        return ordFulfillmentCharRepository.findById(id).map(ordFulfillmentCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdFulfillmentChar : {}", id);
        ordFulfillmentCharRepository.deleteById(id);
    }
}
