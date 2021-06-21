package com.thunisoft.openhub.nav.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 本服务的 Spring Security 的配置类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要登录访问权限的 URL.
     */
    private static final String[] AUTH_URLS = new String[] {"/api/v1/**", "/dashboard", "/categories", "/links"};

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 配置安全拦截信息.
     *
     * @param http {@link HttpSecurity} 实例
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/session/login")
                .successHandler((req, response, auth) -> {
                    log.info("登录成功.");
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(objectMapper.writeValueAsString(auth));
                })
                .failureHandler((req, response, e) -> {
                    if (log.isDebugEnabled()) {
                        log.debug("登录失败，错误信息堆栈为：", e);
                    } else {
                        log.warn("登录失败，错误描述信息为：【{}】", e.getMessage());
                    }
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
                })
                .and().logout()
                .logoutUrl("/session/logout").logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and().authorizeRequests()
                .antMatchers(AUTH_URLS).authenticated()
                .anyRequest().permitAll()
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

}
