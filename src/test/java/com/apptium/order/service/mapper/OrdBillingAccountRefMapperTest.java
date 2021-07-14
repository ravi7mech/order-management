package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdBillingAccountRefMapperTest {

    private OrdBillingAccountRefMapper ordBillingAccountRefMapper;

    @BeforeEach
    public void setUp() {
        ordBillingAccountRefMapper = new OrdBillingAccountRefMapperImpl();
    }
}
