package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdChannelMapperTest {

    private OrdChannelMapper ordChannelMapper;

    @BeforeEach
    public void setUp() {
        ordChannelMapper = new OrdChannelMapperImpl();
    }
}
