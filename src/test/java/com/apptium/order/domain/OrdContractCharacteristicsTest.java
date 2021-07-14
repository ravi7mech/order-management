package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContractCharacteristicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContractCharacteristics.class);
        OrdContractCharacteristics ordContractCharacteristics1 = new OrdContractCharacteristics();
        ordContractCharacteristics1.setId(1L);
        OrdContractCharacteristics ordContractCharacteristics2 = new OrdContractCharacteristics();
        ordContractCharacteristics2.setId(ordContractCharacteristics1.getId());
        assertThat(ordContractCharacteristics1).isEqualTo(ordContractCharacteristics2);
        ordContractCharacteristics2.setId(2L);
        assertThat(ordContractCharacteristics1).isNotEqualTo(ordContractCharacteristics2);
        ordContractCharacteristics1.setId(null);
        assertThat(ordContractCharacteristics1).isNotEqualTo(ordContractCharacteristics2);
    }
}
