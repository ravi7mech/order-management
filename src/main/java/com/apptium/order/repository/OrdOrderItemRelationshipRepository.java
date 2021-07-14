package com.apptium.order.repository;

import com.apptium.order.domain.OrdOrderItemRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdOrderItemRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdOrderItemRelationshipRepository
    extends JpaRepository<OrdOrderItemRelationship, Long>, JpaSpecificationExecutor<OrdOrderItemRelationship> {}
