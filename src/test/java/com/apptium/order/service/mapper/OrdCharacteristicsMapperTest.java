package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdCharacteristicsMapperTest {

    private OrdCharacteristicsMapper ordCharacteristicsMapper;

    @BeforeEach
    public void setUp() {
        ordCharacteristicsMapper = new OrdCharacteristicsMapperImpl();
    }
}
