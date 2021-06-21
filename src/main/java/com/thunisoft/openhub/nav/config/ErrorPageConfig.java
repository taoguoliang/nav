package com.thunisoft.openhub.nav.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置处理类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Configuration
public class ErrorPageConfig {

    /**
     * 配置错误页面处理.
     *
     * @return {@link WebServerFactoryCustomizer} 实例
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    }

}
