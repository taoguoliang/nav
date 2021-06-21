package com.thunisoft.openhub.nav.exception;

import com.thunisoft.openhub.nav.utils.StrUtils;

/**
 * 本服务自定义的运行时异常.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
public class RunException extends RuntimeException {

    private static final long serialVersionUID = -3030387677806493215L;

    /**
     * 根据 Message 来构造异常实例.
     *
     * @param message 消息
     */
    public RunException(String message) {
        super(message);
    }

    /**
     * 根据 pattern 模式字符串和对应的占位参数来构造异常实例.
     *
     * @param pattern 消息
     * @param args 不定参
     */
    public RunException(String pattern, Object... args) {
        super(StrUtils.format(pattern, args));
    }

    /**
     * 根据 message 和 异常实例来构造异常实例.
     *
     * @param e Throwable 实例
     * @param message 消息
     */
    public RunException(Throwable e, String message) {
        super(message, e);
    }

    /**
     * 根据 pattern 模式字符串、不定参数和异常实例来构造 {@link RunException} 异常实例.
     *
     * @param e Throwable 实例
     * @param pattern 消息
     * @param args 不定参
     */
    public RunException(Throwable e, String pattern, Object... args) {
        super(StrUtils.format(pattern, args), e);
    }

}
