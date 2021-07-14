package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContactDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContactDetails.class);
        OrdContactDetails ordContactDetails1 = new OrdContactDetails();
        ordContactDetails1.setId(1L);
        OrdContactDetails ordContactDetails2 = new OrdContactDetails();
        ordContactDetails2.setId(ordContactDetails1.getId());
        assertThat(ordContactDetails1).isEqualTo(ordContactDetails2);
        ordContactDetails2.setId(2L);
        assertThat(ordContactDetails1).isNotEqualTo(ordContactDetails2);
        ordContactDetails1.setId(null);
        assertThat(ordContactDetails1).isNotEqualTo(ordContactDetails2);
    }
}
