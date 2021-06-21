package com.thunisoft.openhub.nav.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 网址信息结果实体类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class LinkResult implements Comparable<LinkResult> {

    /**
     * 网址链接的名称.
     */
    private String name;

    /**
     * 分网址链接的 LOGO 地址，可以是相对路径也可以是一个 {@code URL} 绝对地址.
     */
    private String logo;

    /**
     * 网址链接的简要描述信息.
     */
    private String description;

    /**
     * 网址链接的 {@code URL} 地址.
     */
    private String url;

    /**
     * 网址链接的阅读点击次数.
     */
    private int read;

    /**
     * 网址链接的点赞次数.
     */
    private int stars;

    /**
     * 网址链接的显示顺序.
     */
    private int order;

    /**
     * 对象的大小比较.
     *
     * @param o 待比较的对象.
     * @return 比较结果
     */
    @Override
    public int compareTo(LinkResult o) {
        return Integer.compare(this.order, o.order);
    }

}
