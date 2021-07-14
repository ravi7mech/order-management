package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdPlaceMapperTest {

    private OrdPlaceMapper ordPlaceMapper;

    @BeforeEach
    public void setUp() {
        ordPlaceMapper = new OrdPlaceMapperImpl();
    }
}
