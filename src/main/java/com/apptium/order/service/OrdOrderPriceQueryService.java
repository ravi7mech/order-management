package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdOrderPrice;
import com.apptium.order.repository.OrdOrderPriceRepository;
import com.apptium.order.service.criteria.OrdOrderPriceCriteria;
import com.apptium.order.service.dto.OrdOrderPriceDTO;
import com.apptium.order.service.mapper.OrdOrderPriceMapper;
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
 * Service for executing complex queries for {@link OrdOrderPrice} entities in the database.
 * The main input is a {@link OrdOrderPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdOrderPriceDTO} or a {@link Page} of {@link OrdOrderPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdOrderPriceQueryService extends QueryService<OrdOrderPrice> {

    private final Logger log = LoggerFactory.getLogger(OrdOrderPriceQueryService.class);

    private final OrdOrderPriceRepository ordOrderPriceRepository;

    private final OrdOrderPriceMapper ordOrderPriceMapper;

    public OrdOrderPriceQueryService(OrdOrderPriceRepository ordOrderPriceRepository, OrdOrderPriceMapper ordOrderPriceMapper) {
        this.ordOrderPriceRepository = ordOrderPriceRepository;
        this.ordOrderPriceMapper = ordOrderPriceMapper;
    }

    /**
     * Return a {@link List} of {@link OrdOrderPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderPriceDTO> findByCriteria(OrdOrderPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdOrderPrice> specification = createSpecification(criteria);
        return ordOrderPriceMapper.toDto(ordOrderPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdOrderPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdOrderPriceDTO> findByCriteria(OrdOrderPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdOrderPrice> specification = createSpecification(criteria);
        return ordOrderPriceRepository.findAll(specification, page).map(ordOrderPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdOrderPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdOrderPrice> specification = createSpecification(criteria);
        return ordOrderPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdOrderPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdOrderPrice> createSpecification(OrdOrderPriceCriteria criteria) {
        Specification<OrdOrderPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdOrderPrice_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdOrderPrice_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OrdOrderPrice_.description));
            }
            if (criteria.getPriceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriceType(), OrdOrderPrice_.priceType));
            }
            if (criteria.getUnitOfMeasure() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitOfMeasure(), OrdOrderPrice_.unitOfMeasure));
            }
            if (criteria.getRecurringChargePeriod() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRecurringChargePeriod(), OrdOrderPrice_.recurringChargePeriod));
            }
            if (criteria.getPriceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceId(), OrdOrderPrice_.priceId));
            }
            if (criteria.getOrdPriceAmountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPriceAmountId(),
                            root -> root.join(OrdOrderPrice_.ordPriceAmount, JoinType.LEFT).get(OrdPriceAmount_.id)
                        )
                    );
            }
            if (criteria.getOrdPriceAlterationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPriceAlterationId(),
                            root -> root.join(OrdOrderPrice_.ordPriceAlterations, JoinType.LEFT).get(OrdPriceAlteration_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdOrderPrice_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdOrderPrice_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
