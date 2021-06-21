package com.thunisoft.openhub.nav.service;

import com.thunisoft.openhub.nav.bean.CategoryLinks;
import com.thunisoft.openhub.nav.bean.LinkQueryParam;
import com.thunisoft.openhub.nav.bean.LinkResult;
import com.thunisoft.openhub.nav.exception.RunException;
import com.thunisoft.openhub.nav.repository.CategoryRepository;
import com.thunisoft.openhub.nav.repository.LinkRepository;
import com.thunisoft.openhub.nav.repository.pojo.Category;
import com.thunisoft.openhub.nav.repository.pojo.Link;
import com.thunisoft.openhub.nav.utils.StrUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 链接服务类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@Service
public class LinkService {

    @Resource
    private CacheService cacheService;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private LinkRepository linkRepository;

    /**
     * 分页查询所有网址信息.
     *
     * @param linkParam 网址查询参数
     * @param pageable 分页信息
     * @return 网址信息的分页查询结果
     */
    public Page<Link> findAll(LinkQueryParam linkParam, Pageable pageable) {
        return this.linkRepository.findByPaging(linkParam, pageable);
    }

    /**
     * 保存分类的信息.
     *
     * @param link 网址信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Link link) {
        String categoryId = link.getCategoryId();
        if (StringUtils.isBlank(categoryId)) {
            throw new RunException("要保存的网址信息没有填写所属的分类信息!");
        }

        // 获取分类信息.
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RunException("要保存的网址信息所属的分类信息不存在!"));

        // 如果 ID 为空，就新增.
        String id = link.getId();
        if (StringUtils.isBlank(id)) {
            Date date = new Date();
            int order = link.getOrder();
            linkRepository.save(link
                    .setId(StrUtils.getShortId())
                    .setCategoryName(category.getName())
                    .setOrder(order <= 0 ? this.findMaxOrder() + 1 : order)
                    .setCreateTime(date)
                    .setUpdateTime(date));
            this.cacheService.removeCache(category.getPage());
            log.info("【新增网址 -> 完成】保存新的网址信息完成.");
            return;
        }

        Optional<Link> linkOptional = linkRepository.findById(id);
        if (!linkOptional.isPresent()) {
            throw new RunException("【更新网址 -> 异常】需要更新的网址信息不存在！");
        }

        // 更新网址信息.
        linkRepository.save(linkOptional.get()
                .setCategoryId(categoryId)
                .setCategoryName(category.getName())
                .setName(link.getName())
                .setLogo(link.getLogo())
                .setDescription(link.getDescription())
                .setUrl(link.getUrl())
                .setRead(link.getRead())
                .setStars(link.getStars())
                .setOrder(link.getOrder())
                .setUpdateTime(new Date()));
        this.cacheService.removeCache(category.getPage());
        log.info("【更新网址 -> 完成】更新网址信息完成.");
    }

    /**
     * 获取当前最大的顺序号.
     *
     * @return 顺序号
     */
    public int findMaxOrder() {
        Integer maxOrder = linkRepository.findMaxOrder();
        return maxOrder == null ? 0 : maxOrder;
    }

    /**
     * 根据网址 ID 删除网址信息.
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        this.linkRepository.deleteById(id);
        this.cacheService.clear();
    }

    /**
     * 根据网址 ID 查询网址信息.
     *
     * @param id ID
     * @return 网址信息
     */
    public Link findById(String id) {
        return this.linkRepository.findById(id).orElseThrow(() -> new RunException("ID 为【{}】的网址信息不存在！", id));
    }

    /**
     * 根据页面查询该页面中的所有网址及分类信息.
     *
     * @param page 所属页面
     * @return 某页面中所有网址信息
     */
    public List<CategoryLinks> findLinksByPage(String page) {
        // 如果对应的页面分类网址信息为空，就直接返回.
        List<Category> categories = categoryRepository.findByPage(page);
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }

        // 根据网址分类信息得到其所有 ID 和对应的子分类结果信息.
        final List<String> categoryIds = new ArrayList<>();
        final Map<String, CategoryLinks> categoryMap = new HashMap<>();
        for (Category category : categories) {
            String categoryId = category.getId();
            categoryIds.add(categoryId);
            categoryMap.put(categoryId, new CategoryLinks()
                    .setId(categoryId)
                    .setName(category.getName())
                    .setIcon(category.getIcon())
                    .setOrder(category.getOrder()));
        }

        // 遍历所有网址链接，将他们添加到对应的分类下.
        this.buildCategoryLinks(categoryIds, categoryMap);

        // 再次循环构建出所有分类下的子分类信息，并构建出最终的所有一级分类网址信息.
        this.buildSubCategories(categories, categoryMap);

        // 对所有分类、子分类和各分类下的网址集合信息进行排序.
        List<CategoryLinks> categoryLinksList = new ArrayList<>(categoryMap.values());
        categoryLinksList.sort(Comparator.comparingInt(CategoryLinks::getOrder));
        for (CategoryLinks categoryLinks : categoryLinksList) {
            categoryLinks.sort();
        }
        return categoryLinksList;
    }

    /**
     * 查询并构建出每个分类下所有的网址信息.
     *
     * @param categoryIds 所有分类 ID 集合
     * @param categoryMap 分类的对象关系 Map，key 为分类 ID，value 为 {@link CategoryLinks} 实例
     */
    private void buildCategoryLinks(List<String> categoryIds, Map<String, CategoryLinks> categoryMap) {
        List<Link> allLinks = this.linkRepository.findByCategoryIds(categoryIds);
        for (Link link : allLinks) {
            String categoryId = link.getCategoryId();
            CategoryLinks categoryLinks = categoryMap.get(categoryId);
            if (categoryLinks == null) {
                continue;
            }

            List<LinkResult> links = categoryLinks.getLinks();
            links = links == null ? new ArrayList<>() : links;
            links.add(new LinkResult()
                    .setName(link.getName())
                    .setDescription(link.getDescription())
                    .setLogo(link.getLogo())
                    .setUrl(link.getUrl())
                    .setOrder(link.getOrder())
                    .setRead(link.getRead())
                    .setStars(link.getStars()));
            categoryLinks.setLinks(links);
        }
    }

    /**
     * 根据所有分类信息，构建出一级分类网址信息，将二级分类作为子集合属性.
     *
     * @param categories 所有分类网址信息
     * @param categoryMap 分类的对象关系 Map，key 为分类 ID，value 为 {@link CategoryLinks} 实例
     */
    private void buildSubCategories(List<Category> categories, Map<String, CategoryLinks> categoryMap) {
        for (Category category : categories) {
            String parentId = category.getParentId();
            if (StringUtils.isNotBlank(parentId)) {
                CategoryLinks categoryLinks = categoryMap.get(parentId);
                if (categoryLinks == null) {
                    continue;
                }

                // 将子分类添加到其对应父分类的集合中，并最后把自己从一级分类中移除.
                List<CategoryLinks> subCategories = categoryLinks.getSubCategories();
                subCategories = subCategories == null ? new ArrayList<>() : subCategories;
                subCategories.add(categoryMap.get(category.getId()));
                categoryLinks.setSubCategories(subCategories);
                categoryMap.remove(category.getId());
            }
        }
    }

}
