package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdNote;
import com.apptium.order.repository.OrdNoteRepository;
import com.apptium.order.service.criteria.OrdNoteCriteria;
import com.apptium.order.service.dto.OrdNoteDTO;
import com.apptium.order.service.mapper.OrdNoteMapper;
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
 * Service for executing complex queries for {@link OrdNote} entities in the database.
 * The main input is a {@link OrdNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdNoteDTO} or a {@link Page} of {@link OrdNoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdNoteQueryService extends QueryService<OrdNote> {

    private final Logger log = LoggerFactory.getLogger(OrdNoteQueryService.class);

    private final OrdNoteRepository ordNoteRepository;

    private final OrdNoteMapper ordNoteMapper;

    public OrdNoteQueryService(OrdNoteRepository ordNoteRepository, OrdNoteMapper ordNoteMapper) {
        this.ordNoteRepository = ordNoteRepository;
        this.ordNoteMapper = ordNoteMapper;
    }

    /**
     * Return a {@link List} of {@link OrdNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdNoteDTO> findByCriteria(OrdNoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdNote> specification = createSpecification(criteria);
        return ordNoteMapper.toDto(ordNoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdNoteDTO> findByCriteria(OrdNoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdNote> specification = createSpecification(criteria);
        return ordNoteRepository.findAll(specification, page).map(ordNoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdNoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdNote> specification = createSpecification(criteria);
        return ordNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdNote> createSpecification(OrdNoteCriteria criteria) {
        Specification<OrdNote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdNote_.id));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), OrdNote_.author));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), OrdNote_.text));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OrdNote_.createdDate));
            }
            if (criteria.getOrdProductOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductOrderId(),
                            root -> root.join(OrdNote_.ordProductOrder, JoinType.LEFT).get(OrdProductOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
