package com.apptium.order.repository;

import com.apptium.order.domain.OrdFulfillment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdFulfillment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdFulfillmentRepository extends JpaRepository<OrdFulfillment, Long>, JpaSpecificationExecutor<OrdFulfillment> {}
