var categoryTable = $('#category-table').dataTable({
    bAutoWidth: false,
    pageLength: 10,
    bStateSave: false,
    bSort: false,
    bFilter: false,
    processing: true,
    serverSide: true,
    ajax: function (data, callback, settings) {
        $.ajax({
            type: "GET",
            url: "api/v1/categories",
            cache: false,
            data: {
                name: $('#search-catetory-name').val(),
                pageSize: data.length,
                page: data.start / data.length
            },
            dataType: "json",
            success: function (result) {
                callback({
                    recordsTotal: result.totalElements,
                    recordsFiltered: result.totalElements,
                    data: result.content
                });
            }
        });
    },
    columns: [
        {
            render: function (data, type, full) {
                return '';
            }
        },
        {
            render: function (data, type, row) {
                return '<i class="' + row.icon + '"></i> ' + row.name;
            }
        },
        {
            render: function (data, type, row) {
                return row.page === 'internet' ? '互联网' : '首页';
            }
        },
        {data: "updateTime"},
        {data: "order"},
        {
            render: function (data, type, row) {
                return '<a href="javascript:void(0);" class="btn btn-secondary btn-sm btn-icon icon-left" ' +
                    'onclick="editCategory(\'' + row.id + '\');">编辑</a>' +
                    '<a href="javascript:void(0);" class="btn btn-danger btn-sm btn-icon icon-left" ' +
                    'onclick="deleteCategory(\'' + row.id + '\');">删除</a>';
            }
        }
    ],
    fnDrawCallback: function () {
        var api = this.api();
        //var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
        api.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    },
    language: {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
});

/**
 * 显示提示信息.
 *
 * @param msg 消息
 */
var toastErrorMsg = function(msg) {
    toastr.error(msg, {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-full-width",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    });
};

$('#search-btn').on('click', function () {
    categoryTable.fnDraw();
});

// 监听分类新增按钮的点击事件.
$('#new-category-btn').on('click', function () {
    // 初始化清空表单.
    $('#categoryId').val('');
    $('#name').val('');
    $('#icon').val('linecons-globe');
    $('#par-cate-select').val('');
    $('#order').val('');
    $('#preview-icon').removeClass().addClass($('#icon').val());

    $('#new-category-modal').modal();
    getFirstLevelCategories();
    getAndSetMaxOrder();
});

/**
 * 编辑分类.
 */
var editCategory = function (id) {
    $.ajax({
        type: "GET",
        url: "api/v1/categories/" + id,
        dataType: "json",
        success: function (data) {
            if (data) {
                $('#categoryId').val(data.id);
                $('#name').val(data.name);
                $('#icon').val(data.icon);
                $('#page').val(data.page);
                $('#order').val(data.order);
                $('#preview-icon').removeClass().addClass(data.icon)
            }
            getFirstLevelCategories(data.parentId);
            $('#new-category-modal').modal();
        },
        error: function () {
            toastErrorMsg('该分类已不存在!');
        }
    });
}

// 保存分类信息.
$('#save-category-btn').on('click', function () {
    var params = {
        id: $('#categoryId').val(),
        name: $('#name').val(),
        icon: $('#icon').val(),
        parentId: $("#par-cate-select option:selected").val(),
        page: $('#page').val(),
        order: $('#order').val()
    };

    $.ajax({
        type: "POST",
        url: "api/v1/categories",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(params),
        success: function (data) {
            $('#new-category-modal').modal('hide');
            categoryTable.fnDraw();
        },
        error: function () {
            toastErrorMsg('保存失败!');
        }
    });
});

/**
 * 删除分类.
 */
var deleteCategory = function (id) {
    $('#delete-category-id').val(id);
    $('#delete-category-modal').modal();
}

// 监听删除分类的方法
$('#do-delete-btn').on('click', function () {
    $.ajax({
        type: "DELETE",
        url: "api/v1/categories/" + $('#delete-category-id').val(),
        success: function (data) {
            categoryTable.fnDraw();
            $('#delete-category-id').val('');
            $('#delete-category-modal').modal('hide');
        },
        error: function () {
            $('#delete-category-id').val('');
            toastErrorMsg('删除分类失败!');
        }
    });
});

/**
 * 获取页面的单值代码信息.
 */
var getFirstLevelCategories = function (selectedvalue) {
    $.ajax({
        type: "GET",
        url: "api/v1/categories/codes/levels/1",
        dataType: "json",
        success: function (data) {
            if (data) {
                var parSelect = $('#par-cate-select');
                parSelect.html('');
                if (!selectedvalue) {
                    parSelect.append('<option value="" selected>无</option>');
                }

                for (var i = 0; i < data.length; ++i) {
                    if (data[i].value === selectedvalue) {
                        parSelect.append('<option value="' + data[i].value + '" selected>' + data[i].name + '</option>');
                    } else {
                        parSelect.append('<option value="' + data[i].value + '">' + data[i].name + '</option>');
                    }
                }
            }
        }
    });
}

/**
 * 获取并设置当前分类的最大序号.
 */
var getAndSetMaxOrder = function () {
    $.ajax({
        type: "GET",
        url: "api/v1/categories/orders/max",
        success: function (data) {
            $('#order').val(data + 1);
        }
    });
}

// 监听图标 icon 的值，来实时显示图标.
$('#icon').bind('input propertychange change', function() {
    var iconValue = $.trim($(this).val());
    if (iconValue) {
        $('#preview-icon').removeClass().addClass(iconValue);
    }
});

/**
 * 监听查询框的 enter 事件.
 */
$('#search-form input').keydown(function (event) {
    if (event.keyCode === 13) {
        categoryTable.fnDraw();
    }
});
