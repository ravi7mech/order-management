package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdAcquisitionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdAcquisition.class);
        OrdAcquisition ordAcquisition1 = new OrdAcquisition();
        ordAcquisition1.setId(1L);
        OrdAcquisition ordAcquisition2 = new OrdAcquisition();
        ordAcquisition2.setId(ordAcquisition1.getId());
        assertThat(ordAcquisition1).isEqualTo(ordAcquisition2);
        ordAcquisition2.setId(2L);
        assertThat(ordAcquisition1).isNotEqualTo(ordAcquisition2);
        ordAcquisition1.setId(null);
        assertThat(ordAcquisition1).isNotEqualTo(ordAcquisition2);
    }
}
