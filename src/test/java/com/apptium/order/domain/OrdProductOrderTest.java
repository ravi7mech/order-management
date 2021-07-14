package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductOrder.class);
        OrdProductOrder ordProductOrder1 = new OrdProductOrder();
        ordProductOrder1.setId(1L);
        OrdProductOrder ordProductOrder2 = new OrdProductOrder();
        ordProductOrder2.setId(ordProductOrder1.getId());
        assertThat(ordProductOrder1).isEqualTo(ordProductOrder2);
        ordProductOrder2.setId(2L);
        assertThat(ordProductOrder1).isNotEqualTo(ordProductOrder2);
        ordProductOrder1.setId(null);
        assertThat(ordProductOrder1).isNotEqualTo(ordProductOrder2);
    }
}
