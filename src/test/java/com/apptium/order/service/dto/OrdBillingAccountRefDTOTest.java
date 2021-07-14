package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdBillingAccountRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdBillingAccountRefDTO.class);
        OrdBillingAccountRefDTO ordBillingAccountRefDTO1 = new OrdBillingAccountRefDTO();
        ordBillingAccountRefDTO1.setId(1L);
        OrdBillingAccountRefDTO ordBillingAccountRefDTO2 = new OrdBillingAccountRefDTO();
        assertThat(ordBillingAccountRefDTO1).isNotEqualTo(ordBillingAccountRefDTO2);
        ordBillingAccountRefDTO2.setId(ordBillingAccountRefDTO1.getId());
        assertThat(ordBillingAccountRefDTO1).isEqualTo(ordBillingAccountRefDTO2);
        ordBillingAccountRefDTO2.setId(2L);
        assertThat(ordBillingAccountRefDTO1).isNotEqualTo(ordBillingAccountRefDTO2);
        ordBillingAccountRefDTO1.setId(null);
        assertThat(ordBillingAccountRefDTO1).isNotEqualTo(ordBillingAccountRefDTO2);
    }
}
