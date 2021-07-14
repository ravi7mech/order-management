package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdOrderItemRelationshipMapperTest {

    private OrdOrderItemRelationshipMapper ordOrderItemRelationshipMapper;

    @BeforeEach
    public void setUp() {
        ordOrderItemRelationshipMapper = new OrdOrderItemRelationshipMapperImpl();
    }
}
