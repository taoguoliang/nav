package com.thunisoft.openhub.nav.service;

import com.thunisoft.openhub.nav.bean.Code;
import com.thunisoft.openhub.nav.consts.PageEnum;
import com.thunisoft.openhub.nav.exception.RunException;
import com.thunisoft.openhub.nav.repository.CategoryRepository;
import com.thunisoft.openhub.nav.repository.pojo.Category;
import com.thunisoft.openhub.nav.utils.StrUtils;
import java.util.ArrayList;
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
 * 网址分类服务.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@Service
public class CategoryService {

    @Resource
    private CacheService cacheService;

    @Resource
    private CategoryRepository categoryRepository;

    /**
     * 分页查询所有分类信息.
     *
     * @param categoryName 分类名称
     * @param pageable 分页信息
     * @return 分类信息的分页查询结果
     */
    public Page<Category> findAll(String categoryName, Pageable pageable) {
        return this.categoryRepository.findByPaging(categoryName, pageable);
    }

    /**
     * 保存分类的信息.
     *
     * @param category 分类信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Category category) {
        // 对名称进行去空格操作，如果有空格的话，在 HTML 的锚点进行跳转时会有问题.
        String name = category.getName().trim().replace(" ", "");

        // 如果 ID 为空，就新增分类信息.
        String id = category.getId();
        if (StringUtils.isBlank(id)) {
            Date date = new Date();
            int order = category.getOrder();
            this.categoryRepository.save(category.setId(StrUtils.getShortId())
                    .setParentId(StringUtils.isBlank(category.getParentId()) ? null : category.getParentId())
                    .setName(name)
                    .setPage(PageEnum.of(category.getPage()).getCode())
                    .setOrder(order <= 0 ? this.findMaxOrder() + 1 : order)
                    .setCreateTime(date)
                    .setUpdateTime(date));
            this.cacheService.removeCache(category.getPage());
            log.info("【新增分类 -> 完成】保存新的网址分类信息完成.");
            return;
        }

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new RunException("【更新分类 -> 异常】需要更新的分类信息不存在！");
        }

        // 如果原来的数据存在，就更新分类信息.
        Category oldCategory = categoryOptional.get();
        String parentId = category.getParentId();
        oldCategory.setParentId(StringUtils.isBlank(parentId) ? null : parentId)
                .setName(name)
                .setPage(PageEnum.of(category.getPage()).getCode())
                .setIcon(category.getIcon())
                .setOrder(category.getOrder())
                .setUpdateTime(new Date());
        categoryRepository.save(oldCategory);
        this.cacheService.removeCache(category.getPage());
        log.info("【更新分类 -> 完成】更新网址分类信息完成.");
    }

    /**
     * 获取当前最大的序号.
     *
     * @return 序号
     */
    public int findMaxOrder() {
        Integer order = categoryRepository.findMaxOrder();
        return order == null ? 0 : order;
    }

    /**
     * 查询出所有分类代码集合信息.
     *
     * @return 一级分类代码的集合
     */
    public List<Code> findAllCategoryCodes() {
        return this.buildCategoryCodes(categoryRepository.findAll());
    }

    /**
     * 查询出所有的一级分类代码集合信息.
     *
     * @return 一级分类代码的集合
     */
    public List<Code> findFirstLevelCategoryCodes() {
        return this.buildCategoryCodes(categoryRepository.findFirstLevelCategories());
    }

    /**
     * 查询出所有一级分类或二级分类集合信息，其中这一级分类不能再有二级分类.
     *
     * @return 一级分类或二级分类集合
     */
    public List<Code> findFirstOrSecondLevelCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        if (CollectionUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }

        // 将所有分类 ID 及对应的信息存入到 Map 中.
        Map<String, Category> map = new HashMap<>(categories.size());
        for (Category category : categories) {
            map.put(category.getId(), category);
        }

        // 将 父ID 不为空所对应的父分类从 Map 中移除.
        for (Category category : categories) {
            String parentId = category.getParentId();
            if (StringUtils.isNotBlank(parentId)) {
                map.remove(parentId);
            }
        }
        return this.buildCategoryCodes(new ArrayList<>(map.values()));
    }

    private List<Code> buildCategoryCodes(List<Category> categories) {
        List<Code> codes = new ArrayList<>();
        for (Category category : categories) {
            codes.add(new Code(category.getId(), category.getName()));
        }
        return codes;
    }

    /**
     * 根据分类 ID 删除分类.
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        this.categoryRepository.deleteById(id);
        this.cacheService.clear();
    }

    /**
     * 根据分类 ID 查询分类.
     *
     * @param id ID
     * @return 分类信息
     */
    public Category findById(String id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new RunException("ID 为【{}】的分类信息不存在！", id));
    }

}
