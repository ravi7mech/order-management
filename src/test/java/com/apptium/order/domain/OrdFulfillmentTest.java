package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdFulfillmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdFulfillment.class);
        OrdFulfillment ordFulfillment1 = new OrdFulfillment();
        ordFulfillment1.setId(1L);
        OrdFulfillment ordFulfillment2 = new OrdFulfillment();
        ordFulfillment2.setId(ordFulfillment1.getId());
        assertThat(ordFulfillment1).isEqualTo(ordFulfillment2);
        ordFulfillment2.setId(2L);
        assertThat(ordFulfillment1).isNotEqualTo(ordFulfillment2);
        ordFulfillment1.setId(null);
        assertThat(ordFulfillment1).isNotEqualTo(ordFulfillment2);
    }
}
