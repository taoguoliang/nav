package com.thunisoft.openhub.nav;

import com.blinkfox.fenix.EnableFenix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 根启动类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@EnableFenix
@SpringBootApplication
public class NavServerApplication {

    /**
     * 启动主入口方法.
     *
     * @param args 数组参数
     */
    public static void main(String[] args) {
        log.warn("【启动服务 -> 开始】正在启动【nav-server】服务 ...");
        SpringApplication.run(NavServerApplication.class, args);
    }

}
