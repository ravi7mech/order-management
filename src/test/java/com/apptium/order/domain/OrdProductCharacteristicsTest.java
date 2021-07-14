package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductCharacteristicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductCharacteristics.class);
        OrdProductCharacteristics ordProductCharacteristics1 = new OrdProductCharacteristics();
        ordProductCharacteristics1.setId(1L);
        OrdProductCharacteristics ordProductCharacteristics2 = new OrdProductCharacteristics();
        ordProductCharacteristics2.setId(ordProductCharacteristics1.getId());
        assertThat(ordProductCharacteristics1).isEqualTo(ordProductCharacteristics2);
        ordProductCharacteristics2.setId(2L);
        assertThat(ordProductCharacteristics1).isNotEqualTo(ordProductCharacteristics2);
        ordProductCharacteristics1.setId(null);
        assertThat(ordProductCharacteristics1).isNotEqualTo(ordProductCharacteristics2);
    }
}
