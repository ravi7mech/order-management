package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdAcquisitionMapperTest {

    private OrdAcquisitionMapper ordAcquisitionMapper;

    @BeforeEach
    public void setUp() {
        ordAcquisitionMapper = new OrdAcquisitionMapperImpl();
    }
}
