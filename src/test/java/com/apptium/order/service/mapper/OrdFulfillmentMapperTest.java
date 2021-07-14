package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdFulfillmentMapperTest {

    private OrdFulfillmentMapper ordFulfillmentMapper;

    @BeforeEach
    public void setUp() {
        ordFulfillmentMapper = new OrdFulfillmentMapperImpl();
    }
}
