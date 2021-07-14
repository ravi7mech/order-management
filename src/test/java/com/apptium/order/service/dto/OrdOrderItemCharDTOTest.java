package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemCharDTO.class);
        OrdOrderItemCharDTO ordOrderItemCharDTO1 = new OrdOrderItemCharDTO();
        ordOrderItemCharDTO1.setId(1L);
        OrdOrderItemCharDTO ordOrderItemCharDTO2 = new OrdOrderItemCharDTO();
        assertThat(ordOrderItemCharDTO1).isNotEqualTo(ordOrderItemCharDTO2);
        ordOrderItemCharDTO2.setId(ordOrderItemCharDTO1.getId());
        assertThat(ordOrderItemCharDTO1).isEqualTo(ordOrderItemCharDTO2);
        ordOrderItemCharDTO2.setId(2L);
        assertThat(ordOrderItemCharDTO1).isNotEqualTo(ordOrderItemCharDTO2);
        ordOrderItemCharDTO1.setId(null);
        assertThat(ordOrderItemCharDTO1).isNotEqualTo(ordOrderItemCharDTO2);
    }
}
