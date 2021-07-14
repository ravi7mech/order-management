package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPriceAlterationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPriceAlterationDTO.class);
        OrdPriceAlterationDTO ordPriceAlterationDTO1 = new OrdPriceAlterationDTO();
        ordPriceAlterationDTO1.setId(1L);
        OrdPriceAlterationDTO ordPriceAlterationDTO2 = new OrdPriceAlterationDTO();
        assertThat(ordPriceAlterationDTO1).isNotEqualTo(ordPriceAlterationDTO2);
        ordPriceAlterationDTO2.setId(ordPriceAlterationDTO1.getId());
        assertThat(ordPriceAlterationDTO1).isEqualTo(ordPriceAlterationDTO2);
        ordPriceAlterationDTO2.setId(2L);
        assertThat(ordPriceAlterationDTO1).isNotEqualTo(ordPriceAlterationDTO2);
        ordPriceAlterationDTO1.setId(null);
        assertThat(ordPriceAlterationDTO1).isNotEqualTo(ordPriceAlterationDTO2);
    }
}
