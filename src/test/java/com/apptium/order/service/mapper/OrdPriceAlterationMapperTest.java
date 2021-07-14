package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdPriceAlterationMapperTest {

    private OrdPriceAlterationMapper ordPriceAlterationMapper;

    @BeforeEach
    public void setUp() {
        ordPriceAlterationMapper = new OrdPriceAlterationMapperImpl();
    }
}
