package com.thunisoft.openhub.nav.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 本服务的全局异常处理类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理 Exception 的方法.
     *
     * @param e 异常实例
     * @return 异常提示的字符串
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("【异常处理】请求的服务接口发生了异常，请检查!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    /**
     * 统一处理 {@link RunException} 的方法.
     *
     * @param e 异常实例
     * @return 异常提示的字符串
     */
    @ExceptionHandler(RunException.class)
    public ResponseEntity<String> handleRunException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
