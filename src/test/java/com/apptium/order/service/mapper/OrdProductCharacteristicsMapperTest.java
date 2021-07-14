package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdProductCharacteristicsMapperTest {

    private OrdProductCharacteristicsMapper ordProductCharacteristicsMapper;

    @BeforeEach
    public void setUp() {
        ordProductCharacteristicsMapper = new OrdProductCharacteristicsMapperImpl();
    }
}
