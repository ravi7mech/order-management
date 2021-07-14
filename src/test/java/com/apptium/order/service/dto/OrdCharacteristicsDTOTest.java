package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdCharacteristicsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdCharacteristicsDTO.class);
        OrdCharacteristicsDTO ordCharacteristicsDTO1 = new OrdCharacteristicsDTO();
        ordCharacteristicsDTO1.setId(1L);
        OrdCharacteristicsDTO ordCharacteristicsDTO2 = new OrdCharacteristicsDTO();
        assertThat(ordCharacteristicsDTO1).isNotEqualTo(ordCharacteristicsDTO2);
        ordCharacteristicsDTO2.setId(ordCharacteristicsDTO1.getId());
        assertThat(ordCharacteristicsDTO1).isEqualTo(ordCharacteristicsDTO2);
        ordCharacteristicsDTO2.setId(2L);
        assertThat(ordCharacteristicsDTO1).isNotEqualTo(ordCharacteristicsDTO2);
        ordCharacteristicsDTO1.setId(null);
        assertThat(ordCharacteristicsDTO1).isNotEqualTo(ordCharacteristicsDTO2);
    }
}
