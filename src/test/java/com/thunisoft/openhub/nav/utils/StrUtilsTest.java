package com.thunisoft.openhub.nav.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class StrUtilsTest {

    @Test
    void getShortId() {
        Assertions.assertNotNull(StrUtils.getShortId());
        log.info("生成的 ID: [{}].", StrUtils.getShortId());
    }

    @Test
    void format() {
        Assertions.assertEquals("Hello World.", StrUtils.format("Hello {}.", "World"));
    }

}