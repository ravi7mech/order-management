package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdAcquisitionChar;
import com.apptium.order.repository.OrdAcquisitionCharRepository;
import com.apptium.order.service.OrdAcquisitionCharService;
import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
import com.apptium.order.service.mapper.OrdAcquisitionCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdAcquisitionChar}.
 */
@Service
@Transactional
public class OrdAcquisitionCharServiceImpl implements OrdAcquisitionCharService {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionCharServiceImpl.class);

    private final OrdAcquisitionCharRepository ordAcquisitionCharRepository;

    private final OrdAcquisitionCharMapper ordAcquisitionCharMapper;

    public OrdAcquisitionCharServiceImpl(
        OrdAcquisitionCharRepository ordAcquisitionCharRepository,
        OrdAcquisitionCharMapper ordAcquisitionCharMapper
    ) {
        this.ordAcquisitionCharRepository = ordAcquisitionCharRepository;
        this.ordAcquisitionCharMapper = ordAcquisitionCharMapper;
    }

    @Override
    public OrdAcquisitionCharDTO save(OrdAcquisitionCharDTO ordAcquisitionCharDTO) {
        log.debug("Request to save OrdAcquisitionChar : {}", ordAcquisitionCharDTO);
        OrdAcquisitionChar ordAcquisitionChar = ordAcquisitionCharMapper.toEntity(ordAcquisitionCharDTO);
        ordAcquisitionChar = ordAcquisitionCharRepository.save(ordAcquisitionChar);
        return ordAcquisitionCharMapper.toDto(ordAcquisitionChar);
    }

    @Override
    public Optional<OrdAcquisitionCharDTO> partialUpdate(OrdAcquisitionCharDTO ordAcquisitionCharDTO) {
        log.debug("Request to partially update OrdAcquisitionChar : {}", ordAcquisitionCharDTO);

        return ordAcquisitionCharRepository
            .findById(ordAcquisitionCharDTO.getId())
            .map(
                existingOrdAcquisitionChar -> {
                    ordAcquisitionCharMapper.partialUpdate(existingOrdAcquisitionChar, ordAcquisitionCharDTO);

                    return existingOrdAcquisitionChar;
                }
            )
            .map(ordAcquisitionCharRepository::save)
            .map(ordAcquisitionCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdAcquisitionCharDTO> findAll() {
        log.debug("Request to get all OrdAcquisitionChars");
        return ordAcquisitionCharRepository
            .findAll()
            .stream()
            .map(ordAcquisitionCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdAcquisitionCharDTO> findOne(Long id) {
        log.debug("Request to get OrdAcquisitionChar : {}", id);
        return ordAcquisitionCharRepository.findById(id).map(ordAcquisitionCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdAcquisitionChar : {}", id);
        ordAcquisitionCharRepository.deleteById(id);
    }
}
