package com.apptium.order.repository;

import com.apptium.order.domain.OrdContactDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdContactDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdContactDetailsRepository extends JpaRepository<OrdContactDetails, Long>, JpaSpecificationExecutor<OrdContactDetails> {}
