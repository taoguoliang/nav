package com.thunisoft.openhub.nav.repository.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 网址链接的实体类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Setter
@Getter
@Entity
@Table(name = "t_link")
@Accessors(chain = true)
public class Link {

    /**
     * ID.
     */
    @Id
    @Column(name = "c_id")
    private String id;

    /**
     * 网址所属的分类 ID.
     */
    @Column(name = "c_category_id")
    @NotBlank(message = "网址的所属分类不能为空!")
    private String categoryId;

    /**
     * 网址所属的分类的名称.
     */
    @Column(name = "c_category_name")
    private String categoryName;

    /**
     * 网址链接的名称.
     */
    @Column(name = "c_name")
    @NotBlank(message = "网址的名称不能为空!")
    private String name;

    /**
     * 分网址链接的 LOGO 地址，可以是相对路径也可以是一个 {@code URL} 绝对地址.
     */
    @Column(name = "c_logo")
    @NotBlank(message = "网址的 LOGO 不能为空!")
    private String logo;

    /**
     * 网址链接的简要描述信息.
     */
    @Column(name = "c_description", length = 510)
    @NotBlank(message = "网址的描述信息不能为空!")
    private String description;

    /**
     * 网址链接的 {@code URL} 地址.
     */
    @Column(name = "c_url")
    @NotBlank(message = "网址的 URL 链接不能为空!")
    private String url;

    /**
     * 网址链接的阅读点击次数.
     */
    @Column(name = "n_read")
    private int read;

    /**
     * 网址链接的点赞次数.
     */
    @Column(name = "n_stars")
    private int stars;

    /**
     * 网址链接的显示顺序.
     */
    @Column(name = "n_order")
    private int order;

    /**
     * 创建时间.
     */
    @Column(name = "dt_create_time")
    private Date createTime;

    /**
     * 最后更新时间.
     */
    @Column(name = "dt_update_time")
    private Date updateTime;

}
