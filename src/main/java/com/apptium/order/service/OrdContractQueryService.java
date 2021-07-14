package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdContract;
import com.apptium.order.repository.OrdContractRepository;
import com.apptium.order.service.criteria.OrdContractCriteria;
import com.apptium.order.service.dto.OrdContractDTO;
import com.apptium.order.service.mapper.OrdContractMapper;
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
 * Service for executing complex queries for {@link OrdContract} entities in the database.
 * The main input is a {@link OrdContractCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdContractDTO} or a {@link Page} of {@link OrdContractDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdContractQueryService extends QueryService<OrdContract> {

    private final Logger log = LoggerFactory.getLogger(OrdContractQueryService.class);

    private final OrdContractRepository ordContractRepository;

    private final OrdContractMapper ordContractMapper;

    public OrdContractQueryService(OrdContractRepository ordContractRepository, OrdContractMapper ordContractMapper) {
        this.ordContractRepository = ordContractRepository;
        this.ordContractMapper = ordContractMapper;
    }

    /**
     * Return a {@link List} of {@link OrdContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdContractDTO> findByCriteria(OrdContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdContract> specification = createSpecification(criteria);
        return ordContractMapper.toDto(ordContractRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdContractDTO> findByCriteria(OrdContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdContract> specification = createSpecification(criteria);
        return ordContractRepository.findAll(specification, page).map(ordContractMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdContractCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdContract> specification = createSpecification(criteria);
        return ordContractRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdContractCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdContract> createSpecification(OrdContractCriteria criteria) {
        Specification<OrdContract> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdContract_.id));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractId(), OrdContract_.contractId));
            }
            if (criteria.getLanguageId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLanguageId(), OrdContract_.languageId));
            }
            if (criteria.getTermTypeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermTypeCode(), OrdContract_.termTypeCode));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), OrdContract_.action));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrdContract_.status));
            }
            if (criteria.getOrdContractCharacteristicsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdContractCharacteristicsId(),
                            root -> root.join(OrdContract_.ordContractCharacteristics, JoinType.LEFT).get(OrdContractCharacteristics_.id)
                        )
                    );
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdContract_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
