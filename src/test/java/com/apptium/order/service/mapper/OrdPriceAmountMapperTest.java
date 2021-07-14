package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdPriceAmountMapperTest {

    private OrdPriceAmountMapper ordPriceAmountMapper;

    @BeforeEach
    public void setUp() {
        ordPriceAmountMapper = new OrdPriceAmountMapperImpl();
    }
}
