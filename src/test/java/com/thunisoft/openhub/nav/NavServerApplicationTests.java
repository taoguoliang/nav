package com.thunisoft.openhub.nav;

import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用来测试 SpringBoot 服务能否正常启动的单元测试类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@SpringBootTest
class NavServerApplicationTests {

    /**
     * 一个恒为真的测试用例.
     */
    @Test
    void contextLoads() {
        Assertions.assertTrue(new Random().nextInt(1) < 2);
    }

}
