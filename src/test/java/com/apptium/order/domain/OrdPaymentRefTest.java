package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPaymentRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPaymentRef.class);
        OrdPaymentRef ordPaymentRef1 = new OrdPaymentRef();
        ordPaymentRef1.setId(1L);
        OrdPaymentRef ordPaymentRef2 = new OrdPaymentRef();
        ordPaymentRef2.setId(ordPaymentRef1.getId());
        assertThat(ordPaymentRef1).isEqualTo(ordPaymentRef2);
        ordPaymentRef2.setId(2L);
        assertThat(ordPaymentRef1).isNotEqualTo(ordPaymentRef2);
        ordPaymentRef1.setId(null);
        assertThat(ordPaymentRef1).isNotEqualTo(ordPaymentRef2);
    }
}
