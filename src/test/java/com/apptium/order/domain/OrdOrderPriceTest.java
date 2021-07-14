package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderPriceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderPrice.class);
        OrdOrderPrice ordOrderPrice1 = new OrdOrderPrice();
        ordOrderPrice1.setId(1L);
        OrdOrderPrice ordOrderPrice2 = new OrdOrderPrice();
        ordOrderPrice2.setId(ordOrderPrice1.getId());
        assertThat(ordOrderPrice1).isEqualTo(ordOrderPrice2);
        ordOrderPrice2.setId(2L);
        assertThat(ordOrderPrice1).isNotEqualTo(ordOrderPrice2);
        ordOrderPrice1.setId(null);
        assertThat(ordOrderPrice1).isNotEqualTo(ordOrderPrice2);
    }
}
