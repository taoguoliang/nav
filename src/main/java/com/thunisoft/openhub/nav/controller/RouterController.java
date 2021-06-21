package com.thunisoft.openhub.nav.controller;

import com.thunisoft.openhub.nav.consts.PageEnum;
import com.thunisoft.openhub.nav.service.CacheService;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面路由的控制器类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Controller
@RequestMapping
public class RouterController {

    @Resource
    private CacheService cacheService;

    /**
     * 返回视图名称.
     *
     * @param modelView modelView
     * @param viewName viewName
     * @return ModelAndView
     */
    private ModelAndView goViewByName(ModelAndView modelView, String viewName) {
        modelView.setViewName(viewName);
        return modelView;
    }

    /**
     * 根路径时路由到 index.html 的首页请求.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView indexDefault(ModelAndView modelView) {
        return this.index(modelView);
    }

    /**
     * index.html 首页请求.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelView) {
        modelView.addObject("categoryLinks", cacheService.get(PageEnum.INDEX.getCode()));
        return this.goViewByName(modelView, "index");
    }

    /**
     * login.html 进入登录页面.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelView) {
        return this.goViewByName(modelView, "login");
    }

    /**
     * dashboard.html 进入仪表盘首页统计页面.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView modelView) {
        return this.goViewByName(modelView, "dashboard");
    }

    /**
     * categories.html 进入分类管理页面.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/categories")
    public ModelAndView categories(ModelAndView modelView) {
        return this.goViewByName(modelView, "category");
    }

    /**
     * links.html 进入网址管理页面.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/links")
    public ModelAndView links(ModelAndView modelView) {
        return this.goViewByName(modelView, "link");
    }

    /**
     * about.html 进入 404 页面.
     *
     * @param modelView 模型视图
     * @return ModelAndView
     */
    @GetMapping("/404")
    public ModelAndView about(ModelAndView modelView) {
        return this.goViewByName(modelView, "404");
    }

}
