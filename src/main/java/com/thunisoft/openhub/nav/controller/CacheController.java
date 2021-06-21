package com.thunisoft.openhub.nav.controller;

import com.thunisoft.openhub.nav.bean.CategoryLinks;
import com.thunisoft.openhub.nav.consts.Const;
import com.thunisoft.openhub.nav.consts.PageEnum;
import com.thunisoft.openhub.nav.service.CacheService;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网址导航相关信息的缓存控制器类.
 *
 * @author blinkfox on 2020-08-03.
 * @since v1.0.0
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(Const.API_V1 + "/caches")
public class CacheController {

    @Resource
    private CacheService cacheService;

    /**
     * 刷新所有页面的网址信息.
     *
     * @return 网址代码列表
     */
    @GetMapping("/actions/refresh")
    public ResponseEntity<String> refresh() {
        for (PageEnum pageEnum : PageEnum.values()) {
            this.cacheService.removeCache(pageEnum.getCode());
            this.cacheService.get(pageEnum.getCode());
        }
        return ResponseEntity.ok("刷新所有页面的缓存成功.");
    }

    /**
     * 根据页面刷新该页面的网址信息，并返回对应的集合信息.
     *
     * @param page 页面
     * @return 分类和网址的集合信息
     */
    @GetMapping("/{page}/actions/refresh")
    public ResponseEntity<List<CategoryLinks>> refreshByPage(@PathVariable("page") String page) {
        this.cacheService.removeCache(page);
        return ResponseEntity.ok(this.cacheService.get(page));
    }

}
