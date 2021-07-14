package com.apptium.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdNoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdNoteDTO.class);
        OrdNoteDTO ordNoteDTO1 = new OrdNoteDTO();
        ordNoteDTO1.setId(1L);
        OrdNoteDTO ordNoteDTO2 = new OrdNoteDTO();
        assertThat(ordNoteDTO1).isNotEqualTo(ordNoteDTO2);
        ordNoteDTO2.setId(ordNoteDTO1.getId());
        assertThat(ordNoteDTO1).isEqualTo(ordNoteDTO2);
        ordNoteDTO2.setId(2L);
        assertThat(ordNoteDTO1).isNotEqualTo(ordNoteDTO2);
        ordNoteDTO1.setId(null);
        assertThat(ordNoteDTO1).isNotEqualTo(ordNoteDTO2);
    }
}
