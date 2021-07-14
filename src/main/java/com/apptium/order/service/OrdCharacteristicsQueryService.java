package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdCharacteristics;
import com.apptium.order.repository.OrdCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdCharacteristicsMapper;
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
 * Service for executing complex queries for {@link OrdCharacteristics} entities in the database.
 * The main input is a {@link OrdCharacteristicsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdCharacteristicsDTO} or a {@link Page} of {@link OrdCharacteristicsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdCharacteristicsQueryService extends QueryService<OrdCharacteristics> {

    private final Logger log = LoggerFactory.getLogger(OrdCharacteristicsQueryService.class);

    private final OrdCharacteristicsRepository ordCharacteristicsRepository;

    private final OrdCharacteristicsMapper ordCharacteristicsMapper;

    public OrdCharacteristicsQueryService(
        OrdCharacteristicsRepository ordCharacteristicsRepository,
        OrdCharacteristicsMapper ordCharacteristicsMapper
    ) {
        this.ordCharacteristicsRepository = ordCharacteristicsRepository;
        this.ordCharacteristicsMapper = ordCharacteristicsMapper;
    }

    /**
     * Return a {@link List} of {@link OrdCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdCharacteristicsDTO> findByCriteria(OrdCharacteristicsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdCharacteristics> specification = createSpecification(criteria);
        return ordCharacteristicsMapper.toDto(ordCharacteristicsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdCharacteristicsDTO> findByCriteria(OrdCharacteristicsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdCharacteristics> specification = createSpecification(criteria);
        return ordCharacteristicsRepository.findAll(specification, page).map(ordCharacteristicsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdCharacteristicsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdCharacteristics> specification = createSpecification(criteria);
        return ordCharacteristicsRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdCharacteristicsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdCharacteristics> createSpecification(OrdCharacteristicsCriteria criteria) {
        Specification<OrdCharacteristics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdCharacteristics_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdCharacteristics_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdCharacteristics_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdCharacteristics_.valueType));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdCharacteristics_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
