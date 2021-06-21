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
 * 导航链接的分类实体类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Setter
@Getter
@Entity
@Table(name = "t_category")
@Accessors(chain = true)
public class Category {

    /**
     * ID.
     */
    @Id
    @Column(name = "c_id")
    private String id;

    /**
     * 分类的父 ID.
     */
    @Column(name = "c_parent_id")
    private String parentId;

    /**
     * 分类所属页面，默认值为 {@code index}.
     */
    @Column(name = "c_page")
    @NotBlank(message = "分类的所属页面不能为空!")
    private String page;

    /**
     * 分类的名称.
     */
    @Column(name = "c_name")
    @NotBlank(message = "分类的名称不能为空!")
    private String name;

    /**
     * 分类的 {@code icon} 图标.
     */
    @Column(name = "c_icon")
    @NotBlank(message = "分类的 ICON 图标不能为空!")
    private String icon;

    /**
     * 分类的显示顺序.
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
