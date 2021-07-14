package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdContractCharacteristicsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdContractCharacteristicsDTO.class);
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO1 = new OrdContractCharacteristicsDTO();
        ordContractCharacteristicsDTO1.setId(1L);
        OrdContractCharacteristicsDTO ordContractCharacteristicsDTO2 = new OrdContractCharacteristicsDTO();
        assertThat(ordContractCharacteristicsDTO1).isNotEqualTo(ordContractCharacteristicsDTO2);
        ordContractCharacteristicsDTO2.setId(ordContractCharacteristicsDTO1.getId());
        assertThat(ordContractCharacteristicsDTO1).isEqualTo(ordContractCharacteristicsDTO2);
        ordContractCharacteristicsDTO2.setId(2L);
        assertThat(ordContractCharacteristicsDTO1).isNotEqualTo(ordContractCharacteristicsDTO2);
        ordContractCharacteristicsDTO1.setId(null);
        assertThat(ordContractCharacteristicsDTO1).isNotEqualTo(ordContractCharacteristicsDTO2);
    }
}
