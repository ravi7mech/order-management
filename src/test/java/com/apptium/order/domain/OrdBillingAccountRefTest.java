package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdBillingAccountRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdBillingAccountRef.class);
        OrdBillingAccountRef ordBillingAccountRef1 = new OrdBillingAccountRef();
        ordBillingAccountRef1.setId(1L);
        OrdBillingAccountRef ordBillingAccountRef2 = new OrdBillingAccountRef();
        ordBillingAccountRef2.setId(ordBillingAccountRef1.getId());
        assertThat(ordBillingAccountRef1).isEqualTo(ordBillingAccountRef2);
        ordBillingAccountRef2.setId(2L);
        assertThat(ordBillingAccountRef1).isNotEqualTo(ordBillingAccountRef2);
        ordBillingAccountRef1.setId(null);
        assertThat(ordBillingAccountRef1).isNotEqualTo(ordBillingAccountRef2);
    }
}
