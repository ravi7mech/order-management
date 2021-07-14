package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdPaymentRef;
import com.apptium.order.repository.OrdPaymentRefRepository;
import com.apptium.order.service.criteria.OrdPaymentRefCriteria;
import com.apptium.order.service.dto.OrdPaymentRefDTO;
import com.apptium.order.service.mapper.OrdPaymentRefMapper;
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
 * Service for executing complex queries for {@link OrdPaymentRef} entities in the database.
 * The main input is a {@link OrdPaymentRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdPaymentRefDTO} or a {@link Page} of {@link OrdPaymentRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdPaymentRefQueryService extends QueryService<OrdPaymentRef> {

    private final Logger log = LoggerFactory.getLogger(OrdPaymentRefQueryService.class);

    private final OrdPaymentRefRepository ordPaymentRefRepository;

    private final OrdPaymentRefMapper ordPaymentRefMapper;

    public OrdPaymentRefQueryService(OrdPaymentRefRepository ordPaymentRefRepository, OrdPaymentRefMapper ordPaymentRefMapper) {
        this.ordPaymentRefRepository = ordPaymentRefRepository;
        this.ordPaymentRefMapper = ordPaymentRefMapper;
    }

    /**
     * Return a {@link List} of {@link OrdPaymentRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPaymentRefDTO> findByCriteria(OrdPaymentRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdPaymentRef> specification = createSpecification(criteria);
        return ordPaymentRefMapper.toDto(ordPaymentRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdPaymentRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdPaymentRefDTO> findByCriteria(OrdPaymentRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdPaymentRef> specification = createSpecification(criteria);
        return ordPaymentRefRepository.findAll(specification, page).map(ordPaymentRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdPaymentRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdPaymentRef> specification = createSpecification(criteria);
        return ordPaymentRefRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdPaymentRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdPaymentRef> createSpecification(OrdPaymentRefCriteria criteria) {
        Specification<OrdPaymentRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdPaymentRef_.id));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentId(), OrdPaymentRef_.paymentId));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdPaymentRef_.href));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdPaymentRef_.name));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentAmount(), OrdPaymentRef_.paymentAmount));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), OrdPaymentRef_.action));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdPaymentRef_.status));
            }
            if (criteria.getEnrolRecurring() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnrolRecurring(), OrdPaymentRef_.enrolRecurring));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdPaymentRef_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
