package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdBillingAccountRef;
import com.apptium.order.repository.OrdBillingAccountRefRepository;
import com.apptium.order.service.criteria.OrdBillingAccountRefCriteria;
import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
import com.apptium.order.service.mapper.OrdBillingAccountRefMapper;
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
 * Service for executing complex queries for {@link OrdBillingAccountRef} entities in the database.
 * The main input is a {@link OrdBillingAccountRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdBillingAccountRefDTO} or a {@link Page} of {@link OrdBillingAccountRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdBillingAccountRefQueryService extends QueryService<OrdBillingAccountRef> {

    private final Logger log = LoggerFactory.getLogger(OrdBillingAccountRefQueryService.class);

    private final OrdBillingAccountRefRepository ordBillingAccountRefRepository;

    private final OrdBillingAccountRefMapper ordBillingAccountRefMapper;

    public OrdBillingAccountRefQueryService(
        OrdBillingAccountRefRepository ordBillingAccountRefRepository,
        OrdBillingAccountRefMapper ordBillingAccountRefMapper
    ) {
        this.ordBillingAccountRefRepository = ordBillingAccountRefRepository;
        this.ordBillingAccountRefMapper = ordBillingAccountRefMapper;
    }

    /**
     * Return a {@link List} of {@link OrdBillingAccountRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdBillingAccountRefDTO> findByCriteria(OrdBillingAccountRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdBillingAccountRef> specification = createSpecification(criteria);
        return ordBillingAccountRefMapper.toDto(ordBillingAccountRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdBillingAccountRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdBillingAccountRefDTO> findByCriteria(OrdBillingAccountRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdBillingAccountRef> specification = createSpecification(criteria);
        return ordBillingAccountRefRepository.findAll(specification, page).map(ordBillingAccountRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdBillingAccountRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdBillingAccountRef> specification = createSpecification(criteria);
        return ordBillingAccountRefRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdBillingAccountRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdBillingAccountRef> createSpecification(OrdBillingAccountRefCriteria criteria) {
        Specification<OrdBillingAccountRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdBillingAccountRef_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdBillingAccountRef_.name));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdBillingAccountRef_.href));
            }
            if (criteria.getCartPriceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCartPriceId(), OrdBillingAccountRef_.cartPriceId));
            }
            if (criteria.getBillingAccountId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBillingAccountId(), OrdBillingAccountRef_.billingAccountId));
            }
            if (criteria.getBillingSystem() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBillingSystem(), OrdBillingAccountRef_.billingSystem));
            }
            if (criteria.getDeliveryMethod() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDeliveryMethod(), OrdBillingAccountRef_.deliveryMethod));
            }
            if (criteria.getBillingAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBillingAddress(), OrdBillingAccountRef_.billingAddress));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdBillingAccountRef_.status));
            }
            if (criteria.getQuoteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteId(), OrdBillingAccountRef_.quoteId));
            }
            if (criteria.getSalesOrderId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalesOrderId(), OrdBillingAccountRef_.salesOrderId));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdBillingAccountRef_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
