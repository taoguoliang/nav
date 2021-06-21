package com.thunisoft.openhub.nav.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 页面工具类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@UtilityClass
public class PageUtils {

    /**
     * 构建分页请求参数 {@link Pageable} 对象.
     *
     * @param page 当前页，从 0 开始
     * @param pageSize 每页条数
     * @return {@link Pageable} 实例
     */
    public Pageable buildPageRequest(Integer page, Integer pageSize) {
        return PageRequest.of(page == null || page < 0 ? 0 : page, pageSize == null || pageSize <= 0 ? 10 : pageSize);
    }

    /**
     * 构建分页请求参数 {@link Pageable} 对象.
     *
     * @param page 当前页，从 0 开始
     * @param pageSize 每页条数
     * @param sort 排序信息
     * @return {@link Pageable} 实例
     */
    public Pageable buildPageRequest(Integer page, Integer pageSize, Sort sort) {
        return PageRequest.of(page == null || page < 0 ? 0 : page,
                pageSize == null || pageSize <= 0 ? 10 : pageSize, sort);
    }

}
