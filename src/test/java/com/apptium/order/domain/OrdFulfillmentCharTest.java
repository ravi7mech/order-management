package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdFulfillmentCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdFulfillmentChar.class);
        OrdFulfillmentChar ordFulfillmentChar1 = new OrdFulfillmentChar();
        ordFulfillmentChar1.setId(1L);
        OrdFulfillmentChar ordFulfillmentChar2 = new OrdFulfillmentChar();
        ordFulfillmentChar2.setId(ordFulfillmentChar1.getId());
        assertThat(ordFulfillmentChar1).isEqualTo(ordFulfillmentChar2);
        ordFulfillmentChar2.setId(2L);
        assertThat(ordFulfillmentChar1).isNotEqualTo(ordFulfillmentChar2);
        ordFulfillmentChar1.setId(null);
        assertThat(ordFulfillmentChar1).isNotEqualTo(ordFulfillmentChar2);
    }
}
