package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdAcquisitionCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdAcquisitionCharDTO.class);
        OrdAcquisitionCharDTO ordAcquisitionCharDTO1 = new OrdAcquisitionCharDTO();
        ordAcquisitionCharDTO1.setId(1L);
        OrdAcquisitionCharDTO ordAcquisitionCharDTO2 = new OrdAcquisitionCharDTO();
        assertThat(ordAcquisitionCharDTO1).isNotEqualTo(ordAcquisitionCharDTO2);
        ordAcquisitionCharDTO2.setId(ordAcquisitionCharDTO1.getId());
        assertThat(ordAcquisitionCharDTO1).isEqualTo(ordAcquisitionCharDTO2);
        ordAcquisitionCharDTO2.setId(2L);
        assertThat(ordAcquisitionCharDTO1).isNotEqualTo(ordAcquisitionCharDTO2);
        ordAcquisitionCharDTO1.setId(null);
        assertThat(ordAcquisitionCharDTO1).isNotEqualTo(ordAcquisitionCharDTO2);
    }
}
