package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdChannelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdChannelDTO.class);
        OrdChannelDTO ordChannelDTO1 = new OrdChannelDTO();
        ordChannelDTO1.setId(1L);
        OrdChannelDTO ordChannelDTO2 = new OrdChannelDTO();
        assertThat(ordChannelDTO1).isNotEqualTo(ordChannelDTO2);
        ordChannelDTO2.setId(ordChannelDTO1.getId());
        assertThat(ordChannelDTO1).isEqualTo(ordChannelDTO2);
        ordChannelDTO2.setId(2L);
        assertThat(ordChannelDTO1).isNotEqualTo(ordChannelDTO2);
        ordChannelDTO1.setId(null);
        assertThat(ordChannelDTO1).isNotEqualTo(ordChannelDTO2);
    }
}
