package com.apptium.order.repository;

import com.apptium.order.domain.OrdProductCharacteristics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdProductCharacteristics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdProductCharacteristicsRepository
    extends JpaRepository<OrdProductCharacteristics, Long>, JpaSpecificationExecutor<OrdProductCharacteristics> {}
