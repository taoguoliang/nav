package com.thunisoft.openhub.nav.utils;

import com.blinkfox.IdWorker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

/**
 * 字符串操作的工具类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StrUtils {

    /**
     * ID 生成器实例.
     */
    private static final IdWorker idWorker = new IdWorker();

    /**
     * 获取雪花算法生成的短 ID 字符串.
     *
     * @return 短 ID.
     */
    public static String getShortId() {
        return idWorker.get62RadixId();
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @param args 参数
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

}
