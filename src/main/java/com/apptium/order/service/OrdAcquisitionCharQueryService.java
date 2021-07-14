package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdAcquisitionChar;
import com.apptium.order.repository.OrdAcquisitionCharRepository;
import com.apptium.order.service.criteria.OrdAcquisitionCharCriteria;
import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
import com.apptium.order.service.mapper.OrdAcquisitionCharMapper;
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
 * Service for executing complex queries for {@link OrdAcquisitionChar} entities in the database.
 * The main input is a {@link OrdAcquisitionCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdAcquisitionCharDTO} or a {@link Page} of {@link OrdAcquisitionCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdAcquisitionCharQueryService extends QueryService<OrdAcquisitionChar> {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionCharQueryService.class);

    private final OrdAcquisitionCharRepository ordAcquisitionCharRepository;

    private final OrdAcquisitionCharMapper ordAcquisitionCharMapper;

    public OrdAcquisitionCharQueryService(
        OrdAcquisitionCharRepository ordAcquisitionCharRepository,
        OrdAcquisitionCharMapper ordAcquisitionCharMapper
    ) {
        this.ordAcquisitionCharRepository = ordAcquisitionCharRepository;
        this.ordAcquisitionCharMapper = ordAcquisitionCharMapper;
    }

    /**
     * Return a {@link List} of {@link OrdAcquisitionCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdAcquisitionCharDTO> findByCriteria(OrdAcquisitionCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdAcquisitionChar> specification = createSpecification(criteria);
        return ordAcquisitionCharMapper.toDto(ordAcquisitionCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdAcquisitionCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdAcquisitionCharDTO> findByCriteria(OrdAcquisitionCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdAcquisitionChar> specification = createSpecification(criteria);
        return ordAcquisitionCharRepository.findAll(specification, page).map(ordAcquisitionCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdAcquisitionCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdAcquisitionChar> specification = createSpecification(criteria);
        return ordAcquisitionCharRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdAcquisitionCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdAcquisitionChar> createSpecification(OrdAcquisitionCharCriteria criteria) {
        Specification<OrdAcquisitionChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdAcquisitionChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdAcquisitionChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OrdAcquisitionChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), OrdAcquisitionChar_.valueType));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrdAcquisitionChar_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdAcquisitionChar_.createdDate));
            }
            if (criteria.getOrdAcquisitionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdAcquisitionId(),
                            root -> root.join(OrdAcquisitionChar_.ordAcquisition, JoinType.LEFT).get(OrdAcquisition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
