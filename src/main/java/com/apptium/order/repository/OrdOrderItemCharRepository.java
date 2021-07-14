package com.apptium.order.repository;

import com.apptium.order.domain.OrdOrderItemChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdOrderItemChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdOrderItemCharRepository extends JpaRepository<OrdOrderItemChar, Long>, JpaSpecificationExecutor<OrdOrderItemChar> {}
