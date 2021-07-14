package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemRelationship.class);
        OrdOrderItemRelationship ordOrderItemRelationship1 = new OrdOrderItemRelationship();
        ordOrderItemRelationship1.setId(1L);
        OrdOrderItemRelationship ordOrderItemRelationship2 = new OrdOrderItemRelationship();
        ordOrderItemRelationship2.setId(ordOrderItemRelationship1.getId());
        assertThat(ordOrderItemRelationship1).isEqualTo(ordOrderItemRelationship2);
        ordOrderItemRelationship2.setId(2L);
        assertThat(ordOrderItemRelationship1).isNotEqualTo(ordOrderItemRelationship2);
        ordOrderItemRelationship1.setId(null);
        assertThat(ordOrderItemRelationship1).isNotEqualTo(ordOrderItemRelationship2);
    }
}
