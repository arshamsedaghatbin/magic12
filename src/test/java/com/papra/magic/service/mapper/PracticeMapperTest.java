package com.papra.magic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PracticeMapperTest {

    private PracticeMapper practiceMapper;

    @BeforeEach
    public void setUp() {
        practiceMapper = new PracticeMapperImpl();
    }
}
