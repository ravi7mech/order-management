package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdContactDetails;
import com.apptium.order.repository.OrdContactDetailsRepository;
import com.apptium.order.service.criteria.OrdContactDetailsCriteria;
import com.apptium.order.service.dto.OrdContactDetailsDTO;
import com.apptium.order.service.mapper.OrdContactDetailsMapper;
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
 * Service for executing complex queries for {@link OrdContactDetails} entities in the database.
 * The main input is a {@link OrdContactDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdContactDetailsDTO} or a {@link Page} of {@link OrdContactDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdContactDetailsQueryService extends QueryService<OrdContactDetails> {

    private final Logger log = LoggerFactory.getLogger(OrdContactDetailsQueryService.class);

    private final OrdContactDetailsRepository ordContactDetailsRepository;

    private final OrdContactDetailsMapper ordContactDetailsMapper;

    public OrdContactDetailsQueryService(
        OrdContactDetailsRepository ordContactDetailsRepository,
        OrdContactDetailsMapper ordContactDetailsMapper
    ) {
        this.ordContactDetailsRepository = ordContactDetailsRepository;
        this.ordContactDetailsMapper = ordContactDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link OrdContactDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdContactDetailsDTO> findByCriteria(OrdContactDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdContactDetails> specification = createSpecification(criteria);
        return ordContactDetailsMapper.toDto(ordContactDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdContactDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdContactDetailsDTO> findByCriteria(OrdContactDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdContactDetails> specification = createSpecification(criteria);
        return ordContactDetailsRepository.findAll(specification, page).map(ordContactDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdContactDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdContactDetails> specification = createSpecification(criteria);
        return ordContactDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdContactDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdContactDetails> createSpecification(OrdContactDetailsCriteria criteria) {
        Specification<OrdContactDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdContactDetails_.id));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), OrdContactDetails_.contactName));
            }
            if (criteria.getContactPhoneNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactPhoneNumber(), OrdContactDetails_.contactPhoneNumber));
            }
            if (criteria.getContactEmailId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactEmailId(), OrdContactDetails_.contactEmailId));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), OrdContactDetails_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), OrdContactDetails_.lastName));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdContactDetails_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
