package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdOrderItem;
import com.apptium.order.repository.OrdOrderItemRepository;
import com.apptium.order.service.criteria.OrdOrderItemCriteria;
import com.apptium.order.service.dto.OrdOrderItemDTO;
import com.apptium.order.service.mapper.OrdOrderItemMapper;
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
 * Service for executing complex queries for {@link OrdOrderItem} entities in the database.
 * The main input is a {@link OrdOrderItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdOrderItemDTO} or a {@link Page} of {@link OrdOrderItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdOrderItemQueryService extends QueryService<OrdOrderItem> {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemQueryService.class);

    private final OrdOrderItemRepository ordOrderItemRepository;

    private final OrdOrderItemMapper ordOrderItemMapper;

    public OrdOrderItemQueryService(OrdOrderItemRepository ordOrderItemRepository, OrdOrderItemMapper ordOrderItemMapper) {
        this.ordOrderItemRepository = ordOrderItemRepository;
        this.ordOrderItemMapper = ordOrderItemMapper;
    }

    /**
     * Return a {@link List} of {@link OrdOrderItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemDTO> findByCriteria(OrdOrderItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdOrderItem> specification = createSpecification(criteria);
        return ordOrderItemMapper.toDto(ordOrderItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdOrderItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdOrderItemDTO> findByCriteria(OrdOrderItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdOrderItem> specification = createSpecification(criteria);
        return ordOrderItemRepository.findAll(specification, page).map(ordOrderItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdOrderItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdOrderItem> specification = createSpecification(criteria);
        return ordOrderItemRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdOrderItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdOrderItem> createSpecification(OrdOrderItemCriteria criteria) {
        Specification<OrdOrderItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdOrderItem_.id));
            }
            if (criteria.getBillerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillerId(), OrdOrderItem_.billerId));
            }
            if (criteria.getFullfillmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFullfillmentId(), OrdOrderItem_.fullfillmentId));
            }
            if (criteria.getAcquisitionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcquisitionId(), OrdOrderItem_.acquisitionId));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), OrdOrderItem_.action));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), OrdOrderItem_.state));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), OrdOrderItem_.quantity));
            }
            if (criteria.getItemType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemType(), OrdOrderItem_.itemType));
            }
            if (criteria.getItemDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDescription(), OrdOrderItem_.itemDescription));
            }
            if (criteria.getCartItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCartItemId(), OrdOrderItem_.cartItemId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrdOrderItem_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdOrderItem_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), OrdOrderItem_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), OrdOrderItem_.updatedDate));
            }
            if (criteria.getOrdOrderPriceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderPriceId(),
                            root -> root.join(OrdOrderItem_.ordOrderPrice, JoinType.LEFT).get(OrdOrderPrice_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemRelationshipId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemRelationshipId(),
                            root -> root.join(OrdOrderItem_.ordOrderItemRelationship, JoinType.LEFT).get(OrdOrderItemRelationship_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOfferingRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOfferingRefId(),
                            root -> root.join(OrdOrderItem_.ordProductOfferingRef, JoinType.LEFT).get(OrdProductOfferingRef_.id)
                        )
                    );
            }
            if (criteria.getOrdProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductId(),
                            root -> root.join(OrdOrderItem_.ordProduct, JoinType.LEFT).get(OrdProduct_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemProvisioningId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemProvisioningId(),
                            root -> root.join(OrdOrderItem_.ordOrderItemProvisioning, JoinType.LEFT).get(OrdOrderItemProvisioning_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemCharId(),
                            root -> root.join(OrdOrderItem_.ordOrderItemChars, JoinType.LEFT).get(OrdOrderItemChar_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdOrderItem_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
