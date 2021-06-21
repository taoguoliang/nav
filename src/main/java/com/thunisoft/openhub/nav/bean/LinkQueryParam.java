package com.thunisoft.openhub.nav.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 网址模糊查询参数的实体类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class LinkQueryParam {

    /**
     * 所属分类名称.
     */
    private String categoryName;

    /**
     * 网址本省的名称.
     */
    private String name;

    /**
     * 网址的 URL 地址.
     */
    private String url;

}
