package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItem.class);
        OrdOrderItem ordOrderItem1 = new OrdOrderItem();
        ordOrderItem1.setId(1L);
        OrdOrderItem ordOrderItem2 = new OrdOrderItem();
        ordOrderItem2.setId(ordOrderItem1.getId());
        assertThat(ordOrderItem1).isEqualTo(ordOrderItem2);
        ordOrderItem2.setId(2L);
        assertThat(ordOrderItem1).isNotEqualTo(ordOrderItem2);
        ordOrderItem1.setId(null);
        assertThat(ordOrderItem1).isNotEqualTo(ordOrderItem2);
    }
}
