package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdOrderItemRelationship;
import com.apptium.order.repository.OrdOrderItemRelationshipRepository;
import com.apptium.order.service.criteria.OrdOrderItemRelationshipCriteria;
import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import com.apptium.order.service.mapper.OrdOrderItemRelationshipMapper;
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
 * Service for executing complex queries for {@link OrdOrderItemRelationship} entities in the database.
 * The main input is a {@link OrdOrderItemRelationshipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdOrderItemRelationshipDTO} or a {@link Page} of {@link OrdOrderItemRelationshipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdOrderItemRelationshipQueryService extends QueryService<OrdOrderItemRelationship> {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemRelationshipQueryService.class);

    private final OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository;

    private final OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper;

    public OrdOrderItemRelationshipQueryService(
        OrdOrderItemRelationshipRepository ordOrderItemRelationshipRepository,
        OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper
    ) {
        this.ordOrderItemRelationshipRepository = ordOrderItemRelationshipRepository;
        this.ordOrderItemRelationshipMapper = ordOrderItemRelationshipMapper;
    }

    /**
     * Return a {@link List} of {@link OrdOrderItemRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemRelationshipDTO> findByCriteria(OrdOrderItemRelationshipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdOrderItemRelationship> specification = createSpecification(criteria);
        return ordOrderItemRelationshipMapper.toDto(ordOrderItemRelationshipRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdOrderItemRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdOrderItemRelationshipDTO> findByCriteria(OrdOrderItemRelationshipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdOrderItemRelationship> specification = createSpecification(criteria);
        return ordOrderItemRelationshipRepository.findAll(specification, page).map(ordOrderItemRelationshipMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdOrderItemRelationshipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdOrderItemRelationship> specification = createSpecification(criteria);
        return ordOrderItemRelationshipRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdOrderItemRelationshipCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdOrderItemRelationship> createSpecification(OrdOrderItemRelationshipCriteria criteria) {
        Specification<OrdOrderItemRelationship> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdOrderItemRelationship_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), OrdOrderItemRelationship_.type));
            }
            if (criteria.getPrimaryOrderItemId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPrimaryOrderItemId(), OrdOrderItemRelationship_.primaryOrderItemId)
                    );
            }
            if (criteria.getSecondaryOrderItemId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getSecondaryOrderItemId(), OrdOrderItemRelationship_.secondaryOrderItemId)
                    );
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdOrderItemRelationship_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
