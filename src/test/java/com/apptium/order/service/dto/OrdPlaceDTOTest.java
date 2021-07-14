package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdPlaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdPlaceDTO.class);
        OrdPlaceDTO ordPlaceDTO1 = new OrdPlaceDTO();
        ordPlaceDTO1.setId(1L);
        OrdPlaceDTO ordPlaceDTO2 = new OrdPlaceDTO();
        assertThat(ordPlaceDTO1).isNotEqualTo(ordPlaceDTO2);
        ordPlaceDTO2.setId(ordPlaceDTO1.getId());
        assertThat(ordPlaceDTO1).isEqualTo(ordPlaceDTO2);
        ordPlaceDTO2.setId(2L);
        assertThat(ordPlaceDTO1).isNotEqualTo(ordPlaceDTO2);
        ordPlaceDTO1.setId(null);
        assertThat(ordPlaceDTO1).isNotEqualTo(ordPlaceDTO2);
    }
}
