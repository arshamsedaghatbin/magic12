package com.papra.magic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PracticeSessionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PracticeSessionDTO.class);
        PracticeSessionDTO practiceSessionDTO1 = new PracticeSessionDTO();
        practiceSessionDTO1.setId(1L);
        PracticeSessionDTO practiceSessionDTO2 = new PracticeSessionDTO();
        assertThat(practiceSessionDTO1).isNotEqualTo(practiceSessionDTO2);
        practiceSessionDTO2.setId(practiceSessionDTO1.getId());
        assertThat(practiceSessionDTO1).isEqualTo(practiceSessionDTO2);
        practiceSessionDTO2.setId(2L);
        assertThat(practiceSessionDTO1).isNotEqualTo(practiceSessionDTO2);
        practiceSessionDTO1.setId(null);
        assertThat(practiceSessionDTO1).isNotEqualTo(practiceSessionDTO2);
    }
}
