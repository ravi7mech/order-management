package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProvisiongCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProvisiongChar.class);
        OrdProvisiongChar ordProvisiongChar1 = new OrdProvisiongChar();
        ordProvisiongChar1.setId(1L);
        OrdProvisiongChar ordProvisiongChar2 = new OrdProvisiongChar();
        ordProvisiongChar2.setId(ordProvisiongChar1.getId());
        assertThat(ordProvisiongChar1).isEqualTo(ordProvisiongChar2);
        ordProvisiongChar2.setId(2L);
        assertThat(ordProvisiongChar1).isNotEqualTo(ordProvisiongChar2);
        ordProvisiongChar1.setId(null);
        assertThat(ordProvisiongChar1).isNotEqualTo(ordProvisiongChar2);
    }
}
