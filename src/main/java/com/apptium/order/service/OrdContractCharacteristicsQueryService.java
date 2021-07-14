package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdContractCharacteristics;
import com.apptium.order.repository.OrdContractCharacteristicsRepository;
import com.apptium.order.service.criteria.OrdContractCharacteristicsCriteria;
import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
import com.apptium.order.service.mapper.OrdContractCharacteristicsMapper;
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
 * Service for executing complex queries for {@link OrdContractCharacteristics} entities in the database.
 * The main input is a {@link OrdContractCharacteristicsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdContractCharacteristicsDTO} or a {@link Page} of {@link OrdContractCharacteristicsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdContractCharacteristicsQueryService extends QueryService<OrdContractCharacteristics> {

    private final Logger log = LoggerFactory.getLogger(OrdContractCharacteristicsQueryService.class);

    private final OrdContractCharacteristicsRepository ordContractCharacteristicsRepository;

    private final OrdContractCharacteristicsMapper ordContractCharacteristicsMapper;

    public OrdContractCharacteristicsQueryService(
        OrdContractCharacteristicsRepository ordContractCharacteristicsRepository,
        OrdContractCharacteristicsMapper ordContractCharacteristicsMapper
    ) {
        this.ordContractCharacteristicsRepository = ordContractCharacteristicsRepository;
        this.ordContractCharacteristicsMapper = ordContractCharacteristicsMapper;
    }

    /**
     * Return a {@link List} of {@link OrdContractCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdContractCharacteristicsDTO> findByCriteria(OrdContractCharacteristicsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdContractCharacteristics> specification = createSpecification(criteria);
        return ordContractCharacteristicsMapper.toDto(ordContractCharacteristicsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdContractCharacteristicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdContractCharacteristicsDTO> findByCriteria(OrdContractCharacteristicsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdContractCharacteristics> specification = createSpecification(criteria);
        return ordContractCharacteristicsRepository.findAll(specification, page).map(ordContractCharacteristicsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdContractCharacteristicsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdContractCharacteristics> specification = createSpecification(criteria);
        return ordContractCharacteristicsRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdContractCharacteristicsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdContractCharacteristics> createSpecification(OrdContractCharacteristicsCriteria criteria) {
        Specification<OrdContractCharacteristics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdContractCharacteristics_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdContractCharacteristics_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdContractCharacteristics_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdContractCharacteristics_.valueType));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrdContractCharacteristics_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdContractCharacteristics_.createdDate));
            }
            if (criteria.getOrdContractId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdContractId(),
                            root -> root.join(OrdContractCharacteristics_.ordContract, JoinType.LEFT).get(OrdContract_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
