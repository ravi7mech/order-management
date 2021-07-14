package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPriceAmountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPriceAmount.class);
        OrdPriceAmount ordPriceAmount1 = new OrdPriceAmount();
        ordPriceAmount1.setId(1L);
        OrdPriceAmount ordPriceAmount2 = new OrdPriceAmount();
        ordPriceAmount2.setId(ordPriceAmount1.getId());
        assertThat(ordPriceAmount1).isEqualTo(ordPriceAmount2);
        ordPriceAmount2.setId(2L);
        assertThat(ordPriceAmount1).isNotEqualTo(ordPriceAmount2);
        ordPriceAmount1.setId(null);
        assertThat(ordPriceAmount1).isNotEqualTo(ordPriceAmount2);
    }
}
