package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdOrderItemChar;
import com.apptium.order.repository.OrdOrderItemCharRepository;
import com.apptium.order.service.criteria.OrdOrderItemCharCriteria;
import com.apptium.order.service.dto.OrdOrderItemCharDTO;
import com.apptium.order.service.mapper.OrdOrderItemCharMapper;
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
 * Service for executing complex queries for {@link OrdOrderItemChar} entities in the database.
 * The main input is a {@link OrdOrderItemCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdOrderItemCharDTO} or a {@link Page} of {@link OrdOrderItemCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdOrderItemCharQueryService extends QueryService<OrdOrderItemChar> {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemCharQueryService.class);

    private final OrdOrderItemCharRepository ordOrderItemCharRepository;

    private final OrdOrderItemCharMapper ordOrderItemCharMapper;

    public OrdOrderItemCharQueryService(
        OrdOrderItemCharRepository ordOrderItemCharRepository,
        OrdOrderItemCharMapper ordOrderItemCharMapper
    ) {
        this.ordOrderItemCharRepository = ordOrderItemCharRepository;
        this.ordOrderItemCharMapper = ordOrderItemCharMapper;
    }

    /**
     * Return a {@link List} of {@link OrdOrderItemCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemCharDTO> findByCriteria(OrdOrderItemCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdOrderItemChar> specification = createSpecification(criteria);
        return ordOrderItemCharMapper.toDto(ordOrderItemCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdOrderItemCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdOrderItemCharDTO> findByCriteria(OrdOrderItemCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdOrderItemChar> specification = createSpecification(criteria);
        return ordOrderItemCharRepository.findAll(specification, page).map(ordOrderItemCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdOrderItemCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdOrderItemChar> specification = createSpecification(criteria);
        return ordOrderItemCharRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdOrderItemCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdOrderItemChar> createSpecification(OrdOrderItemCharCriteria criteria) {
        Specification<OrdOrderItemChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdOrderItemChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdOrderItemChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdOrderItemChar_.value));
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdOrderItemChar_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
