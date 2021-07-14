package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdProvisiongChar;
import com.apptium.order.repository.OrdProvisiongCharRepository;
import com.apptium.order.service.OrdProvisiongCharService;
import com.apptium.order.service.dto.OrdProvisiongCharDTO;
import com.apptium.order.service.mapper.OrdProvisiongCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdProvisiongChar}.
 */
@Service
@Transactional
public class OrdProvisiongCharServiceImpl implements OrdProvisiongCharService {

    private final Logger log = LoggerFactory.getLogger(OrdProvisiongCharServiceImpl.class);

    private final OrdProvisiongCharRepository ordProvisiongCharRepository;

    private final OrdProvisiongCharMapper ordProvisiongCharMapper;

    public OrdProvisiongCharServiceImpl(
        OrdProvisiongCharRepository ordProvisiongCharRepository,
        OrdProvisiongCharMapper ordProvisiongCharMapper
    ) {
        this.ordProvisiongCharRepository = ordProvisiongCharRepository;
        this.ordProvisiongCharMapper = ordProvisiongCharMapper;
    }

    @Override
    public OrdProvisiongCharDTO save(OrdProvisiongCharDTO ordProvisiongCharDTO) {
        log.debug("Request to save OrdProvisiongChar : {}", ordProvisiongCharDTO);
        OrdProvisiongChar ordProvisiongChar = ordProvisiongCharMapper.toEntity(ordProvisiongCharDTO);
        ordProvisiongChar = ordProvisiongCharRepository.save(ordProvisiongChar);
        return ordProvisiongCharMapper.toDto(ordProvisiongChar);
    }

    @Override
    public Optional<OrdProvisiongCharDTO> partialUpdate(OrdProvisiongCharDTO ordProvisiongCharDTO) {
        log.debug("Request to partially update OrdProvisiongChar : {}", ordProvisiongCharDTO);

        return ordProvisiongCharRepository
            .findById(ordProvisiongCharDTO.getId())
            .map(
                existingOrdProvisiongChar -> {
                    ordProvisiongCharMapper.partialUpdate(existingOrdProvisiongChar, ordProvisiongCharDTO);

                    return existingOrdProvisiongChar;
                }
            )
            .map(ordProvisiongCharRepository::save)
            .map(ordProvisiongCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdProvisiongCharDTO> findAll() {
        log.debug("Request to get all OrdProvisiongChars");
        return ordProvisiongCharRepository
            .findAll()
            .stream()
            .map(ordProvisiongCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdProvisiongCharDTO> findOne(Long id) {
        log.debug("Request to get OrdProvisiongChar : {}", id);
        return ordProvisiongCharRepository.findById(id).map(ordProvisiongCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdProvisiongChar : {}", id);
        ordProvisiongCharRepository.deleteById(id);
    }
}
