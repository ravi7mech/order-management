package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdOrderItemProvisioning;
import com.apptium.order.repository.OrdOrderItemProvisioningRepository;
import com.apptium.order.service.criteria.OrdOrderItemProvisioningCriteria;
import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
import com.apptium.order.service.mapper.OrdOrderItemProvisioningMapper;
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
 * Service for executing complex queries for {@link OrdOrderItemProvisioning} entities in the database.
 * The main input is a {@link OrdOrderItemProvisioningCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdOrderItemProvisioningDTO} or a {@link Page} of {@link OrdOrderItemProvisioningDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdOrderItemProvisioningQueryService extends QueryService<OrdOrderItemProvisioning> {

    private final Logger log = LoggerFactory.getLogger(OrdOrderItemProvisioningQueryService.class);

    private final OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository;

    private final OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper;

    public OrdOrderItemProvisioningQueryService(
        OrdOrderItemProvisioningRepository ordOrderItemProvisioningRepository,
        OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper
    ) {
        this.ordOrderItemProvisioningRepository = ordOrderItemProvisioningRepository;
        this.ordOrderItemProvisioningMapper = ordOrderItemProvisioningMapper;
    }

    /**
     * Return a {@link List} of {@link OrdOrderItemProvisioningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdOrderItemProvisioningDTO> findByCriteria(OrdOrderItemProvisioningCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdOrderItemProvisioning> specification = createSpecification(criteria);
        return ordOrderItemProvisioningMapper.toDto(ordOrderItemProvisioningRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdOrderItemProvisioningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdOrderItemProvisioningDTO> findByCriteria(OrdOrderItemProvisioningCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdOrderItemProvisioning> specification = createSpecification(criteria);
        return ordOrderItemProvisioningRepository.findAll(specification, page).map(ordOrderItemProvisioningMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdOrderItemProvisioningCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdOrderItemProvisioning> specification = createSpecification(criteria);
        return ordOrderItemProvisioningRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdOrderItemProvisioningCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdOrderItemProvisioning> createSpecification(OrdOrderItemProvisioningCriteria criteria) {
        Specification<OrdOrderItemProvisioning> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdOrderItemProvisioning_.id));
            }
            if (criteria.getProvisioningId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProvisioningId(), OrdOrderItemProvisioning_.provisioningId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdOrderItemProvisioning_.status));
            }
            if (criteria.getOrdProvisiongCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProvisiongCharId(),
                            root -> root.join(OrdOrderItemProvisioning_.ordProvisiongChars, JoinType.LEFT).get(OrdProvisiongChar_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdOrderItemProvisioning_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
