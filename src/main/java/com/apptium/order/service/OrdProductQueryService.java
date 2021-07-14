package com.apptium.order.service;

import com.apptium.order.domain.*; // for static metamodels
import com.apptium.order.domain.OrdProduct;
import com.apptium.order.repository.OrdProductRepository;
import com.apptium.order.service.criteria.OrdProductCriteria;
import com.apptium.order.service.dto.OrdProductDTO;
import com.apptium.order.service.mapper.OrdProductMapper;
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
 * Service for executing complex queries for {@link OrdProduct} entities in the database.
 * The main input is a {@link OrdProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdProductDTO} or a {@link Page} of {@link OrdProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdProductQueryService extends QueryService<OrdProduct> {

    private final Logger log = LoggerFactory.getLogger(OrdProductQueryService.class);

    private final OrdProductRepository ordProductRepository;

    private final OrdProductMapper ordProductMapper;

    public OrdProductQueryService(OrdProductRepository ordProductRepository, OrdProductMapper ordProductMapper) {
        this.ordProductRepository = ordProductRepository;
        this.ordProductMapper = ordProductMapper;
    }

    /**
     * Return a {@link List} of {@link OrdProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdProductDTO> findByCriteria(OrdProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrdProduct> specification = createSpecification(criteria);
        return ordProductMapper.toDto(ordProductRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdProductDTO> findByCriteria(OrdProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrdProduct> specification = createSpecification(criteria);
        return ordProductRepository.findAll(specification, page).map(ordProductMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrdProduct> specification = createSpecification(criteria);
        return ordProductRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrdProduct> createSpecification(OrdProductCriteria criteria) {
        Specification<OrdProduct> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrdProduct_.id));
            }
            if (criteria.getVersionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersionId(), OrdProduct_.versionId));
            }
            if (criteria.getVariationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVariationId(), OrdProduct_.variationId));
            }
            if (criteria.getLineOfService() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLineOfService(), OrdProduct_.lineOfService));
            }
            if (criteria.getAssetId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetId(), OrdProduct_.assetId));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), OrdProduct_.serialNo));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrdProduct_.name));
            }
            if (criteria.getOrdProductCharacteristicsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdProductCharacteristicsId(),
                            root -> root.join(OrdProduct_.ordProductCharacteristics, JoinType.LEFT).get(OrdProductCharacteristics_.id)
                        )
                    );
            }
            if (criteria.getOrdPlaceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdPlaceId(),
                            root -> root.join(OrdProduct_.ordPlaces, JoinType.LEFT).get(OrdPlace_.id)
                        )
                    );
            }
            if (criteria.getOrdOrderItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrdOrderItemId(),
                            root -> root.join(OrdProduct_.ordOrderItem, JoinType.LEFT).get(OrdOrderItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
