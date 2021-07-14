package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPriceAlterationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPriceAlteration.class);
        OrdPriceAlteration ordPriceAlteration1 = new OrdPriceAlteration();
        ordPriceAlteration1.setId(1L);
        OrdPriceAlteration ordPriceAlteration2 = new OrdPriceAlteration();
        ordPriceAlteration2.setId(ordPriceAlteration1.getId());
        assertThat(ordPriceAlteration1).isEqualTo(ordPriceAlteration2);
        ordPriceAlteration2.setId(2L);
        assertThat(ordPriceAlteration1).isNotEqualTo(ordPriceAlteration2);
        ordPriceAlteration1.setId(null);
        assertThat(ordPriceAlteration1).isNotEqualTo(ordPriceAlteration2);
    }
}
