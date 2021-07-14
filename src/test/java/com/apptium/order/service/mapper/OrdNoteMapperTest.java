package com.apptium.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdNoteMapperTest {

    private OrdNoteMapper ordNoteMapper;

    @BeforeEach
    public void setUp() {
        ordNoteMapper = new OrdNoteMapperImpl();
    }
}
