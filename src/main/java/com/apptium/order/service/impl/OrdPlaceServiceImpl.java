package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdPlace;
import com.apptium.order.repository.OrdPlaceRepository;
import com.apptium.order.service.OrdPlaceService;
import com.apptium.order.service.dto.OrdPlaceDTO;
import com.apptium.order.service.mapper.OrdPlaceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdPlace}.
 */
@Service
@Transactional
public class OrdPlaceServiceImpl implements OrdPlaceService {

    private final Logger log = LoggerFactory.getLogger(OrdPlaceServiceImpl.class);

    private final OrdPlaceRepository ordPlaceRepository;

    private final OrdPlaceMapper ordPlaceMapper;

    public OrdPlaceServiceImpl(OrdPlaceRepository ordPlaceRepository, OrdPlaceMapper ordPlaceMapper) {
        this.ordPlaceRepository = ordPlaceRepository;
        this.ordPlaceMapper = ordPlaceMapper;
    }

    @Override
    public OrdPlaceDTO save(OrdPlaceDTO ordPlaceDTO) {
        log.debug("Request to save OrdPlace : {}", ordPlaceDTO);
        OrdPlace ordPlace = ordPlaceMapper.toEntity(ordPlaceDTO);
        ordPlace = ordPlaceRepository.save(ordPlace);
        return ordPlaceMapper.toDto(ordPlace);
    }

    @Override
    public Optional<OrdPlaceDTO> partialUpdate(OrdPlaceDTO ordPlaceDTO) {
        log.debug("Request to partially update OrdPlace : {}", ordPlaceDTO);

        return ordPlaceRepository
            .findById(ordPlaceDTO.getId())
            .map(
                existingOrdPlace -> {
                    ordPlaceMapper.partialUpdate(existingOrdPlace, ordPlaceDTO);

                    return existingOrdPlace;
                }
            )
            .map(ordPlaceRepository::save)
            .map(ordPlaceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdPlaceDTO> findAll() {
        log.debug("Request to get all OrdPlaces");
        return ordPlaceRepository.findAll().stream().map(ordPlaceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdPlaceDTO> findOne(Long id) {
        log.debug("Request to get OrdPlace : {}", id);
        return ordPlaceRepository.findById(id).map(ordPlaceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdPlace : {}", id);
        ordPlaceRepository.deleteById(id);
    }
}
