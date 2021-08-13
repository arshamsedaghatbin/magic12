package com.papra.magic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookMarkActionMapperTest {

    private BookMarkActionMapper bookMarkActionMapper;

    @BeforeEach
    public void setUp() {
        bookMarkActionMapper = new BookMarkActionMapperImpl();
    }
}
