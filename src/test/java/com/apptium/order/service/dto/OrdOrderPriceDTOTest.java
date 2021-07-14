package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderPriceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderPriceDTO.class);
        OrdOrderPriceDTO ordOrderPriceDTO1 = new OrdOrderPriceDTO();
        ordOrderPriceDTO1.setId(1L);
        OrdOrderPriceDTO ordOrderPriceDTO2 = new OrdOrderPriceDTO();
        assertThat(ordOrderPriceDTO1).isNotEqualTo(ordOrderPriceDTO2);
        ordOrderPriceDTO2.setId(ordOrderPriceDTO1.getId());
        assertThat(ordOrderPriceDTO1).isEqualTo(ordOrderPriceDTO2);
        ordOrderPriceDTO2.setId(2L);
        assertThat(ordOrderPriceDTO1).isNotEqualTo(ordOrderPriceDTO2);
        ordOrderPriceDTO1.setId(null);
        assertThat(ordOrderPriceDTO1).isNotEqualTo(ordOrderPriceDTO2);
    }
}
