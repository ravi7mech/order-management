package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdAcquisition;
import com.apptium.order.repository.OrdAcquisitionRepository;
import com.apptium.order.service.criteria.OrdAcquisitionCriteria;
import com.apptium.order.service.dto.OrdAcquisitionDTO;
import com.apptium.order.service.mapper.OrdAcquisitionMapper;
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
 * Service for executing complex queries for {@link OrdAcquisition} entities in the database.
 * The main input is a {@link OrdAcquisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdAcquisitionDTO} or a {@link Page} of {@link OrdAcquisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdAcquisitionQueryService extends QueryService<OrdAcquisition> {

    private final Logger log = LoggerFactory.getLogger(OrdAcquisitionQueryService.class);

    private final OrdAcquisitionRepository ordAcquisitionRepository;

    private final OrdAcquisitionMapper ordAcquisitionMapper;

    public OrdAcquisitionQueryService(OrdAcquisitionRepository ordAcquisitionRepository, OrdAcquisitionMapper ordAcquisitionMapper) {
        this.ordAcquisitionRepository = ordAcquisitionRepository;
        this.ordAcquisitionMapper = ordAcquisitionMapper;
    }

    /**
     * Return a {@link List} of {@link OrdAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdAcquisitionDTO> findByCriteria(OrdAcquisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdAcquisition> specification = createSpecification(criteria);
        return ordAcquisitionMapper.toDto(ordAcquisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdAcquisitionDTO> findByCriteria(OrdAcquisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdAcquisition> specification = createSpecification(criteria);
        return ordAcquisitionRepository.findAll(specification, page).map(ordAcquisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdAcquisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdAcquisition> specification = createSpecification(criteria);
        return ordAcquisitionRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdAcquisitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdAcquisition> createSpecification(OrdAcquisitionCriteria criteria) {
        Specification<OrdAcquisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdAcquisition_.id));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChannel(), OrdAcquisition_.channel));
            }
            if (criteria.getAffiliate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAffiliate(), OrdAcquisition_.affiliate));
            }
            if (criteria.getPartner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartner(), OrdAcquisition_.partner));
            }
            if (criteria.getAcquisitionAgent() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAcquisitionAgent(), OrdAcquisition_.acquisitionAgent));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), OrdAcquisition_.action));
            }
            if (criteria.getOrdAcquisitionCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdAcquisitionCharId(),
                            root -> root.join(OrdAcquisition_.ordAcquisitionChars, JoinType.LEFT).get(OrdAcquisitionChar_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdAcquisition_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
