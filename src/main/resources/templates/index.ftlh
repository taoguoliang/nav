<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="author" content="blinkfox"/>
    <title>CXY网址导航</title>
    <meta name="keywords" content="CXY网址导航">
    <meta name="description" content="CXY网址导航">

    <link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
    <link rel="stylesheet" href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.css">
    <link rel="stylesheet" href="assets/css/xenon-core.css">
    <link rel="stylesheet" href="assets/css/xenon-components.css">
    <link rel="stylesheet" href="assets/css/xenon-skins.css">
    <link rel="stylesheet" href="assets/css/nav.css">
    <script src="assets/js/jquery-1.11.1.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="page-body">
<!-- 点击爆炸效果. -->
<div id="clickCanvas" style="position:fixed; left:0; top:0; z-index:999999999; pointer-events:none;"></div>

<div class="page-container">
    <div class="sidebar-menu toggle-others fixed">
        <div class="sidebar-menu-inner">
            <#include "commons/logo.ftlh"/>
            <ul id="main-menu" class="main-menu">
                <#list categoryLinks as category>
                    <#if category.subCategories?? && (category.subCategories?size > 0)>
                        <li>
                            <a>
                                <i class="${category.icon}"></i>
                                <span class="title">${category.name}</span>
                            </a>
                            <ul>
                                <#list category.subCategories as subCategory>
                                    <li>
                                        <a href="#${subCategory.name}" class="smooth">
                                            <span class="title">${subCategory.name}</span>
                                        </a>
                                    </li>
                                </#list>
                            </ul>
                        </li>
                    <#else>
                        <li>
                            <a href="#${category.name}" class="smooth">
                                <i class="${category.icon}"></i>
                                <span class="title">${category.name}</span>
                            </a>
                        </li>
                    </#if>
                </#list>
                <li>
                    <a href="#关于本站" class="smooth">
                        <i class="linecons-heart"></i>
                        <span class="tooltip-blue">关于本站</span>
                        <span class="label label-Primary pull-right hidden-collapsed">♥︎</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-content">
        <nav class="navbar user-info-navbar" role="navigation">
            <ul class="user-info-menu left-links list-inline list-unstyled">
                <li class="hidden-sm hidden-xs">
                    <a href="#" data-toggle="sidebar">
                        <i class="fa-bars"></i>
                    </a>
                </li>
                <li class="dropdown hover-line language-switcher">
                    <iframe src="https://tianqiapi.com/api.php?style=tm&skin=pitaya" style="margin-top: 30px"
                            scrolling="no" frameborder="0" width="270" height="24" allowtransparency="true"></iframe>
                </li>
            </ul>
        </nav>

        <#-- 搜索模块. -->
        <#include "commons/search.ftlh"/>

        <#-- 渲染生成各个分类下的网址. -->
        <#list categoryLinks as category>
            <#if category.subCategories?? && (category.subCategories?size > 0)>
            <#-- 渲染该分类下的子分类下的网址. -->
                <#list category.subCategories as subCategory>
                    <h4 class="text-gray category-title">
                        <i class="${subCategory.icon}" style="margin-right: 7px;" id="${subCategory.name}"></i>${subCategory.name}
                    </h4>
                    <#if subCategory.links?? && (subCategory.links?size > 0)>
                        <div class="row">
                            <#list subCategory.links as link>
                                <div class="col-sm-3">
                                    <div class="xe-widget xe-conversations box2 label-info"
                                         onclick="window.open('${link.url}', '_blank')"
                                         data-toggle="tooltip" data-placement="bottom" title="" data-html="true"
                                         data-original-title="${link.url} <br/>${link.description}">
                                        <div class="xe-comment-entry">
                                            <a class="xe-user-img"><img data-src="${link.logo}" class="lozad img-circle"
                                                                        width="40"></a>
                                            <div class="xe-comment">
                                                <a href="#"
                                                   class="xe-user-name overflowClip_1"><strong>${link.name}</strong></a>
                                                <p class="overflowClip_2">${link.description}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </#if>
                </#list>
            <#else>
                <h4 class="text-gray category-title">
                    <i class="${category.icon}" style="margin-right: 7px;" id="${category.name}"></i>${category.name}
                </h4>
                <#if category.links?? && (category.links?size > 0)>
                    <div class="row">
                        <#list category.links as link>
                            <div class="col-sm-3">
                                <div class="xe-widget xe-conversations box2 label-info"
                                     onclick="window.open('${link.url}', '_blank')"
                                     data-toggle="tooltip" data-placement="bottom" title="" data-html="true"
                                     data-original-title="${link.url} <br/>${link.description}">
                                    <div class="xe-comment-entry">
                                        <a class="xe-user-img">
                                            <img data-src="${link.logo}" class="lozad img-circle" width="40">
                                        </a>
                                        <div class="xe-comment">
                                            <a href="#" class="xe-user-name overflowClip_1">
                                                <strong>${link.name}</strong>
                                            </a>
                                            <p class="overflowClip_2">${link.description}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </#if>
            </#if>
        </#list>

        <h4 class="text-gray category-title">
            <i class="linecons-heart" style="margin-right: 7px;" id="关于本站"></i>关于本站
        </h4>
        <div class="row">
            <div class="col-md-6">
                <div class="xe-widget xe-conversations box2 label-info"
                     onclick="window.open('http://nav.taoguoliang.cn', '_blank')"
                     data-toggle="tooltip" data-placement="bottom" title="" data-html="true"
                     data-original-title="http://nav.taoguoliang.cn <br/>方便找网站~">
                    <div class="xe-comment-entry">
                        <a class="xe-user-img">
                            <img data-src="assets/images/logo.png" class="lozad img-circle" width="40">
                        </a>
                        <div class="xe-comment">
                            <a href="#" class="xe-user-name overflowClip_1">
                                <strong>CXY网址导航 (nav.taoguoliang.cn)</strong>
                            </a>
                            <p class="overflowClip_2">方便找网站~</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="xe-widget xe-conversations box2 label-info"
                     onclick="window.open('https://github.com/taoguoliang/issues', '_blank')"
                     data-toggle="tooltip" data-placement="bottom" title="" data-html="true"
                     data-original-title="https://github.com/taoguoliang/issues <br/>
                     如果你有关于本站的建议反馈或者想添加你需要的链接，请前往本项目 GitLab 议题（issue）页提出你的反馈或建议。">
                    <div class="xe-comment-entry">
                        <a class="xe-user-img">
                            <img data-src="assets/images/logos/issue.png" class="lozad img-circle" width="40">
                        </a>
                        <div class="xe-comment">
                            <a href="#" class="xe-user-name overflowClip_1">
                                <strong>建议反馈</strong>
                            </a>
                            <p class="overflowClip_2">如果你有关于本站的建议反馈或者想添加你需要的链接，请前往本项目 GitLab 议题（issue）页提出你的反馈或建议。</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页脚. -->
        <#include "commons/footer.ftlh"/>
    </div>
</div>

<!-- 锚点平滑移动 -->
<script type="text/javascript">
    $(document).ready(function () {
        // 针对 IE 11 做兼容.
        if (typeof Object.assign != 'function') {
            Object.assign = function(target) {
                'use strict';
                if (target == null) {
                    throw new TypeError('Cannot convert undefined or null to object');
                }

                target = Object(target);
                for (var index = 1; index < arguments.length; index++) {
                    var source = arguments[index];
                    if (source != null) {
                        for (var key in source) {
                            if (Object.prototype.hasOwnProperty.call(source, key)) {
                                target[key] = source[key];
                            }
                        }
                    }
                }
                return target;
            };
        }

        //img lazy loaded
        var observer = lozad();
        observer.observe();

        $(document).on('click', '.has-sub', function () {
            var _this = $(this)
            if (!$(this).hasClass('expanded')) {
                setTimeout(function () {
                    _this.find('ul').attr("style", "")
                }, 300);

            } else {
                $('.has-sub ul').each(function (id, ele) {
                    var _that = $(this)
                    if (_this.find('ul')[0] != ele) {
                        setTimeout(function () {
                            _that.attr("style", "")
                        }, 300);
                    }
                })
            }
        })
        $('.user-info-menu .hidden-sm').click(function () {
            if ($('.sidebar-menu').hasClass('collapsed')) {
                $('.has-sub.expanded > ul').attr("style", "")
            } else {
                $('.has-sub.expanded > ul').show()
            }
        })
        $("#main-menu li ul li").click(function () {
            $(this).siblings('li').removeClass('active'); // 删除其他兄弟元素的样式
            $(this).addClass('active'); // 添加当前元素的样式
        });
        $("a.smooth").click(function (ev) {
            ev.preventDefault();

            public_vars.$mainMenu.add(public_vars.$sidebarProfile).toggleClass('mobile-is-visible');
            ps_destroy();
            $("html, body").animate({
                scrollTop: $($(this).attr("href")).offset().top - 30
            }, {
                duration: 500,
                easing: "swing"
            });
        });
        return false;
    });

    var href = "";
    var pos = 0;
    $("a.smooth").click(function (e) {
        $("#main-menu li").each(function () {
            $(this).removeClass("active");
        });
        $(this).parent("li").addClass("active");
        e.preventDefault();
        href = $(this).attr("href");
        pos = $(href).position().top - 30;
    });
</script>

<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/TweenMax.min.js"></script>
<script src="assets/js/resizeable.js"></script>
<script src="assets/js/joinable.js"></script>
<script src="assets/js/xenon-api.js"></script>
<script src="assets/js/xenon-toggles.js"></script>
<script src="assets/js/xenon-custom.js"></script>
<script src="assets/js/lozad.js"></script>
<script src="assets/js/click-canvas.js"></script>
</body>

</html>
