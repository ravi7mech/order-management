package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdFulfillmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdFulfillmentDTO.class);
        OrdFulfillmentDTO ordFulfillmentDTO1 = new OrdFulfillmentDTO();
        ordFulfillmentDTO1.setId(1L);
        OrdFulfillmentDTO ordFulfillmentDTO2 = new OrdFulfillmentDTO();
        assertThat(ordFulfillmentDTO1).isNotEqualTo(ordFulfillmentDTO2);
        ordFulfillmentDTO2.setId(ordFulfillmentDTO1.getId());
        assertThat(ordFulfillmentDTO1).isEqualTo(ordFulfillmentDTO2);
        ordFulfillmentDTO2.setId(2L);
        assertThat(ordFulfillmentDTO1).isNotEqualTo(ordFulfillmentDTO2);
        ordFulfillmentDTO1.setId(null);
        assertThat(ordFulfillmentDTO1).isNotEqualTo(ordFulfillmentDTO2);
    }
}
