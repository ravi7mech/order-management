package com.apptium.order.repository;

import com.apptium.order.domain.OrdProductOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdProductOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdProductOrderRepository extends JpaRepository<OrdProductOrder, Long>, JpaSpecificationExecutor<OrdProductOrder> {}
