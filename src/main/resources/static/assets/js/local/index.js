/**
 * 锚点平滑移动.
 */
var smoothLink = function () {
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
}

/**
 * 初始化时获取当前页的所有分类网址信息.
 */
var getAllCategoryLinks = function () {
    $.ajax({
        type: "GET",
        url: "api/v1/categories/links/index",
        dataType: "json",
        success: function (data) {
            if (data && data.length > 0) {
                var menu = '';
                var $mainMenu = $('#main-menu');
                for (var i = 0, len = data.length; i < len; ++i) {
                    var category = data[i];
                    var subCategories = category.subCategories;
                    if (subCategories && subCategories.length > 0) {
                        menu += '<li><a><i class="' + category.icon + '"></i>' +
                            '<span class="title">' + category.name + '</span></a><ul>';
                        for (var j = 0; j < subCategories.length; ++j) {
                            var subCategory = subCategories[j];
                            menu += '<li><a href="#' + subCategory.name + '" class="smooth"><span class="title">' +
                                subCategory.name + '</span></a></li>';
                        }
                        menu += '</ul></li>';
                    } else {
                        menu += '<li><a href="#' + category.name + '" class="smooth"><i class="' + category.icon + '"></i>' +
                            '<span class="title">' + category.name + '</span></a></li>';
                    }
                }
                $mainMenu.prepend(menu);
            }
        },
        complete: function () {
            // 完成之后，使链接可以平滑移动.
            smoothLink();
        }
    });
}

$(function () {
    getAllCategoryLinks();
});
