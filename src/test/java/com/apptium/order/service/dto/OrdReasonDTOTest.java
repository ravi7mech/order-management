package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdReasonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdReasonDTO.class);
        OrdReasonDTO ordReasonDTO1 = new OrdReasonDTO();
        ordReasonDTO1.setId(1L);
        OrdReasonDTO ordReasonDTO2 = new OrdReasonDTO();
        assertThat(ordReasonDTO1).isNotEqualTo(ordReasonDTO2);
        ordReasonDTO2.setId(ordReasonDTO1.getId());
        assertThat(ordReasonDTO1).isEqualTo(ordReasonDTO2);
        ordReasonDTO2.setId(2L);
        assertThat(ordReasonDTO1).isNotEqualTo(ordReasonDTO2);
        ordReasonDTO1.setId(null);
        assertThat(ordReasonDTO1).isNotEqualTo(ordReasonDTO2);
    }
}
