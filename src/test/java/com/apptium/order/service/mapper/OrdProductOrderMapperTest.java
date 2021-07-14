package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdProductOrderMapperTest {

    private OrdProductOrderMapper ordProductOrderMapper;

    @BeforeEach
    public void setUp() {
        ordProductOrderMapper = new OrdProductOrderMapperImpl();
    }
}
