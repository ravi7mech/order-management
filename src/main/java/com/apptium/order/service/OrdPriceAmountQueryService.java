package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdPriceAmount;
import com.apptium.order.repository.OrdPriceAmountRepository;
import com.apptium.order.service.criteria.OrdPriceAmountCriteria;
import com.apptium.order.service.dto.OrdPriceAmountDTO;
import com.apptium.order.service.mapper.OrdPriceAmountMapper;
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
 * Service for executing complex queries for {@link OrdPriceAmount} entities in the database.
 * The main input is a {@link OrdPriceAmountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdPriceAmountDTO} or a {@link Page} of {@link OrdPriceAmountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdPriceAmountQueryService extends QueryService<OrdPriceAmount> {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAmountQueryService.class);

    private final OrdPriceAmountRepository ordPriceAmountRepository;

    private final OrdPriceAmountMapper ordPriceAmountMapper;

    public OrdPriceAmountQueryService(OrdPriceAmountRepository ordPriceAmountRepository, OrdPriceAmountMapper ordPriceAmountMapper) {
        this.ordPriceAmountRepository = ordPriceAmountRepository;
        this.ordPriceAmountMapper = ordPriceAmountMapper;
    }

    /**
     * Return a {@link List} of {@link OrdPriceAmountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPriceAmountDTO> findByCriteria(OrdPriceAmountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdPriceAmount> specification = createSpecification(criteria);
        return ordPriceAmountMapper.toDto(ordPriceAmountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdPriceAmountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdPriceAmountDTO> findByCriteria(OrdPriceAmountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdPriceAmount> specification = createSpecification(criteria);
        return ordPriceAmountRepository.findAll(specification, page).map(ordPriceAmountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdPriceAmountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdPriceAmount> specification = createSpecification(criteria);
        return ordPriceAmountRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdPriceAmountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdPriceAmount> createSpecification(OrdPriceAmountCriteria criteria) {
        Specification<OrdPriceAmount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdPriceAmount_.id));
            }
            if (criteria.getCurrencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyCode(), OrdPriceAmount_.currencyCode));
            }
            if (criteria.getTaxIncludedAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTaxIncludedAmount(), OrdPriceAmount_.taxIncludedAmount));
            }
            if (criteria.getDutyFreeAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDutyFreeAmount(), OrdPriceAmount_.dutyFreeAmount));
            }
            if (criteria.getTaxRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxRate(), OrdPriceAmount_.taxRate));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), OrdPriceAmount_.percentage));
            }
            if (criteria.getTotalRecurringPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalRecurringPrice(), OrdPriceAmount_.totalRecurringPrice));
            }
            if (criteria.getTotalOneTimePrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalOneTimePrice(), OrdPriceAmount_.totalOneTimePrice));
            }
            if (criteria.getOrdOrderPriceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderPriceId(),
                            root -> root.join(OrdPriceAmount_.ordOrderPrice, JoinType.LEFT).get(OrdOrderPrice_.id)
                        )
                    );
            }
            if (criteria.getOrdPriceAlterationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPriceAlterationId(),
                            root -> root.join(OrdPriceAmount_.ordPriceAlteration, JoinType.LEFT).get(OrdPriceAlteration_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
