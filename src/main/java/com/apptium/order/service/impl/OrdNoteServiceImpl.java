package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdNote;
import com.apptium.order.repository.OrdNoteRepository;
import com.apptium.order.service.OrdNoteService;
import com.apptium.order.service.dto.OrdNoteDTO;
import com.apptium.order.service.mapper.OrdNoteMapper;
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
 * Service Implementation for managing {@link OrdNote}.
 */
@Service
@Transactional
public class OrdNoteServiceImpl implements OrdNoteService {

    private final Logger log = LoggerFactory.getLogger(OrdNoteServiceImpl.class);

    private final OrdNoteRepository ordNoteRepository;

    private final OrdNoteMapper ordNoteMapper;

    public OrdNoteServiceImpl(OrdNoteRepository ordNoteRepository, OrdNoteMapper ordNoteMapper) {
        this.ordNoteRepository = ordNoteRepository;
        this.ordNoteMapper = ordNoteMapper;
    }

    @Override
    public OrdNoteDTO save(OrdNoteDTO ordNoteDTO) {
        log.debug("Request to save OrdNote : {}", ordNoteDTO);
        OrdNote ordNote = ordNoteMapper.toEntity(ordNoteDTO);
        ordNote = ordNoteRepository.save(ordNote);
        return ordNoteMapper.toDto(ordNote);
    }

    @Override
    public Optional<OrdNoteDTO> partialUpdate(OrdNoteDTO ordNoteDTO) {
        log.debug("Request to partially update OrdNote : {}", ordNoteDTO);

        return ordNoteRepository
            .findById(ordNoteDTO.getId())
            .map(
                existingOrdNote -> {
                    ordNoteMapper.partialUpdate(existingOrdNote, ordNoteDTO);

                    return existingOrdNote;
                }
            )
            .map(ordNoteRepository::save)
            .map(ordNoteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdNoteDTO> findAll() {
        log.debug("Request to get all OrdNotes");
        return ordNoteRepository.findAll().stream().map(ordNoteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordNotes where OrdProductOrder is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdNoteDTO> findAllWhereOrdProductOrderIsNull() {
        log.debug("Request to get all ordNotes where OrdProductOrder is null");
        return StreamSupport
            .stream(ordNoteRepository.findAll().spliterator(), false)
            .filter(ordNote -> ordNote.getOrdProductOrder() == null)
            .map(ordNoteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdNoteDTO> findOne(Long id) {
        log.debug("Request to get OrdNote : {}", id);
        return ordNoteRepository.findById(id).map(ordNoteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdNote : {}", id);
        ordNoteRepository.deleteById(id);
    }
}
