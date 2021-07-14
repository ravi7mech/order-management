package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdProductOrder;
import com.apptium.order.repository.OrdProductOrderRepository;
import com.apptium.order.service.criteria.OrdProductOrderCriteria;
import com.apptium.order.service.dto.OrdProductOrderDTO;
import com.apptium.order.service.mapper.OrdProductOrderMapper;
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
 * Service for executing complex queries for {@link OrdProductOrder} entities in the database.
 * The main input is a {@link OrdProductOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdProductOrderDTO} or a {@link Page} of {@link OrdProductOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdProductOrderQueryService extends QueryService<OrdProductOrder> {

    private final Logger log = LoggerFactory.getLogger(OrdProductOrderQueryService.class);

    private final OrdProductOrderRepository ordProductOrderRepository;

    private final OrdProductOrderMapper ordProductOrderMapper;

    public OrdProductOrderQueryService(OrdProductOrderRepository ordProductOrderRepository, OrdProductOrderMapper ordProductOrderMapper) {
        this.ordProductOrderRepository = ordProductOrderRepository;
        this.ordProductOrderMapper = ordProductOrderMapper;
    }

    /**
     * Return a {@link List} of {@link OrdProductOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductOrderDTO> findByCriteria(OrdProductOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdProductOrder> specification = createSpecification(criteria);
        return ordProductOrderMapper.toDto(ordProductOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdProductOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdProductOrderDTO> findByCriteria(OrdProductOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdProductOrder> specification = createSpecification(criteria);
        return ordProductOrderRepository.findAll(specification, page).map(ordProductOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdProductOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdProductOrder> specification = createSpecification(criteria);
        return ordProductOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdProductOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdProductOrder> createSpecification(OrdProductOrderCriteria criteria) {
        Specification<OrdProductOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdProductOrder_.id));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdProductOrder_.href));
            }
            if (criteria.getExternalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExternalId(), OrdProductOrder_.externalId));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriority(), OrdProductOrder_.priority));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OrdProductOrder_.description));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), OrdProductOrder_.category));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdProductOrder_.status));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), OrdProductOrder_.orderDate));
            }
            if (criteria.getCompletionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletionDate(), OrdProductOrder_.completionDate));
            }
            if (criteria.getRequestedStartDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRequestedStartDate(), OrdProductOrder_.requestedStartDate));
            }
            if (criteria.getRequestedCompletionDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getRequestedCompletionDate(), OrdProductOrder_.requestedCompletionDate)
                    );
            }
            if (criteria.getExpectedCompletionDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getExpectedCompletionDate(), OrdProductOrder_.expectedCompletionDate)
                    );
            }
            if (criteria.getNotificationContact() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNotificationContact(), OrdProductOrder_.notificationContact));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), OrdProductOrder_.customerId));
            }
            if (criteria.getShoppingCartId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShoppingCartId(), OrdProductOrder_.shoppingCartId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), OrdProductOrder_.type));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationId(), OrdProductOrder_.locationId));
            }
            if (criteria.getOrdContactDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdContactDetailsId(),
                            root -> root.join(OrdProductOrder_.ordContactDetails, JoinType.LEFT).get(OrdContactDetails_.id)
                        )
                    );
            }
            if (criteria.getOrdNoteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdNoteId(),
                            root -> root.join(OrdProductOrder_.ordNote, JoinType.LEFT).get(OrdNote_.id)
                        )
                    );
            }
            if (criteria.getOrdChannelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdChannelId(),
                            root -> root.join(OrdProductOrder_.ordChannel, JoinType.LEFT).get(OrdChannel_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderPriceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderPriceId(),
                            root -> root.join(OrdProductOrder_.ordOrderPrice, JoinType.LEFT).get(OrdOrderPrice_.id)
                        )
                    );
            }
            if (criteria.getOrdBillingAccountRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdBillingAccountRefId(),
                            root -> root.join(OrdProductOrder_.ordBillingAccountRef, JoinType.LEFT).get(OrdBillingAccountRef_.id)
                        )
                    );
            }
            if (criteria.getOrdCharacteristicsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdCharacteristicsId(),
                            root -> root.join(OrdProductOrder_.ordCharacteristics, JoinType.LEFT).get(OrdCharacteristics_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdProductOrder_.ordOrderItems, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
            if (criteria.getOrdPaymentRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPaymentRefId(),
                            root -> root.join(OrdProductOrder_.ordPaymentRefs, JoinType.LEFT).get(OrdPaymentRef_.id)
                        )
                    );
            }
            if (criteria.getOrdReasonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdReasonId(),
                            root -> root.join(OrdProductOrder_.ordReasons, JoinType.LEFT).get(OrdReason_.id)
                        )
                    );
            }
            if (criteria.getOrdContractId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdContractId(),
                            root -> root.join(OrdProductOrder_.ordContracts, JoinType.LEFT).get(OrdContract_.id)
                        )
                    );
            }
            if (criteria.getOrdFulfillmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdFulfillmentId(),
                            root -> root.join(OrdProductOrder_.ordFulfillments, JoinType.LEFT).get(OrdFulfillment_.id)
                        )
                    );
            }
            if (criteria.getOrdAcquisitionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdAcquisitionId(),
                            root -> root.join(OrdProductOrder_.ordAcquisitions, JoinType.LEFT).get(OrdAcquisition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
