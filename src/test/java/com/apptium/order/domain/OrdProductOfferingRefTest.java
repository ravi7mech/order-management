package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductOfferingRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductOfferingRef.class);
        OrdProductOfferingRef ordProductOfferingRef1 = new OrdProductOfferingRef();
        ordProductOfferingRef1.setId(1L);
        OrdProductOfferingRef ordProductOfferingRef2 = new OrdProductOfferingRef();
        ordProductOfferingRef2.setId(ordProductOfferingRef1.getId());
        assertThat(ordProductOfferingRef1).isEqualTo(ordProductOfferingRef2);
        ordProductOfferingRef2.setId(2L);
        assertThat(ordProductOfferingRef1).isNotEqualTo(ordProductOfferingRef2);
        ordProductOfferingRef1.setId(null);
        assertThat(ordProductOfferingRef1).isNotEqualTo(ordProductOfferingRef2);
    }
}
