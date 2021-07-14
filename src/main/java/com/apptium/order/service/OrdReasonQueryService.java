package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdReason;
import com.apptium.order.repository.OrdReasonRepository;
import com.apptium.order.service.criteria.OrdReasonCriteria;
import com.apptium.order.service.dto.OrdReasonDTO;
import com.apptium.order.service.mapper.OrdReasonMapper;
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
 * Service for executing complex queries for {@link OrdReason} entities in the database.
 * The main input is a {@link OrdReasonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdReasonDTO} or a {@link Page} of {@link OrdReasonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdReasonQueryService extends QueryService<OrdReason> {

    private final Logger log = LoggerFactory.getLogger(OrdReasonQueryService.class);

    private final OrdReasonRepository ordReasonRepository;

    private final OrdReasonMapper ordReasonMapper;

    public OrdReasonQueryService(OrdReasonRepository ordReasonRepository, OrdReasonMapper ordReasonMapper) {
        this.ordReasonRepository = ordReasonRepository;
        this.ordReasonMapper = ordReasonMapper;
    }

    /**
     * Return a {@link List} of {@link OrdReasonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdReasonDTO> findByCriteria(OrdReasonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdReason> specification = createSpecification(criteria);
        return ordReasonMapper.toDto(ordReasonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdReasonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdReasonDTO> findByCriteria(OrdReasonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdReason> specification = createSpecification(criteria);
        return ordReasonRepository.findAll(specification, page).map(ordReasonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdReasonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdReason> specification = createSpecification(criteria);
        return ordReasonRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdReasonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdReason> createSpecification(OrdReasonCriteria criteria) {
        Specification<OrdReason> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdReason_.id));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), OrdReason_.reason));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OrdReason_.description));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdReason_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
