package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdFulfillment;
import com.apptium.order.repository.OrdFulfillmentRepository;
import com.apptium.order.service.criteria.OrdFulfillmentCriteria;
import com.apptium.order.service.dto.OrdFulfillmentDTO;
import com.apptium.order.service.mapper.OrdFulfillmentMapper;
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
 * Service for executing complex queries for {@link OrdFulfillment} entities in the database.
 * The main input is a {@link OrdFulfillmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdFulfillmentDTO} or a {@link Page} of {@link OrdFulfillmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdFulfillmentQueryService extends QueryService<OrdFulfillment> {

    private final Logger log = LoggerFactory.getLogger(OrdFulfillmentQueryService.class);

    private final OrdFulfillmentRepository ordFulfillmentRepository;

    private final OrdFulfillmentMapper ordFulfillmentMapper;

    public OrdFulfillmentQueryService(OrdFulfillmentRepository ordFulfillmentRepository, OrdFulfillmentMapper ordFulfillmentMapper) {
        this.ordFulfillmentRepository = ordFulfillmentRepository;
        this.ordFulfillmentMapper = ordFulfillmentMapper;
    }

    /**
     * Return a {@link List} of {@link OrdFulfillmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdFulfillmentDTO> findByCriteria(OrdFulfillmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdFulfillment> specification = createSpecification(criteria);
        return ordFulfillmentMapper.toDto(ordFulfillmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdFulfillmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdFulfillmentDTO> findByCriteria(OrdFulfillmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdFulfillment> specification = createSpecification(criteria);
        return ordFulfillmentRepository.findAll(specification, page).map(ordFulfillmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdFulfillmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdFulfillment> specification = createSpecification(criteria);
        return ordFulfillmentRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdFulfillmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdFulfillment> createSpecification(OrdFulfillmentCriteria criteria) {
        Specification<OrdFulfillment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdFulfillment_.id));
            }
            if (criteria.getWorkorderId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkorderId(), OrdFulfillment_.workorderId));
            }
            if (criteria.getAppointmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppointmentId(), OrdFulfillment_.appointmentId));
            }
            if (criteria.getOrderFulfillmentType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOrderFulfillmentType(), OrdFulfillment_.orderFulfillmentType));
            }
            if (criteria.getAlternateShippingAddress() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAlternateShippingAddress(), OrdFulfillment_.alternateShippingAddress)
                    );
            }
            if (criteria.getOrderCallAheadNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOrderCallAheadNumber(), OrdFulfillment_.orderCallAheadNumber));
            }
            if (criteria.getOrderJobComments() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOrderJobComments(), OrdFulfillment_.orderJobComments));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdFulfillment_.status));
            }
            if (criteria.getOrdFulfillmentCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdFulfillmentCharId(),
                            root -> root.join(OrdFulfillment_.ordFulfillmentChars, JoinType.LEFT).get(OrdFulfillmentChar_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdFulfillment_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
