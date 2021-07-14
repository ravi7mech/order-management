package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdOrderItemCharMapperTest {

    private OrdOrderItemCharMapper ordOrderItemCharMapper;

    @BeforeEach
    public void setUp() {
        ordOrderItemCharMapper = new OrdOrderItemCharMapperImpl();
    }
}
