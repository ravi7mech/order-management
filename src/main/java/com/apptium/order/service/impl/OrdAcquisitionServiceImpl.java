package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdAcquisition;
import com.apptium.order.repository.OrdAcquisitionRepository;
import com.apptium.order.service.OrdAcquisitionService;
import com.apptium.order.service.dto.OrdAcquisitionDTO;
import com.apptium.order.service.mapper.OrdAcquisitionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdAcquisition}.
 */
@Service
@Transactional
public class OrdAcquisitionServiceImpl implements OrdAcquisitionService {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionServiceImpl.class);

    private final OrdAcquisitionRepository ordAcquisitionRepository;

    private final OrdAcquisitionMapper ordAcquisitionMapper;

    public OrdAcquisitionServiceImpl(OrdAcquisitionRepository ordAcquisitionRepository, OrdAcquisitionMapper ordAcquisitionMapper) {
        this.ordAcquisitionRepository = ordAcquisitionRepository;
        this.ordAcquisitionMapper = ordAcquisitionMapper;
    }

    @Override
    public OrdAcquisitionDTO save(OrdAcquisitionDTO ordAcquisitionDTO) {
        log.debug("Request to save OrdAcquisition : {}", ordAcquisitionDTO);
        OrdAcquisition ordAcquisition = ordAcquisitionMapper.toEntity(ordAcquisitionDTO);
        ordAcquisition = ordAcquisitionRepository.save(ordAcquisition);
        return ordAcquisitionMapper.toDto(ordAcquisition);
    }

    @Override
    public Optional<OrdAcquisitionDTO> partialUpdate(OrdAcquisitionDTO ordAcquisitionDTO) {
        log.debug("Request to partially update OrdAcquisition : {}", ordAcquisitionDTO);

        return ordAcquisitionRepository
            .findById(ordAcquisitionDTO.getId())
            .map(
                existingOrdAcquisition -> {
                    ordAcquisitionMapper.partialUpdate(existingOrdAcquisition, ordAcquisitionDTO);

                    return existingOrdAcquisition;
                }
            )
            .map(ordAcquisitionRepository::save)
            .map(ordAcquisitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdAcquisitionDTO> findAll() {
        log.debug("Request to get all OrdAcquisitions");
        return ordAcquisitionRepository
            .findAll()
            .stream()
            .map(ordAcquisitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdAcquisitionDTO> findOne(Long id) {
        log.debug("Request to get OrdAcquisition : {}", id);
        return ordAcquisitionRepository.findById(id).map(ordAcquisitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdAcquisition : {}", id);
        ordAcquisitionRepository.deleteById(id);
    }
}
