package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdOrderPriceMapperTest {

    private OrdOrderPriceMapper ordOrderPriceMapper;

    @BeforeEach
    public void setUp() {
        ordOrderPriceMapper = new OrdOrderPriceMapperImpl();
    }
}
