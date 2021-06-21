package com.thunisoft.openhub.nav.controller;

import com.thunisoft.openhub.nav.bean.LinkQueryParam;
import com.thunisoft.openhub.nav.consts.Const;
import com.thunisoft.openhub.nav.repository.pojo.Link;
import com.thunisoft.openhub.nav.service.LinkService;
import com.thunisoft.openhub.nav.utils.PageUtils;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
 * 网址链接的控制器类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Const.API_V1 + "/links")
public class LinkController {

    @Resource
    private LinkService linkService;

    /**
     * 分页模糊查询所有网址信息.
     *
     * @param page 当前页，从 0 开始.
     * @param pageSize 每页的分页条数
     * @param name 网址名称.
     * @param url URL 地址
     * @param categoryName 分类名称
     * @return 所有网址信息
     */
    @GetMapping
    public ResponseEntity<Page<Link>> getByPaging(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "url", required = false) String url,
            @RequestParam(name = "categoryName", required = false) String categoryName) {
        LinkQueryParam linkParam = new LinkQueryParam().setCategoryName(categoryName).setName(name).setUrl(url);
        log.info("查询网址信息的相关参数为：【{}】.", linkParam);
        return ResponseEntity.ok(this.linkService.findAll(linkParam,
                PageUtils.buildPageRequest(page, pageSize, Sort.by(Sort.Order.asc("order")))));
    }

    /**
     * 查询出所有的一级网址代码集合信息.
     *
     * @return 网址代码列表
     */
    @GetMapping("/orders/max")
    public ResponseEntity<Integer> getMaxOrder() {
        return ResponseEntity.ok(this.linkService.findMaxOrder());
    }

    /**
     * 保存网址信息.
     *
     * @param link 网址信息实体
     * @return 网址代码列表
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(@RequestBody @Validated Link link) {
        this.linkService.saveOrUpdate(link);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据网址 ID 删除网址.
     *
     * @param id ID
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.linkService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据网址 ID 删除网址.
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public ResponseEntity<Link> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.linkService.findById(id));
    }

}
