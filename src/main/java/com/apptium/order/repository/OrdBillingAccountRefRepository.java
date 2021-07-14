package com.apptium.order.repository;

import com.apptium.order.domain.OrdBillingAccountRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdBillingAccountRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdBillingAccountRefRepository
    extends JpaRepository<OrdBillingAccountRef, Long>, JpaSpecificationExecutor<OrdBillingAccountRef> {}
