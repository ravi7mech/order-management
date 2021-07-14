package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPlace.class);
        OrdPlace ordPlace1 = new OrdPlace();
        ordPlace1.setId(1L);
        OrdPlace ordPlace2 = new OrdPlace();
        ordPlace2.setId(ordPlace1.getId());
        assertThat(ordPlace1).isEqualTo(ordPlace2);
        ordPlace2.setId(2L);
        assertThat(ordPlace1).isNotEqualTo(ordPlace2);
        ordPlace1.setId(null);
        assertThat(ordPlace1).isNotEqualTo(ordPlace2);
    }
}
