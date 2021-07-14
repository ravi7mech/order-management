package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdPlace;
import com.apptium.order.repository.OrdPlaceRepository;
import com.apptium.order.service.criteria.OrdPlaceCriteria;
import com.apptium.order.service.dto.OrdPlaceDTO;
import com.apptium.order.service.mapper.OrdPlaceMapper;
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
 * Service for executing complex queries for {@link OrdPlace} entities in the database.
 * The main input is a {@link OrdPlaceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdPlaceDTO} or a {@link Page} of {@link OrdPlaceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdPlaceQueryService extends QueryService<OrdPlace> {

    private final Logger log = LoggerFactory.getLogger(OrdPlaceQueryService.class);

    private final OrdPlaceRepository ordPlaceRepository;

    private final OrdPlaceMapper ordPlaceMapper;

    public OrdPlaceQueryService(OrdPlaceRepository ordPlaceRepository, OrdPlaceMapper ordPlaceMapper) {
        this.ordPlaceRepository = ordPlaceRepository;
        this.ordPlaceMapper = ordPlaceMapper;
    }

    /**
     * Return a {@link List} of {@link OrdPlaceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdPlaceDTO> findByCriteria(OrdPlaceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdPlace> specification = createSpecification(criteria);
        return ordPlaceMapper.toDto(ordPlaceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdPlaceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdPlaceDTO> findByCriteria(OrdPlaceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdPlace> specification = createSpecification(criteria);
        return ordPlaceRepository.findAll(specification, page).map(ordPlaceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdPlaceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdPlace> specification = createSpecification(criteria);
        return ordPlaceRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdPlaceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdPlace> createSpecification(OrdPlaceCriteria criteria) {
        Specification<OrdPlace> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdPlace_.id));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdPlace_.href));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdPlace_.name));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), OrdPlace_.role));
            }
            if (criteria.getOrdProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductId(),
                            root -> root.join(OrdPlace_.ordProduct, JoinType.LEFT).get(OrdProduct_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
