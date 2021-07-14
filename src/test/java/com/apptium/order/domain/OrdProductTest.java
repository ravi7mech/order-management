package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProduct.class);
        OrdProduct ordProduct1 = new OrdProduct();
        ordProduct1.setId(1L);
        OrdProduct ordProduct2 = new OrdProduct();
        ordProduct2.setId(ordProduct1.getId());
        assertThat(ordProduct1).isEqualTo(ordProduct2);
        ordProduct2.setId(2L);
        assertThat(ordProduct1).isNotEqualTo(ordProduct2);
        ordProduct1.setId(null);
        assertThat(ordProduct1).isNotEqualTo(ordProduct2);
    }
}
