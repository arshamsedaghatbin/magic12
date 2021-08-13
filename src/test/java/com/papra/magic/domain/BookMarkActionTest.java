package com.papra.magic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookMarkActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookMarkAction.class);
        BookMarkAction bookMarkAction1 = new BookMarkAction();
        bookMarkAction1.setId(1L);
        BookMarkAction bookMarkAction2 = new BookMarkAction();
        bookMarkAction2.setId(bookMarkAction1.getId());
        assertThat(bookMarkAction1).isEqualTo(bookMarkAction2);
        bookMarkAction2.setId(2L);
        assertThat(bookMarkAction1).isNotEqualTo(bookMarkAction2);
        bookMarkAction1.setId(null);
        assertThat(bookMarkAction1).isNotEqualTo(bookMarkAction2);
    }
}
