package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdContract;
import com.apptium.order.repository.OrdContractRepository;
import com.apptium.order.service.OrdContractService;
import com.apptium.order.service.dto.OrdContractDTO;
import com.apptium.order.service.mapper.OrdContractMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdContract}.
 */
@Service
@Transactional
public class OrdContractServiceImpl implements OrdContractService {

    private final Logger log = LoggerFactory.getLogger(OrdContractServiceImpl.class);

    private final OrdContractRepository ordContractRepository;

    private final OrdContractMapper ordContractMapper;

    public OrdContractServiceImpl(OrdContractRepository ordContractRepository, OrdContractMapper ordContractMapper) {
        this.ordContractRepository = ordContractRepository;
        this.ordContractMapper = ordContractMapper;
    }

    @Override
    public OrdContractDTO save(OrdContractDTO ordContractDTO) {
        log.debug("Request to save OrdContract : {}", ordContractDTO);
        OrdContract ordContract = ordContractMapper.toEntity(ordContractDTO);
        ordContract = ordContractRepository.save(ordContract);
        return ordContractMapper.toDto(ordContract);
    }

    @Override
    public Optional<OrdContractDTO> partialUpdate(OrdContractDTO ordContractDTO) {
        log.debug("Request to partially update OrdContract : {}", ordContractDTO);

        return ordContractRepository
            .findById(ordContractDTO.getId())
            .map(
                existingOrdContract -> {
                    ordContractMapper.partialUpdate(existingOrdContract, ordContractDTO);

                    return existingOrdContract;
                }
            )
            .map(ordContractRepository::save)
            .map(ordContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdContractDTO> findAll() {
        log.debug("Request to get all OrdContracts");
        return ordContractRepository.findAll().stream().map(ordContractMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdContractDTO> findOne(Long id) {
        log.debug("Request to get OrdContract : {}", id);
        return ordContractRepository.findById(id).map(ordContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdContract : {}", id);
        ordContractRepository.deleteById(id);
    }
}
