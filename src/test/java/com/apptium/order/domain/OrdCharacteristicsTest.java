package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdCharacteristicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdCharacteristics.class);
        OrdCharacteristics ordCharacteristics1 = new OrdCharacteristics();
        ordCharacteristics1.setId(1L);
        OrdCharacteristics ordCharacteristics2 = new OrdCharacteristics();
        ordCharacteristics2.setId(ordCharacteristics1.getId());
        assertThat(ordCharacteristics1).isEqualTo(ordCharacteristics2);
        ordCharacteristics2.setId(2L);
        assertThat(ordCharacteristics1).isNotEqualTo(ordCharacteristics2);
        ordCharacteristics1.setId(null);
        assertThat(ordCharacteristics1).isNotEqualTo(ordCharacteristics2);
    }
}
