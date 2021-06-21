package com.thunisoft.openhub.nav.controller;

import com.thunisoft.openhub.nav.bean.Code;
import com.thunisoft.openhub.nav.consts.Const;
import com.thunisoft.openhub.nav.repository.pojo.Category;
import com.thunisoft.openhub.nav.service.CategoryService;
import com.thunisoft.openhub.nav.utils.PageUtils;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网址分类相关操作的控制器接口.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@CrossOrigin
@RestController
@RequestMapping(Const.API_V1 + "/categories")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 分页模糊查询所有分类信息.
     *
     * @param page 当前页，从 0 开始.
     * @param pageSize 每页的分页条数
     * @param name 分类名称.
     * @return 所有分类信息
     */
    @GetMapping
    public ResponseEntity<Page<Category>> getByPaging(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(this.categoryService.findAll(name,
                PageUtils.buildPageRequest(page, pageSize, Sort.by(Sort.Order.asc("order")))));
    }

    /**
     * 查询出所有的一级分类代码集合信息.
     *
     * @return 分类代码列表
     */
    @GetMapping("/codes/levels/1")
    public ResponseEntity<List<Code>> getFirstLevelCategories() {
        return ResponseEntity.ok(this.categoryService.findFirstLevelCategoryCodes());
    }

    /**
     * 查询出所有一级分类或二级分类集合信息，其中这一级分类不能再有二级分类.
     *
     * @return 分类代码列表
     */
    @GetMapping("/codes/levels/1/2")
    public ResponseEntity<List<Code>> getFirstOrSecondLevelCategories() {
        return ResponseEntity.ok(this.categoryService.findFirstOrSecondLevelCategories());
    }

    /**
     * 查询出所有分类代码集合信息.
     *
     * @return 分类代码列表
     */
    @GetMapping("/codes")
    public ResponseEntity<List<Code>> getAllCategoryCodes() {
        return ResponseEntity.ok(this.categoryService.findAllCategoryCodes());
    }

    /**
     * 查询出所有的一级分类代码集合信息.
     *
     * @return 分类代码列表
     */
    @GetMapping("/orders/max")
    public ResponseEntity<Integer> getMaxOrder() {
        return ResponseEntity.ok(this.categoryService.findMaxOrder());
    }

    /**
     * 查询出所有的一级分类代码集合信息.
     *
     * @param category 分类实体
     * @return 分类代码列表
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(@RequestBody @Validated Category category) {
        this.categoryService.saveOrUpdate(category);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类 ID 删除分类.
     *
     * @param id ID
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类 ID 删除分类.
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

}
