package com.thunisoft.openhub.nav.bean;

import com.thunisoft.openhub.nav.repository.pojo.Category;
import com.thunisoft.openhub.nav.repository.pojo.Link;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 网址导航分类信息实体类，其中该实体类包含了子分类和各个分类下的网址链接信息.
 *
 * @author chenjiayin on 2020-08-03.
 * @since v1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class CategoryInfo {

    /**
     * ID.
     */
    private String id;

    /**
     * 分类所属页面，默认值为 {@code index}.
     */
    private String page;

    /**
     * 分类的名称.
     */
    private String name;

    /**
     * 分类的 {@code icon} 图标.
     */
    private String icon;

    /**
     * 分类的显示顺序.
     */
    private int order;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 最后更新时间.
     */
    private Date updateTime;

    /**
     * 该分类下的所有网址信息.
     */
    private List<Link> links;

    /**
     * 该分类下的所有子分类及网址信息.
     */
    private List<CategoryInfo> subCategories;

    /**
     * 创建一个新的 {@link Category} 实体类信息.
     *
     * @param parentId 父节点 ID
     * @return {@link Category} 实体类信息
     */
    public Category newCategory(String parentId) {
        Category category = new Category();
        category.setId(this.id);
        if (StringUtils.isNotBlank(parentId)) {
            category.setParentId(parentId);
        }
        category.setPage(this.page);
        category.setName(this.name);
        category.setIcon(this.icon);
        category.setOrder(this.order);
        category.setCreateTime(this.createTime);
        category.setUpdateTime(this.updateTime);
        return category;
    }

    /**
     * 构建并返回含有网址分类 ID 的网址集合信息.
     *
     * @return 网址集合信息
     */
    public List<Link> buildAndGetCategoryLinks() {
        if (CollectionUtils.isEmpty(links)) {
            return new ArrayList<>();
        }

        for (Link link : links) {
            link.setCategoryId(this.id);
            link.setCategoryName(this.name);
        }
        return links;
    }

}
