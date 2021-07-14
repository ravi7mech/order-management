package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductDTO.class);
        OrdProductDTO ordProductDTO1 = new OrdProductDTO();
        ordProductDTO1.setId(1L);
        OrdProductDTO ordProductDTO2 = new OrdProductDTO();
        assertThat(ordProductDTO1).isNotEqualTo(ordProductDTO2);
        ordProductDTO2.setId(ordProductDTO1.getId());
        assertThat(ordProductDTO1).isEqualTo(ordProductDTO2);
        ordProductDTO2.setId(2L);
        assertThat(ordProductDTO1).isNotEqualTo(ordProductDTO2);
        ordProductDTO1.setId(null);
        assertThat(ordProductDTO1).isNotEqualTo(ordProductDTO2);
    }
}
