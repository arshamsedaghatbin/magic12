package com.papra.magic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PracticeSessionMapperTest {

    private PracticeSessionMapper practiceSessionMapper;

    @BeforeEach
    public void setUp() {
        practiceSessionMapper = new PracticeSessionMapperImpl();
    }
}
