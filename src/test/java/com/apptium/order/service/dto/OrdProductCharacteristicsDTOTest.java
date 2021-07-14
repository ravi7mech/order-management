package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdProductCharacteristicsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdProductCharacteristicsDTO.class);
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO1 = new OrdProductCharacteristicsDTO();
        ordProductCharacteristicsDTO1.setId(1L);
        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO2 = new OrdProductCharacteristicsDTO();
        assertThat(ordProductCharacteristicsDTO1).isNotEqualTo(ordProductCharacteristicsDTO2);
        ordProductCharacteristicsDTO2.setId(ordProductCharacteristicsDTO1.getId());
        assertThat(ordProductCharacteristicsDTO1).isEqualTo(ordProductCharacteristicsDTO2);
        ordProductCharacteristicsDTO2.setId(2L);
        assertThat(ordProductCharacteristicsDTO1).isNotEqualTo(ordProductCharacteristicsDTO2);
        ordProductCharacteristicsDTO1.setId(null);
        assertThat(ordProductCharacteristicsDTO1).isNotEqualTo(ordProductCharacteristicsDTO2);
    }
}
