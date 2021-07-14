package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdContractCharacteristicsMapperTest {

    private OrdContractCharacteristicsMapper ordContractCharacteristicsMapper;

    @BeforeEach
    public void setUp() {
        ordContractCharacteristicsMapper = new OrdContractCharacteristicsMapperImpl();
    }
}
