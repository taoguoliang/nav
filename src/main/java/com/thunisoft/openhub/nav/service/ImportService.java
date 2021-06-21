package com.thunisoft.openhub.nav.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thunisoft.openhub.nav.bean.CategoryInfo;
import com.thunisoft.openhub.nav.repository.CategoryRepository;
import com.thunisoft.openhub.nav.repository.LinkRepository;
import com.thunisoft.openhub.nav.repository.pojo.Category;
import com.thunisoft.openhub.nav.repository.pojo.Link;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 网址导航相关的 JSON 数据导入服务.
 *
 * @author chenjiayin on 2020-08-03.
 * @since v1.0.0
 */
@Slf4j
@Service
public class ImportService {

    private static final String CATEGORY_INDEX = "/data/category-index.json";

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private LinkRepository linkRepository;

    /**
     * 开始导入.
     */
    @Transactional(rollbackFor = Exception.class)
    public void doImport() {
        log.info("【导入数据 -> 开始】开始从 JSON 配置文件中导入网址分类和网址信息数据 ...");
        final long start = System.currentTimeMillis();

        // 从 JSON 文件中加载读取数据为实体类集合信息.
        List<CategoryInfo> categoryInfos;
        try {
            categoryInfos = new ObjectMapper().readValue(CategoryService.class.getResourceAsStream(CATEGORY_INDEX),
                    new TypeReference<List<CategoryInfo>>() {});
        } catch (IOException e) {
            log.error("【导入数据 -> 失败】从 JSON 配置文件中导入网址分类和网址信息数据失败，请检查！", e);
            return;
        }

        // 如果网址分类集合信息为空，则直接返回.
        if (CollectionUtils.isEmpty(categoryInfos)) {
            log.warn("【导入数据 -> 为空】从 JSON 配置文件中读取的网址分类和网址信息数据为空，将跳过数据导入环节！");
            return;
        }

        // 遍历相关的集合信息，将需要保持或更新的的网址分类或链接信息保存到集合中，统计保存.
        List<Category> allCategories = new ArrayList<>();
        List<Link> allLinks = new ArrayList<>();
        for (CategoryInfo categoryInfo : categoryInfos) {
            allCategories.add(categoryInfo.newCategory(null));
            allLinks.addAll(categoryInfo.buildAndGetCategoryLinks());

            // 如果子分类不为空，就继续遍历保存子分类中的网址信息.
            List<CategoryInfo> subCategories = categoryInfo.getSubCategories();
            if (!CollectionUtils.isEmpty(subCategories)) {
                for (CategoryInfo subCategoryInfo : subCategories) {
                    allCategories.add(subCategoryInfo.newCategory(categoryInfo.getId()));
                    allLinks.addAll(subCategoryInfo.buildAndGetCategoryLinks());
                }
            }
        }

        // 批量保存网址分类和网址信息.
        this.categoryRepository.saveAll(allCategories);
        this.linkRepository.saveAll(allLinks);
        log.warn("【导入数据 -> 完成】从 JSON 配置文件中导入网址分类和网址信息数据完成，耗时：【{} ms】.", System.currentTimeMillis() - start);
    }

}
