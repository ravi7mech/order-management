package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemDTO.class);
        OrdOrderItemDTO ordOrderItemDTO1 = new OrdOrderItemDTO();
        ordOrderItemDTO1.setId(1L);
        OrdOrderItemDTO ordOrderItemDTO2 = new OrdOrderItemDTO();
        assertThat(ordOrderItemDTO1).isNotEqualTo(ordOrderItemDTO2);
        ordOrderItemDTO2.setId(ordOrderItemDTO1.getId());
        assertThat(ordOrderItemDTO1).isEqualTo(ordOrderItemDTO2);
        ordOrderItemDTO2.setId(2L);
        assertThat(ordOrderItemDTO1).isNotEqualTo(ordOrderItemDTO2);
        ordOrderItemDTO1.setId(null);
        assertThat(ordOrderItemDTO1).isNotEqualTo(ordOrderItemDTO2);
    }
}
