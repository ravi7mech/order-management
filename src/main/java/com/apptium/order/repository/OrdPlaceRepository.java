package com.apptium.order.repository;

import com.apptium.order.domain.OrdPlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdPlaceRepository extends JpaRepository<OrdPlace, Long>, JpaSpecificationExecutor<OrdPlace> {}
