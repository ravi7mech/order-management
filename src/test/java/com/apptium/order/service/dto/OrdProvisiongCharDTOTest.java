package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProvisiongCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProvisiongCharDTO.class);
        OrdProvisiongCharDTO ordProvisiongCharDTO1 = new OrdProvisiongCharDTO();
        ordProvisiongCharDTO1.setId(1L);
        OrdProvisiongCharDTO ordProvisiongCharDTO2 = new OrdProvisiongCharDTO();
        assertThat(ordProvisiongCharDTO1).isNotEqualTo(ordProvisiongCharDTO2);
        ordProvisiongCharDTO2.setId(ordProvisiongCharDTO1.getId());
        assertThat(ordProvisiongCharDTO1).isEqualTo(ordProvisiongCharDTO2);
        ordProvisiongCharDTO2.setId(2L);
        assertThat(ordProvisiongCharDTO1).isNotEqualTo(ordProvisiongCharDTO2);
        ordProvisiongCharDTO1.setId(null);
        assertThat(ordProvisiongCharDTO1).isNotEqualTo(ordProvisiongCharDTO2);
    }
}
