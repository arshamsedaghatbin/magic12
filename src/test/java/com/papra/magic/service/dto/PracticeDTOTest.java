package com.papra.magic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PracticeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PracticeDTO.class);
        PracticeDTO practiceDTO1 = new PracticeDTO();
        practiceDTO1.setId(1L);
        PracticeDTO practiceDTO2 = new PracticeDTO();
        assertThat(practiceDTO1).isNotEqualTo(practiceDTO2);
        practiceDTO2.setId(practiceDTO1.getId());
        assertThat(practiceDTO1).isEqualTo(practiceDTO2);
        practiceDTO2.setId(2L);
        assertThat(practiceDTO1).isNotEqualTo(practiceDTO2);
        practiceDTO1.setId(null);
        assertThat(practiceDTO1).isNotEqualTo(practiceDTO2);
    }
}
