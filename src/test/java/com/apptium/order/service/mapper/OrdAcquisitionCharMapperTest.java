package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdAcquisitionCharMapperTest {

    private OrdAcquisitionCharMapper ordAcquisitionCharMapper;

    @BeforeEach
    public void setUp() {
        ordAcquisitionCharMapper = new OrdAcquisitionCharMapperImpl();
    }
}
