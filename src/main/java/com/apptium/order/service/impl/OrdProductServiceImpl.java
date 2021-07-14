package com.apptium.order.service.impl;

import com.apptium.order.domain.OrdProduct;
import com.apptium.order.repository.OrdProductRepository;
import com.apptium.order.service.OrdProductService;
import com.apptium.order.service.dto.OrdProductDTO;
import com.apptium.order.service.mapper.OrdProductMapper;
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
 * Service Implementation for managing {@link OrdProduct}.
 */
@Service
@Transactional
public class OrdProductServiceImpl implements OrdProductService {

    private final Logger log = LoggerFactory.getLogger(OrdProductServiceImpl.class);

    private final OrdProductRepository ordProductRepository;

    private final OrdProductMapper ordProductMapper;

    public OrdProductServiceImpl(OrdProductRepository ordProductRepository, OrdProductMapper ordProductMapper) {
        this.ordProductRepository = ordProductRepository;
        this.ordProductMapper = ordProductMapper;
    }

    @Override
    public OrdProductDTO save(OrdProductDTO ordProductDTO) {
        log.debug("Request to save OrdProduct : {}", ordProductDTO);
        OrdProduct ordProduct = ordProductMapper.toEntity(ordProductDTO);
        ordProduct = ordProductRepository.save(ordProduct);
        return ordProductMapper.toDto(ordProduct);
    }

    @Override
    public Optional<OrdProductDTO> partialUpdate(OrdProductDTO ordProductDTO) {
        log.debug("Request to partially update OrdProduct : {}", ordProductDTO);

        return ordProductRepository
            .findById(ordProductDTO.getId())
            .map(
                existingOrdProduct -> {
                    ordProductMapper.partialUpdate(existingOrdProduct, ordProductDTO);

                    return existingOrdProduct;
                }
            )
            .map(ordProductRepository::save)
            .map(ordProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdProductDTO> findAll() {
        log.debug("Request to get all OrdProducts");
        return ordProductRepository.findAll().stream().map(ordProductMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ordProducts where OrdOrderItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductDTO> findAllWhereOrdOrderItemIsNull() {
        log.debug("Request to get all ordProducts where OrdOrderItem is null");
        return StreamSupport
            .stream(ordProductRepository.findAll().spliterator(), false)
            .filter(ordProduct -> ordProduct.getOrdOrderItem() == null)
            .map(ordProductMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdProductDTO> findOne(Long id) {
        log.debug("Request to get OrdProduct : {}", id);
        return ordProductRepository.findById(id).map(ordProductMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdProduct : {}", id);
        ordProductRepository.deleteById(id);
    }
}
