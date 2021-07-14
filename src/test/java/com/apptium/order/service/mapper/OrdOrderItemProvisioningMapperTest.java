package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdOrderItemProvisioningMapperTest {

    private OrdOrderItemProvisioningMapper ordOrderItemProvisioningMapper;

    @BeforeEach
    public void setUp() {
        ordOrderItemProvisioningMapper = new OrdOrderItemProvisioningMapperImpl();
    }
}
