package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContractDTO.class);
        OrdContractDTO ordContractDTO1 = new OrdContractDTO();
        ordContractDTO1.setId(1L);
        OrdContractDTO ordContractDTO2 = new OrdContractDTO();
        assertThat(ordContractDTO1).isNotEqualTo(ordContractDTO2);
        ordContractDTO2.setId(ordContractDTO1.getId());
        assertThat(ordContractDTO1).isEqualTo(ordContractDTO2);
        ordContractDTO2.setId(2L);
        assertThat(ordContractDTO1).isNotEqualTo(ordContractDTO2);
        ordContractDTO1.setId(null);
        assertThat(ordContractDTO1).isNotEqualTo(ordContractDTO2);
    }
}
