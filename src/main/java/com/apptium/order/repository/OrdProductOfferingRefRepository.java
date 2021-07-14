package com.apptium.order.repository;

import com.apptium.order.domain.OrdProductOfferingRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdProductOfferingRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdProductOfferingRefRepository
    extends JpaRepository<OrdProductOfferingRef, Long>, JpaSpecificationExecutor<OrdProductOfferingRef> {}
