package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductOrderDTO.class);
        OrdProductOrderDTO ordProductOrderDTO1 = new OrdProductOrderDTO();
        ordProductOrderDTO1.setId(1L);
        OrdProductOrderDTO ordProductOrderDTO2 = new OrdProductOrderDTO();
        assertThat(ordProductOrderDTO1).isNotEqualTo(ordProductOrderDTO2);
        ordProductOrderDTO2.setId(ordProductOrderDTO1.getId());
        assertThat(ordProductOrderDTO1).isEqualTo(ordProductOrderDTO2);
        ordProductOrderDTO2.setId(2L);
        assertThat(ordProductOrderDTO1).isNotEqualTo(ordProductOrderDTO2);
        ordProductOrderDTO1.setId(null);
        assertThat(ordProductOrderDTO1).isNotEqualTo(ordProductOrderDTO2);
    }
}
