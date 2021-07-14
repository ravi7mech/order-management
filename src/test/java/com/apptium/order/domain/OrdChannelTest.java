package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdChannel.class);
        OrdChannel ordChannel1 = new OrdChannel();
        ordChannel1.setId(1L);
        OrdChannel ordChannel2 = new OrdChannel();
        ordChannel2.setId(ordChannel1.getId());
        assertThat(ordChannel1).isEqualTo(ordChannel2);
        ordChannel2.setId(2L);
        assertThat(ordChannel1).isNotEqualTo(ordChannel2);
        ordChannel1.setId(null);
        assertThat(ordChannel1).isNotEqualTo(ordChannel2);
    }
}
