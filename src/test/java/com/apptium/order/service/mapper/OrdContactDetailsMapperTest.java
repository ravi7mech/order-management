package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdContactDetailsMapperTest {

    private OrdContactDetailsMapper ordContactDetailsMapper;

    @BeforeEach
    public void setUp() {
        ordContactDetailsMapper = new OrdContactDetailsMapperImpl();
    }
}
