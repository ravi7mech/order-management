package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdProvisiongChar;
import com.apptium.order.repository.OrdProvisiongCharRepository;
import com.apptium.order.service.criteria.OrdProvisiongCharCriteria;
import com.apptium.order.service.dto.OrdProvisiongCharDTO;
import com.apptium.order.service.mapper.OrdProvisiongCharMapper;
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
 * Service for executing complex queries for {@link OrdProvisiongChar} entities in the database.
 * The main input is a {@link OrdProvisiongCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdProvisiongCharDTO} or a {@link Page} of {@link OrdProvisiongCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdProvisiongCharQueryService extends QueryService<OrdProvisiongChar> {

    private final Logger log = LoggerFactory.getLogger(OrdProvisiongCharQueryService.class);

    private final OrdProvisiongCharRepository ordProvisiongCharRepository;

    private final OrdProvisiongCharMapper ordProvisiongCharMapper;

    public OrdProvisiongCharQueryService(
        OrdProvisiongCharRepository ordProvisiongCharRepository,
        OrdProvisiongCharMapper ordProvisiongCharMapper
    ) {
        this.ordProvisiongCharRepository = ordProvisiongCharRepository;
        this.ordProvisiongCharMapper = ordProvisiongCharMapper;
    }

    /**
     * Return a {@link List} of {@link OrdProvisiongCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProvisiongCharDTO> findByCriteria(OrdProvisiongCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdProvisiongChar> specification = createSpecification(criteria);
        return ordProvisiongCharMapper.toDto(ordProvisiongCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdProvisiongCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdProvisiongCharDTO> findByCriteria(OrdProvisiongCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdProvisiongChar> specification = createSpecification(criteria);
        return ordProvisiongCharRepository.findAll(specification, page).map(ordProvisiongCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdProvisiongCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdProvisiongChar> specification = createSpecification(criteria);
        return ordProvisiongCharRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdProvisiongCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdProvisiongChar> createSpecification(OrdProvisiongCharCriteria criteria) {
        Specification<OrdProvisiongChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdProvisiongChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdProvisiongChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdProvisiongChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdProvisiongChar_.valueType));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrdProvisiongChar_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdProvisiongChar_.createdDate));
            }
            if (criteria.getOrdOrderItemProvisioningId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemProvisioningId(),
                            root -> root.join(OrdProvisiongChar_.ordOrderItemProvisioning, JoinType.LEFT).get(OrdOrderItemProvisioning_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
