package com.apptium.order.repository;

import com.apptium.order.domain.OrdOrderItemProvisioning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdOrderItemProvisioning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdOrderItemProvisioningRepository
    extends JpaRepository<OrdOrderItemProvisioning, Long>, JpaSpecificationExecutor<OrdOrderItemProvisioning> {}
