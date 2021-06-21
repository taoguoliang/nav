package com.thunisoft.openhub.nav.service;

import com.thunisoft.openhub.nav.bean.CategoryLinks;
import com.thunisoft.openhub.nav.consts.PageEnum;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 缓存服务类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Service
public class CacheService {

    @Resource
    private LinkService linkService;

    /**
     * 用于缓存首页分类网址信息的缓存 Map，其中 key 为 {@link PageEnum#getCode()} 值，value 为网址分类及网址信息的队列.
     */
    private static final Map<String, List<CategoryLinks>> cache = new ConcurrentHashMap<>(16);

    /**
     * 从缓存中获取某个页面中的网址分类及网址信息.
     *
     * @param page 页面
     * @return 网址分类及网址信息的有序集合.
     */
    public List<CategoryLinks> get(String page) {
        // 如果传入的页面不存在，就直接返回空数据.
        PageEnum pageEnum = PageEnum.ofNullable(page);
        if (pageEnum == null) {
            return Collections.emptyList();
        }

        // 从缓存中取数据，如果不存在，就重新从数据库中查询并存入缓存.
        List<CategoryLinks> categoryLinksList = cache.get(page);
        return categoryLinksList != null ? categoryLinksList : this.findAndUpdateCache(page);
    }

    /**
     * 查询并更新缓存中的数据，加了同步处理，保证只有一个线程能更新数据.
     *
     * @param page 页面
     * @return 网址分类及网址信息的队列.
     */
    private List<CategoryLinks> findAndUpdateCache(String page) {
        List<CategoryLinks> categoryLinksList = this.linkService.findLinksByPage(page);
        cache.put(page, categoryLinksList);
        return categoryLinksList;
    }

    /**
     * 移除指定页面中的缓存的网址分类及网址信息数据.
     *
     * @param page 页面
     */
    public void removeCache(String page) {
        cache.remove(page);
    }

    /**
     * 清空所有缓存数据.
     */
    public void clear() {
        cache.clear();
    }

}
