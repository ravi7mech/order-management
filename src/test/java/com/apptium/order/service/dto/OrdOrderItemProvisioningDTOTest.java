package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemProvisioningDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemProvisioningDTO.class);
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO1 = new OrdOrderItemProvisioningDTO();
        ordOrderItemProvisioningDTO1.setId(1L);
        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO2 = new OrdOrderItemProvisioningDTO();
        assertThat(ordOrderItemProvisioningDTO1).isNotEqualTo(ordOrderItemProvisioningDTO2);
        ordOrderItemProvisioningDTO2.setId(ordOrderItemProvisioningDTO1.getId());
        assertThat(ordOrderItemProvisioningDTO1).isEqualTo(ordOrderItemProvisioningDTO2);
        ordOrderItemProvisioningDTO2.setId(2L);
        assertThat(ordOrderItemProvisioningDTO1).isNotEqualTo(ordOrderItemProvisioningDTO2);
        ordOrderItemProvisioningDTO1.setId(null);
        assertThat(ordOrderItemProvisioningDTO1).isNotEqualTo(ordOrderItemProvisioningDTO2);
    }
}
