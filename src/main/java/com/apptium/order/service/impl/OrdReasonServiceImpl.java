package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdReason;
import com.apptium.order.repository.OrdReasonRepository;
import com.apptium.order.service.OrdReasonService;
import com.apptium.order.service.dto.OrdReasonDTO;
import com.apptium.order.service.mapper.OrdReasonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrdReason}.
 */
@Service
@Transactional
public class OrdReasonServiceImpl implements OrdReasonService {

    private final Logger log = LoggerFactory.getLogger(OrdReasonServiceImpl.class);

    private final OrdReasonRepository ordReasonRepository;

    private final OrdReasonMapper ordReasonMapper;

    public OrdReasonServiceImpl(OrdReasonRepository ordReasonRepository, OrdReasonMapper ordReasonMapper) {
        this.ordReasonRepository = ordReasonRepository;
        this.ordReasonMapper = ordReasonMapper;
    }

    @Override
    public OrdReasonDTO save(OrdReasonDTO ordReasonDTO) {
        log.debug("Request to save OrdReason : {}", ordReasonDTO);
        OrdReason ordReason = ordReasonMapper.toEntity(ordReasonDTO);
        ordReason = ordReasonRepository.save(ordReason);
        return ordReasonMapper.toDto(ordReason);
    }

    @Override
    public Optional<OrdReasonDTO> partialUpdate(OrdReasonDTO ordReasonDTO) {
        log.debug("Request to partially update OrdReason : {}", ordReasonDTO);

        return ordReasonRepository
            .findById(ordReasonDTO.getId())
            .map(
                existingOrdReason -> {
                    ordReasonMapper.partialUpdate(existingOrdReason, ordReasonDTO);

                    return existingOrdReason;
                }
            )
            .map(ordReasonRepository::save)
            .map(ordReasonMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdReasonDTO> findAll() {
        log.debug("Request to get all OrdReasons");
        return ordReasonRepository.findAll().stream().map(ordReasonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdReasonDTO> findOne(Long id) {
        log.debug("Request to get OrdReason : {}", id);
        return ordReasonRepository.findById(id).map(ordReasonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdReason : {}", id);
        ordReasonRepository.deleteById(id);
    }
}
