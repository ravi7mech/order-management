package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdProductOfferingRef;
import com.apptium.order.repository.OrdProductOfferingRefRepository;
import com.apptium.order.service.criteria.OrdProductOfferingRefCriteria;
import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
import com.apptium.order.service.mapper.OrdProductOfferingRefMapper;
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
 * Service for executing complex queries for {@link OrdProductOfferingRef} entities in the database.
 * The main input is a {@link OrdProductOfferingRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdProductOfferingRefDTO} or a {@link Page} of {@link OrdProductOfferingRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdProductOfferingRefQueryService extends QueryService<OrdProductOfferingRef> {

    private final Logger log = LoggerFactory.getLogger(OrdProductOfferingRefQueryService.class);

    private final OrdProductOfferingRefRepository ordProductOfferingRefRepository;

    private final OrdProductOfferingRefMapper ordProductOfferingRefMapper;

    public OrdProductOfferingRefQueryService(
        OrdProductOfferingRefRepository ordProductOfferingRefRepository,
        OrdProductOfferingRefMapper ordProductOfferingRefMapper
    ) {
        this.ordProductOfferingRefRepository = ordProductOfferingRefRepository;
        this.ordProductOfferingRefMapper = ordProductOfferingRefMapper;
    }

    /**
     * Return a {@link List} of {@link OrdProductOfferingRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductOfferingRefDTO> findByCriteria(OrdProductOfferingRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdProductOfferingRef> specification = createSpecification(criteria);
        return ordProductOfferingRefMapper.toDto(ordProductOfferingRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdProductOfferingRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdProductOfferingRefDTO> findByCriteria(OrdProductOfferingRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdProductOfferingRef> specification = createSpecification(criteria);
        return ordProductOfferingRefRepository.findAll(specification, page).map(ordProductOfferingRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdProductOfferingRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdProductOfferingRef> specification = createSpecification(criteria);
        return ordProductOfferingRefRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdProductOfferingRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdProductOfferingRef> createSpecification(OrdProductOfferingRefCriteria criteria) {
        Specification<OrdProductOfferingRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdProductOfferingRef_.id));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdProductOfferingRef_.href));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdProductOfferingRef_.name));
            }
            if (criteria.getProductGuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductGuid(), OrdProductOfferingRef_.productGuid));
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdProductOfferingRef_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
