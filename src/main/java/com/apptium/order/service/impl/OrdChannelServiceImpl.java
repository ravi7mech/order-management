package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdChannel;
import com.apptium.order.repository.OrdChannelRepository;
import com.apptium.order.service.OrdChannelService;
import com.apptium.order.service.dto.OrdChannelDTO;
import com.apptium.order.service.mapper.OrdChannelMapper;
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
 * Service Implementation for managing {@link OrdChannel}.
 */
@Service
@Transactional
public class OrdChannelServiceImpl implements OrdChannelService {

    private final Logger log = LoggerFactory.getLogger(OrdChannelServiceImpl.class);

    private final OrdChannelRepository ordChannelRepository;

    private final OrdChannelMapper ordChannelMapper;

    public OrdChannelServiceImpl(OrdChannelRepository ordChannelRepository, OrdChannelMapper ordChannelMapper) {
        this.ordChannelRepository = ordChannelRepository;
        this.ordChannelMapper = ordChannelMapper;
    }

    @Override
    public OrdChannelDTO save(OrdChannelDTO ordChannelDTO) {
        log.debug("Request to save OrdChannel : {}", ordChannelDTO);
        OrdChannel ordChannel = ordChannelMapper.toEntity(ordChannelDTO);
        ordChannel = ordChannelRepository.save(ordChannel);
        return ordChannelMapper.toDto(ordChannel);
    }

    @Override
    public Optional<OrdChannelDTO> partialUpdate(OrdChannelDTO ordChannelDTO) {
        log.debug("Request to partially update OrdChannel : {}", ordChannelDTO);

        return ordChannelRepository
            .findById(ordChannelDTO.getId())
            .map(
                existingOrdChannel -> {
                    ordChannelMapper.partialUpdate(existingOrdChannel, ordChannelDTO);

                    return existingOrdChannel;
                }
            )
            .map(ordChannelRepository::save)
            .map(ordChannelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdChannelDTO> findAll() {
        log.debug("Request to get all OrdChannels");
        return ordChannelRepository.findAll().stream().map(ordChannelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordChannels where OrdProductOrder is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdChannelDTO> findAllWhereOrdProductOrderIsNull() {
        log.debug("Request to get all ordChannels where OrdProductOrder is null");
        return StreamSupport
            .stream(ordChannelRepository.findAll().spliterator(), false)
            .filter(ordChannel -> ordChannel.getOrdProductOrder() == null)
            .map(ordChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdChannelDTO> findOne(Long id) {
        log.debug("Request to get OrdChannel : {}", id);
        return ordChannelRepository.findById(id).map(ordChannelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdChannel : {}", id);
        ordChannelRepository.deleteById(id);
    }
}
