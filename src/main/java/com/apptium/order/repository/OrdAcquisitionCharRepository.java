package com.apptium.order.repository;

import com.apptium.order.domain.OrdAcquisitionChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdAcquisitionChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdAcquisitionCharRepository
    extends JpaRepository<OrdAcquisitionChar, Long>, JpaSpecificationExecutor<OrdAcquisitionChar> {}
