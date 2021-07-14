package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdPaymentRefMapperTest {

    private OrdPaymentRefMapper ordPaymentRefMapper;

    @BeforeEach
    public void setUp() {
        ordPaymentRefMapper = new OrdPaymentRefMapperImpl();
    }
}
