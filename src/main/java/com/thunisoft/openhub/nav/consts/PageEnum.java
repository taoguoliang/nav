package com.thunisoft.openhub.nav.consts;

import com.thunisoft.openhub.nav.bean.Code;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 网址分类所在页面的枚举类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PageEnum {

    /**
     * 首页中的网址导航.
     */
    INDEX("index", "首页"),

    /**
     * 互联网中的网址导航.
     */
    INTERNET("internet", "互联网");

    /**
     * 页面代码值.
     */
    @Getter
    private final String code;

    /**
     * 页面名称.
     */
    @Getter
    private final String name;

    /**
     * 根据代码值实例化 {@link PageEnum} 实例.
     *
     * @param code 代码值
     * @return {@link PageEnum} 实例
     */
    public static PageEnum of(String code) {
        PageEnum pageEnum = PageEnum.ofNullable(code);
        return pageEnum == null ? PageEnum.INDEX : pageEnum;
    }

    /**
     * 根据代码值实例化 {@link PageEnum} 实例，结果可能为 {@code null}.
     *
     * @param code 代码值
     * @return {@link PageEnum} 实例
     */
    public static PageEnum ofNullable(String code) {
        for (PageEnum pageEnum : PageEnum.values()) {
            if (pageEnum.getCode().equalsIgnoreCase(code)) {
                return pageEnum;
            }
        }
        return null;
    }

    /**
     * 返回代码信息集合.
     *
     * @return 代码信息集合
     */
    public static List<Code> getCodeList() {
        List<Code> codes = new ArrayList<>();
        codes.add(new Code(INDEX.getCode(), INDEX.getName()));
        codes.add(new Code(INTERNET.getCode(), INTERNET.getName()));
        return codes;
    }

}
