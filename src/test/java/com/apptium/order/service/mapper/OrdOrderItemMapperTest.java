package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdOrderItemMapperTest {

    private OrdOrderItemMapper ordOrderItemMapper;

    @BeforeEach
    public void setUp() {
        ordOrderItemMapper = new OrdOrderItemMapperImpl();
    }
}
