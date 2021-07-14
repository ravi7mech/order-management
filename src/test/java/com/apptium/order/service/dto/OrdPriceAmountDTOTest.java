package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPriceAmountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPriceAmountDTO.class);
        OrdPriceAmountDTO ordPriceAmountDTO1 = new OrdPriceAmountDTO();
        ordPriceAmountDTO1.setId(1L);
        OrdPriceAmountDTO ordPriceAmountDTO2 = new OrdPriceAmountDTO();
        assertThat(ordPriceAmountDTO1).isNotEqualTo(ordPriceAmountDTO2);
        ordPriceAmountDTO2.setId(ordPriceAmountDTO1.getId());
        assertThat(ordPriceAmountDTO1).isEqualTo(ordPriceAmountDTO2);
        ordPriceAmountDTO2.setId(2L);
        assertThat(ordPriceAmountDTO1).isNotEqualTo(ordPriceAmountDTO2);
        ordPriceAmountDTO1.setId(null);
        assertThat(ordPriceAmountDTO1).isNotEqualTo(ordPriceAmountDTO2);
    }
}
