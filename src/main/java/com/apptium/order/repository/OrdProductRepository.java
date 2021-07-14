package com.apptium.order.repository;

import com.apptium.order.domain.OrdProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdProductRepository extends JpaRepository<OrdProduct, Long>, JpaSpecificationExecutor<OrdProduct> {}
