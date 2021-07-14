package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemChar.class);
        OrdOrderItemChar ordOrderItemChar1 = new OrdOrderItemChar();
        ordOrderItemChar1.setId(1L);
        OrdOrderItemChar ordOrderItemChar2 = new OrdOrderItemChar();
        ordOrderItemChar2.setId(ordOrderItemChar1.getId());
        assertThat(ordOrderItemChar1).isEqualTo(ordOrderItemChar2);
        ordOrderItemChar2.setId(2L);
        assertThat(ordOrderItemChar1).isNotEqualTo(ordOrderItemChar2);
        ordOrderItemChar1.setId(null);
        assertThat(ordOrderItemChar1).isNotEqualTo(ordOrderItemChar2);
    }
}
