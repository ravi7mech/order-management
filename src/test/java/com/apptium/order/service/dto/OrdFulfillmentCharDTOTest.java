package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdFulfillmentCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdFulfillmentCharDTO.class);
        OrdFulfillmentCharDTO ordFulfillmentCharDTO1 = new OrdFulfillmentCharDTO();
        ordFulfillmentCharDTO1.setId(1L);
        OrdFulfillmentCharDTO ordFulfillmentCharDTO2 = new OrdFulfillmentCharDTO();
        assertThat(ordFulfillmentCharDTO1).isNotEqualTo(ordFulfillmentCharDTO2);
        ordFulfillmentCharDTO2.setId(ordFulfillmentCharDTO1.getId());
        assertThat(ordFulfillmentCharDTO1).isEqualTo(ordFulfillmentCharDTO2);
        ordFulfillmentCharDTO2.setId(2L);
        assertThat(ordFulfillmentCharDTO1).isNotEqualTo(ordFulfillmentCharDTO2);
        ordFulfillmentCharDTO1.setId(null);
        assertThat(ordFulfillmentCharDTO1).isNotEqualTo(ordFulfillmentCharDTO2);
    }
}
