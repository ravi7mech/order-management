package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdContractMapperTest {

    private OrdContractMapper ordContractMapper;

    @BeforeEach
    public void setUp() {
        ordContractMapper = new OrdContractMapperImpl();
    }
}
