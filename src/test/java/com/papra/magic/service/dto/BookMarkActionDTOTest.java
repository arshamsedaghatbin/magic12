package com.papra.magic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookMarkActionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookMarkActionDTO.class);
        BookMarkActionDTO bookMarkActionDTO1 = new BookMarkActionDTO();
        bookMarkActionDTO1.setId(1L);
        BookMarkActionDTO bookMarkActionDTO2 = new BookMarkActionDTO();
        assertThat(bookMarkActionDTO1).isNotEqualTo(bookMarkActionDTO2);
        bookMarkActionDTO2.setId(bookMarkActionDTO1.getId());
        assertThat(bookMarkActionDTO1).isEqualTo(bookMarkActionDTO2);
        bookMarkActionDTO2.setId(2L);
        assertThat(bookMarkActionDTO1).isNotEqualTo(bookMarkActionDTO2);
        bookMarkActionDTO1.setId(null);
        assertThat(bookMarkActionDTO1).isNotEqualTo(bookMarkActionDTO2);
    }
}
