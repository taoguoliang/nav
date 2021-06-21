package com.thunisoft.openhub.nav.bean;

import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

/**
 * 分类结果实体类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CategoryLinks implements Comparable<CategoryLinks> {

    /**
     * 网址所属分类 ID.
     */
    private String id;

    /**
     * 网址所属分类的名称.
     */
    private String name;

    /**
     * 网址所属分类的 {@code icon} 图标.
     */
    private String icon;

    /**
     * 网址所属分类的显示顺序.
     */
    private int order;

    /**
     * 该分类下的所有链接.
     */
    private List<LinkResult> links;

    /**
     * 该分类下的所有子分类.
     */
    private List<CategoryLinks> subCategories;

    /**
     * 对象的大小比较.
     *
     * @param o 待比较的对象.
     * @return 比较结果
     */
    @Override
    public int compareTo(CategoryLinks o) {
        return Integer.compare(this.order, o.order);
    }

    /**
     * 对子分类和其下所有网址集合按照 order 进行排序.
     */
    public void sort() {
        // 对该分类下的所有网址进行排序.
        this.sort(this.links);

        // 对子分类进行排序，并对子分类下的所有网址集合再进行排序.
        if (!CollectionUtils.isEmpty(this.subCategories)) {
            this.subCategories.sort(Comparator.comparingInt(CategoryLinks::getOrder));
            for (CategoryLinks categoryLinks : this.subCategories) {
                this.sort(categoryLinks.getLinks());
            }
        }
    }

    /**
     * 对网址集合记性排序.
     *
     * @param links 待排序的网址集合
     */
    private void sort(List<LinkResult> links) {
        if (!CollectionUtils.isEmpty(links)) {
            links.sort(Comparator.comparingInt(LinkResult::getOrder));
        }
    }

}
