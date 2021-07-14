package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdFulfillmentCharMapperTest {

    private OrdFulfillmentCharMapper ordFulfillmentCharMapper;

    @BeforeEach
    public void setUp() {
        ordFulfillmentCharMapper = new OrdFulfillmentCharMapperImpl();
    }
}
