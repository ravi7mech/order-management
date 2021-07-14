package com.apptium.order.repository;

import com.apptium.order.domain.OrdChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrdChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdChannelRepository extends JpaRepository<OrdChannel, Long>, JpaSpecificationExecutor<OrdChannel> {}
