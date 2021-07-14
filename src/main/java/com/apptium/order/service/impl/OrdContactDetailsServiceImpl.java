package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdContactDetails;
import com.apptium.order.repository.OrdContactDetailsRepository;
import com.apptium.order.service.OrdContactDetailsService;
import com.apptium.order.service.dto.OrdContactDetailsDTO;
import com.apptium.order.service.mapper.OrdContactDetailsMapper;
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
 * Service Implementation for managing {@link OrdContactDetails}.
 */
@Service
@Transactional
public class OrdContactDetailsServiceImpl implements OrdContactDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrdContactDetailsServiceImpl.class);

    private final OrdContactDetailsRepository ordContactDetailsRepository;

    private final OrdContactDetailsMapper ordContactDetailsMapper;

    public OrdContactDetailsServiceImpl(
        OrdContactDetailsRepository ordContactDetailsRepository,
        OrdContactDetailsMapper ordContactDetailsMapper
    ) {
        this.ordContactDetailsRepository = ordContactDetailsRepository;
        this.ordContactDetailsMapper = ordContactDetailsMapper;
    }

    @Override
    public OrdContactDetailsDTO save(OrdContactDetailsDTO ordContactDetailsDTO) {
        log.debug("Request to save OrdContactDetails : {}", ordContactDetailsDTO);
        OrdContactDetails ordContactDetails = ordContactDetailsMapper.toEntity(ordContactDetailsDTO);
        ordContactDetails = ordContactDetailsRepository.save(ordContactDetails);
        return ordContactDetailsMapper.toDto(ordContactDetails);
    }

    @Override
    public Optional<OrdContactDetailsDTO> partialUpdate(OrdContactDetailsDTO ordContactDetailsDTO) {
        log.debug("Request to partially update OrdContactDetails : {}", ordContactDetailsDTO);

        return ordContactDetailsRepository
            .findById(ordContactDetailsDTO.getId())
            .map(
                existingOrdContactDetails -> {
                    ordContactDetailsMapper.partialUpdate(existingOrdContactDetails, ordContactDetailsDTO);

                    return existingOrdContactDetails;
                }
            )
            .map(ordContactDetailsRepository::save)
            .map(ordContactDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdContactDetailsDTO> findAll() {
        log.debug("Request to get all OrdContactDetails");
        return ordContactDetailsRepository
            .findAll()
            .stream()
            .map(ordContactDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordContactDetails where OrdProductOrder is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdContactDetailsDTO> findAllWhereOrdProductOrderIsNull() {
        log.debug("Request to get all ordContactDetails where OrdProductOrder is null");
        return StreamSupport
            .stream(ordContactDetailsRepository.findAll().spliterator(), false)
            .filter(ordContactDetails -> ordContactDetails.getOrdProductOrder() == null)
            .map(ordContactDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdContactDetailsDTO> findOne(Long id) {
        log.debug("Request to get OrdContactDetails : {}", id);
        return ordContactDetailsRepository.findById(id).map(ordContactDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdContactDetails : {}", id);
        ordContactDetailsRepository.deleteById(id);
    }
}
