package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdProductCharacteristics;
import com.apptium.order.repository.OrdProductCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdProductCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdProductCharacteristicsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OrdProductCharacteristics} entities in the database.
 * The main input is a {@link OrdProductCharacteristicsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdProductCharacteristicsDTO} or a {@link Page} of {@link OrdProductCharacteristicsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdProductCharacteristicsQueryService extends QueryService<OrdProductCharacteristics> {

    private final Logger log = LoggerFactory.getLogger(OrdProductCharacteristicsQueryService.class);

    private final OrdProductCharacteristicsRepository ordProductCharacteristicsRepository;

    private final OrdProductCharacteristicsMapper ordProductCharacteristicsMapper;

    public OrdProductCharacteristicsQueryService(
        OrdProductCharacteristicsRepository ordProductCharacteristicsRepository,
        OrdProductCharacteristicsMapper ordProductCharacteristicsMapper
    ) {
        this.ordProductCharacteristicsRepository = ordProductCharacteristicsRepository;
        this.ordProductCharacteristicsMapper = ordProductCharacteristicsMapper;
    }

    /**
     * Return a {@link List} of {@link OrdProductCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductCharacteristicsDTO> findByCriteria(OrdProductCharacteristicsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdProductCharacteristics> specification = createSpecification(criteria);
        return ordProductCharacteristicsMapper.toDto(ordProductCharacteristicsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdProductCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdProductCharacteristicsDTO> findByCriteria(OrdProductCharacteristicsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdProductCharacteristics> specification = createSpecification(criteria);
        return ordProductCharacteristicsRepository.findAll(specification, page).map(ordProductCharacteristicsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdProductCharacteristicsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdProductCharacteristics> specification = createSpecification(criteria);
        return ordProductCharacteristicsRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdProductCharacteristicsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdProductCharacteristics> createSpecification(OrdProductCharacteristicsCriteria criteria) {
        Specification<OrdProductCharacteristics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdProductCharacteristics_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdProductCharacteristics_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdProductCharacteristics_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdProductCharacteristics_.valueType));
            }
            if (criteria.getOrdProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductId(),
                            root -> root.join(OrdProductCharacteristics_.ordProduct, JoinType.LEFT).get(OrdProduct_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
