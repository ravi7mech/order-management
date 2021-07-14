package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductOfferingRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductOfferingRefDTO.class);
        OrdProductOfferingRefDTO ordProductOfferingRefDTO1 = new OrdProductOfferingRefDTO();
        ordProductOfferingRefDTO1.setId(1L);
        OrdProductOfferingRefDTO ordProductOfferingRefDTO2 = new OrdProductOfferingRefDTO();
        assertThat(ordProductOfferingRefDTO1).isNotEqualTo(ordProductOfferingRefDTO2);
        ordProductOfferingRefDTO2.setId(ordProductOfferingRefDTO1.getId());
        assertThat(ordProductOfferingRefDTO1).isEqualTo(ordProductOfferingRefDTO2);
        ordProductOfferingRefDTO2.setId(2L);
        assertThat(ordProductOfferingRefDTO1).isNotEqualTo(ordProductOfferingRefDTO2);
        ordProductOfferingRefDTO1.setId(null);
        assertThat(ordProductOfferingRefDTO1).isNotEqualTo(ordProductOfferingRefDTO2);
    }
}
