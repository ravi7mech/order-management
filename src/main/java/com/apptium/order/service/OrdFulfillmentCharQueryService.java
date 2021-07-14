package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdFulfillmentChar;
import com.apptium.order.repository.OrdFulfillmentCharRepository;
import com.apptium.order.service.criteria.OrdFulfillmentCharCriteria;
import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
import com.apptium.order.service.mapper.OrdFulfillmentCharMapper;
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
 * Service for executing complex queries for {@link OrdFulfillmentChar} entities in the database.
 * The main input is a {@link OrdFulfillmentCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdFulfillmentCharDTO} or a {@link Page} of {@link OrdFulfillmentCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdFulfillmentCharQueryService extends QueryService<OrdFulfillmentChar> {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentCharQueryService.class);

    private final OrdFulfillmentCharRepository ordFulfillmentCharRepository;

    private final OrdFulfillmentCharMapper ordFulfillmentCharMapper;

    public OrdFulfillmentCharQueryService(
        OrdFulfillmentCharRepository ordFulfillmentCharRepository,
        OrdFulfillmentCharMapper ordFulfillmentCharMapper
    ) {
        this.ordFulfillmentCharRepository = ordFulfillmentCharRepository;
        this.ordFulfillmentCharMapper = ordFulfillmentCharMapper;
    }

    /**
     * Return a {@link List} of {@link OrdFulfillmentCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdFulfillmentCharDTO> findByCriteria(OrdFulfillmentCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdFulfillmentChar> specification = createSpecification(criteria);
        return ordFulfillmentCharMapper.toDto(ordFulfillmentCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdFulfillmentCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdFulfillmentCharDTO> findByCriteria(OrdFulfillmentCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdFulfillmentChar> specification = createSpecification(criteria);
        return ordFulfillmentCharRepository.findAll(specification, page).map(ordFulfillmentCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdFulfillmentCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdFulfillmentChar> specification = createSpecification(criteria);
        return ordFulfillmentCharRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdFulfillmentCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdFulfillmentChar> createSpecification(OrdFulfillmentCharCriteria criteria) {
        Specification<OrdFulfillmentChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdFulfillmentChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdFulfillmentChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdFulfillmentChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdFulfillmentChar_.valueType));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrdFulfillmentChar_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdFulfillmentChar_.createdDate));
            }
            if (criteria.getOrdFulfillmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdFulfillmentId(),
                            root -> root.join(OrdFulfillmentChar_.ordFulfillment, JoinType.LEFT).get(OrdFulfillment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
