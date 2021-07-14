package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdChannel;
import com.apptium.order.repository.OrdChannelRepository;
import com.apptium.order.service.criteria.OrdChannelCriteria;
import com.apptium.order.service.dto.OrdChannelDTO;
import com.apptium.order.service.mapper.OrdChannelMapper;
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
 * Service for executing complex queries for {@link OrdChannel} entities in the database.
 * The main input is a {@link OrdChannelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdChannelDTO} or a {@link Page} of {@link OrdChannelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdChannelQueryService extends QueryService<OrdChannel> {

    private final Logger log = LoggerFactory.getLogger(OrdChannelQueryService.class);

    private final OrdChannelRepository ordChannelRepository;

    private final OrdChannelMapper ordChannelMapper;

    public OrdChannelQueryService(OrdChannelRepository ordChannelRepository, OrdChannelMapper ordChannelMapper) {
        this.ordChannelRepository = ordChannelRepository;
        this.ordChannelMapper = ordChannelMapper;
    }

    /**
     * Return a {@link List} of {@link OrdChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdChannelDTO> findByCriteria(OrdChannelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdChannel> specification = createSpecification(criteria);
        return ordChannelMapper.toDto(ordChannelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdChannelDTO> findByCriteria(OrdChannelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdChannel> specification = createSpecification(criteria);
        return ordChannelRepository.findAll(specification, page).map(ordChannelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdChannelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdChannel> specification = createSpecification(criteria);
        return ordChannelRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdChannelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdChannel> createSpecification(OrdChannelCriteria criteria) {
        Specification<OrdChannel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdChannel_.id));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), OrdChannel_.href));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdChannel_.name));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), OrdChannel_.role));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdChannel_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
