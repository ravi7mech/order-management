package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdAcquisitionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdAcquisitionDTO.class);
        OrdAcquisitionDTO ordAcquisitionDTO1 = new OrdAcquisitionDTO();
        ordAcquisitionDTO1.setId(1L);
        OrdAcquisitionDTO ordAcquisitionDTO2 = new OrdAcquisitionDTO();
        assertThat(ordAcquisitionDTO1).isNotEqualTo(ordAcquisitionDTO2);
        ordAcquisitionDTO2.setId(ordAcquisitionDTO1.getId());
        assertThat(ordAcquisitionDTO1).isEqualTo(ordAcquisitionDTO2);
        ordAcquisitionDTO2.setId(2L);
        assertThat(ordAcquisitionDTO1).isNotEqualTo(ordAcquisitionDTO2);
        ordAcquisitionDTO1.setId(null);
        assertThat(ordAcquisitionDTO1).isNotEqualTo(ordAcquisitionDTO2);
    }
}
