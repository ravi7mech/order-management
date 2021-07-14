package com.apptium.order.repository;

import com.apptium.order.domain.OrdOrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdOrderItemRepository extends JpaRepository<OrdOrderItem, Long>, JpaSpecificationExecutor<OrdOrderItem> {}
