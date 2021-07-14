package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdReasonMapperTest {

    private OrdReasonMapper ordReasonMapper;

    @BeforeEach
    public void setUp() {
        ordReasonMapper = new OrdReasonMapperImpl();
    }
}
