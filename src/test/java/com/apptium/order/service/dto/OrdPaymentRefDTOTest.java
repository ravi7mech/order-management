package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPaymentRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPaymentRefDTO.class);
        OrdPaymentRefDTO ordPaymentRefDTO1 = new OrdPaymentRefDTO();
        ordPaymentRefDTO1.setId(1L);
        OrdPaymentRefDTO ordPaymentRefDTO2 = new OrdPaymentRefDTO();
        assertThat(ordPaymentRefDTO1).isNotEqualTo(ordPaymentRefDTO2);
        ordPaymentRefDTO2.setId(ordPaymentRefDTO1.getId());
        assertThat(ordPaymentRefDTO1).isEqualTo(ordPaymentRefDTO2);
        ordPaymentRefDTO2.setId(2L);
        assertThat(ordPaymentRefDTO1).isNotEqualTo(ordPaymentRefDTO2);
        ordPaymentRefDTO1.setId(null);
        assertThat(ordPaymentRefDTO1).isNotEqualTo(ordPaymentRefDTO2);
    }
}
