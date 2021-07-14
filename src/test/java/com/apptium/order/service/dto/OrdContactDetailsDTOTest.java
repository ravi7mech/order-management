package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContactDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContactDetailsDTO.class);
        OrdContactDetailsDTO ordContactDetailsDTO1 = new OrdContactDetailsDTO();
        ordContactDetailsDTO1.setId(1L);
        OrdContactDetailsDTO ordContactDetailsDTO2 = new OrdContactDetailsDTO();
        assertThat(ordContactDetailsDTO1).isNotEqualTo(ordContactDetailsDTO2);
        ordContactDetailsDTO2.setId(ordContactDetailsDTO1.getId());
        assertThat(ordContactDetailsDTO1).isEqualTo(ordContactDetailsDTO2);
        ordContactDetailsDTO2.setId(2L);
        assertThat(ordContactDetailsDTO1).isNotEqualTo(ordContactDetailsDTO2);
        ordContactDetailsDTO1.setId(null);
        assertThat(ordContactDetailsDTO1).isNotEqualTo(ordContactDetailsDTO2);
    }
}
