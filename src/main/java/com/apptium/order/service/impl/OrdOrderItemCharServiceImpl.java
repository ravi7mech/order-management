package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdOrderItemChar;
import com.apptium.order.repository.OrdOrderItemCharRepository;
import com.apptium.order.service.OrdOrderItemCharService;
import com.apptium.order.service.dto.OrdOrderItemCharDTO;
import com.apptium.order.service.mapper.OrdOrderItemCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdOrderItemChar}.
 */
@Service
@Transactional
public class OrdOrderItemCharServiceImpl implements OrdOrderItemCharService {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemCharServiceImpl.class);

    private final OrdOrderItemCharRepository ordOrderItemCharRepository;

    private final OrdOrderItemCharMapper ordOrderItemCharMapper;

    public OrdOrderItemCharServiceImpl(
        OrdOrderItemCharRepository ordOrderItemCharRepository,
        OrdOrderItemCharMapper ordOrderItemCharMapper
    ) {
        this.ordOrderItemCharRepository = ordOrderItemCharRepository;
        this.ordOrderItemCharMapper = ordOrderItemCharMapper;
    }

    @Override
    public OrdOrderItemCharDTO save(OrdOrderItemCharDTO ordOrderItemCharDTO) {
        log.debug("Request to save OrdOrderItemChar : {}", ordOrderItemCharDTO);
        OrdOrderItemChar ordOrderItemChar = ordOrderItemCharMapper.toEntity(ordOrderItemCharDTO);
        ordOrderItemChar = ordOrderItemCharRepository.save(ordOrderItemChar);
        return ordOrderItemCharMapper.toDto(ordOrderItemChar);
    }

    @Override
    public Optional<OrdOrderItemCharDTO> partialUpdate(OrdOrderItemCharDTO ordOrderItemCharDTO) {
        log.debug("Request to partially update OrdOrderItemChar : {}", ordOrderItemCharDTO);

        return ordOrderItemCharRepository
            .findById(ordOrderItemCharDTO.getId())
            .map(
                existingOrdOrderItemChar -> {
                    ordOrderItemCharMapper.partialUpdate(existingOrdOrderItemChar, ordOrderItemCharDTO);

                    return existingOrdOrderItemChar;
                }
            )
            .map(ordOrderItemCharRepository::save)
            .map(ordOrderItemCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdOrderItemCharDTO> findAll() {
        log.debug("Request to get all OrdOrderItemChars");
        return ordOrderItemCharRepository
            .findAll()
            .stream()
            .map(ordOrderItemCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdOrderItemCharDTO> findOne(Long id) {
        log.debug("Request to get OrdOrderItemChar : {}", id);
        return ordOrderItemCharRepository.findById(id).map(ordOrderItemCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdOrderItemChar : {}", id);
        ordOrderItemCharRepository.deleteById(id);
    }
}
