package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemRelationshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemRelationshipDTO.class);
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO1 = new OrdOrderItemRelationshipDTO();
        ordOrderItemRelationshipDTO1.setId(1L);
        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO2 = new OrdOrderItemRelationshipDTO();
        assertThat(ordOrderItemRelationshipDTO1).isNotEqualTo(ordOrderItemRelationshipDTO2);
        ordOrderItemRelationshipDTO2.setId(ordOrderItemRelationshipDTO1.getId());
        assertThat(ordOrderItemRelationshipDTO1).isEqualTo(ordOrderItemRelationshipDTO2);
        ordOrderItemRelationshipDTO2.setId(2L);
        assertThat(ordOrderItemRelationshipDTO1).isNotEqualTo(ordOrderItemRelationshipDTO2);
        ordOrderItemRelationshipDTO1.setId(null);
        assertThat(ordOrderItemRelationshipDTO1).isNotEqualTo(ordOrderItemRelationshipDTO2);
    }
}
