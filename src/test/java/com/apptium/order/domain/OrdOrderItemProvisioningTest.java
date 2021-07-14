package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdOrderItemProvisioningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdOrderItemProvisioning.class);
        OrdOrderItemProvisioning ordOrderItemProvisioning1 = new OrdOrderItemProvisioning();
        ordOrderItemProvisioning1.setId(1L);
        OrdOrderItemProvisioning ordOrderItemProvisioning2 = new OrdOrderItemProvisioning();
        ordOrderItemProvisioning2.setId(ordOrderItemProvisioning1.getId());
        assertThat(ordOrderItemProvisioning1).isEqualTo(ordOrderItemProvisioning2);
        ordOrderItemProvisioning2.setId(2L);
        assertThat(ordOrderItemProvisioning1).isNotEqualTo(ordOrderItemProvisioning2);
        ordOrderItemProvisioning1.setId(null);
        assertThat(ordOrderItemProvisioning1).isNotEqualTo(ordOrderItemProvisioning2);
    }
}
