package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdReasonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdReason.class);
        OrdReason ordReason1 = new OrdReason();
        ordReason1.setId(1L);
        OrdReason ordReason2 = new OrdReason();
        ordReason2.setId(ordReason1.getId());
        assertThat(ordReason1).isEqualTo(ordReason2);
        ordReason2.setId(2L);
        assertThat(ordReason1).isNotEqualTo(ordReason2);
        ordReason1.setId(null);
        assertThat(ordReason1).isNotEqualTo(ordReason2);
    }
}
