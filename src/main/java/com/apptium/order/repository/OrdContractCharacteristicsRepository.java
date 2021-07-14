package com.apptium.order.repository;

import com.apptium.order.domain.OrdContractCharacteristics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdContractCharacteristics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdContractCharacteristicsRepository
    extends JpaRepository<OrdContractCharacteristics, Long>, JpaSpecificationExecutor<OrdContractCharacteristics> {}
