package com.apptium.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdNote.class);
        OrdNote ordNote1 = new OrdNote();
        ordNote1.setId(1L);
        OrdNote ordNote2 = new OrdNote();
        ordNote2.setId(ordNote1.getId());
        assertThat(ordNote1).isEqualTo(ordNote2);
        ordNote2.setId(2L);
        assertThat(ordNote1).isNotEqualTo(ordNote2);
        ordNote1.setId(null);
        assertThat(ordNote1).isNotEqualTo(ordNote2);
    }
}
