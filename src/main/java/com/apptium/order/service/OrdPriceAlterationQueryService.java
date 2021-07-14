package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdPriceAlteration;
import com.apptium.order.repository.OrdPriceAlterationRepository;
import com.apptium.order.service.criteria.OrdPriceAlterationCriteria;
import com.apptium.order.service.dto.OrdPriceAlterationDTO;
import com.apptium.order.service.mapper.OrdPriceAlterationMapper;
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
 * Service for executing complex queries for {@link OrdPriceAlteration} entities in the database.
 * The main input is a {@link OrdPriceAlterationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdPriceAlterationDTO} or a {@link Page} of {@link OrdPriceAlterationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdPriceAlterationQueryService extends QueryService<OrdPriceAlteration> {

    private final Logger log = LoggerFactory.getLogger(OrdPriceAlterationQueryService.class);

    private final OrdPriceAlterationRepository ordPriceAlterationRepository;

    private final OrdPriceAlterationMapper ordPriceAlterationMapper;

    public OrdPriceAlterationQueryService(
        OrdPriceAlterationRepository ordPriceAlterationRepository,
        OrdPriceAlterationMapper ordPriceAlterationMapper
    ) {
        this.ordPriceAlterationRepository = ordPriceAlterationRepository;
        this.ordPriceAlterationMapper = ordPriceAlterationMapper;
    }

    /**
     * Return a {@link List} of {@link OrdPriceAlterationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPriceAlterationDTO> findByCriteria(OrdPriceAlterationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdPriceAlteration> specification = createSpecification(criteria);
        return ordPriceAlterationMapper.toDto(ordPriceAlterationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdPriceAlterationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdPriceAlterationDTO> findByCriteria(OrdPriceAlterationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdPriceAlteration> specification = createSpecification(criteria);
        return ordPriceAlterationRepository.findAll(specification, page).map(ordPriceAlterationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdPriceAlterationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdPriceAlteration> specification = createSpecification(criteria);
        return ordPriceAlterationRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdPriceAlterationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdPriceAlteration> createSpecification(OrdPriceAlterationCriteria criteria) {
        Specification<OrdPriceAlteration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdPriceAlteration_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdPriceAlteration_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OrdPriceAlteration_.description));
            }
            if (criteria.getPriceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriceType(), OrdPriceAlteration_.priceType));
            }
            if (criteria.getUnitOfMeasure() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitOfMeasure(), OrdPriceAlteration_.unitOfMeasure));
            }
            if (criteria.getRecurringChargePeriod() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getRecurringChargePeriod(), OrdPriceAlteration_.recurringChargePeriod)
                    );
            }
            if (criteria.getApplicationDuration() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getApplicationDuration(), OrdPriceAlteration_.applicationDuration));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriority(), OrdPriceAlteration_.priority));
            }
            if (criteria.getOrdPriceAmountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPriceAmountId(),
                            root -> root.join(OrdPriceAlteration_.ordPriceAmount, JoinType.LEFT).get(OrdPriceAmount_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderPriceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderPriceId(),
                            root -> root.join(OrdPriceAlteration_.ordOrderPrice, JoinType.LEFT).get(OrdOrderPrice_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
