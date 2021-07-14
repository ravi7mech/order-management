package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdProductOfferingRefMapperTest {

    private OrdProductOfferingRefMapper ordProductOfferingRefMapper;

    @BeforeEach
    public void setUp() {
        ordProductOfferingRefMapper = new OrdProductOfferingRefMapperImpl();
    }
}
