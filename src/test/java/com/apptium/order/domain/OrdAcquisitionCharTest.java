package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdAcquisitionCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdAcquisitionChar.class);
        OrdAcquisitionChar ordAcquisitionChar1 = new OrdAcquisitionChar();
        ordAcquisitionChar1.setId(1L);
        OrdAcquisitionChar ordAcquisitionChar2 = new OrdAcquisitionChar();
        ordAcquisitionChar2.setId(ordAcquisitionChar1.getId());
        assertThat(ordAcquisitionChar1).isEqualTo(ordAcquisitionChar2);
        ordAcquisitionChar2.setId(2L);
        assertThat(ordAcquisitionChar1).isNotEqualTo(ordAcquisitionChar2);
        ordAcquisitionChar1.setId(null);
        assertThat(ordAcquisitionChar1).isNotEqualTo(ordAcquisitionChar2);
    }
}
