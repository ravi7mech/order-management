package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContract.class);
        OrdContract ordContract1 = new OrdContract();
        ordContract1.setId(1L);
        OrdContract ordContract2 = new OrdContract();
        ordContract2.setId(ordContract1.getId());
        assertThat(ordContract1).isEqualTo(ordContract2);
        ordContract2.setId(2L);
        assertThat(ordContract1).isNotEqualTo(ordContract2);
        ordContract1.setId(null);
        assertThat(ordContract1).isNotEqualTo(ordContract2);
    }
}
