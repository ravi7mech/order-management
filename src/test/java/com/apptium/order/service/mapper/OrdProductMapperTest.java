package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdProductMapperTest {

    private OrdProductMapper ordProductMapper;

    @BeforeEach
    public void setUp() {
        ordProductMapper = new OrdProductMapperImpl();
    }
}
