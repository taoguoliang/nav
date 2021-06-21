var linkTable = $('#link-table').dataTable({
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
            url: "api/v1/links",
            cache: false,
            data: {
                categoryName: $('#search-catetory-name').val(),
                name: $('#search-link-name').val(),
                url: $('#search-url').val(),
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
        {data: "categoryName"},
        {
            render: function (data, type, row) {
                return '<img src="' + row.logo + '" alt="无" class="td-img-circle" /> ' + row.name;
            }
        },
        {
            render: function (data, type, row) {
                return '<a href="' + row.url + '" target="_blank" style="color: #3d7eff">' + row.url + '</a>';
            }
        },
        {data: "updateTime"},
        {data: "order"},
        {
            render: function (data, type, row) {
                return '<a href="javascript:void(0);" class="btn btn-secondary btn-sm btn-icon icon-left" ' +
                    'onclick="editLink(\'' + row.id + '\');">编辑</a>' +
                    '<a href="javascript:void(0);" class="btn btn-danger btn-sm btn-icon icon-left" ' +
                    'onclick="deleteLink(\'' + row.id + '\');">删除</a>';
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
    linkTable.fnDraw();
});

// 监听分类新增按钮的点击事件.
$('#new-link-btn').on('click', function () {
    $('#linkId').val('');
    $('#category-select').val('');
    $('#name').val('');
    $('#url').val('');
    $('#description').val('');
    $('#order').val('');
    $('#preview-logo').attr('src', 'assets/images/logos/sketchcasts.png');

    $('#new-link-modal').modal();
    getFirstOrSecondLevelCategories();
    getAndSetMaxOrder();
});

/**
 * 编辑分类.
 */
var editLink = function (id) {
    $.ajax({
        type: "GET",
        url: "api/v1/links/" + id,
        dataType: "json",
        success: function (data) {
            if (data) {
                $('#linkId').val(data.id);
                $('#name').val(data.name);
                $('#url').val(data.url);
                $('#logo').val(data.logo);
                $('#description').val(data.description);
                $('#order').val(data.order);
                $('#preview-logo').attr('src', data.logo);
            }

            // 显示编辑框.
            getFirstOrSecondLevelCategories(data.categoryId);
            $('#new-link-modal').modal();
        },
        error: function () {
            toastErrorMsg('该分类已不存在!');
        }
    });
}

// 保存分类信息.
$('#save-link-btn').on('click', function () {
    var params = {
        id: $('#linkId').val(),
        categoryId: $("#category-select option:selected").val(),
        name: $('#name').val(),
        url: $('#url').val(),
        logo: $('#logo').val(),
        description: $('#description').val(),
        order: $('#order').val()
    };

    $.ajax({
        type: "POST",
        url: "api/v1/links",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(params),
        success: function (data) {
            $('#new-link-modal').modal('hide');
            linkTable.fnDraw();
        },
        error: function () {
            toastErrorMsg('保存失败!');
        }
    });
});

/**
 * 删除网址.
 */
var deleteLink = function (id) {
    $('#delete-link-id').val(id);
    $('#delete-link-modal').modal();
}

// 监听删除网址的方法
$('#do-delete-btn').on('click', function () {
    $.ajax({
        type: "DELETE",
        url: "api/v1/links/" + $('#delete-link-id').val(),
        success: function (data) {
            linkTable.fnDraw();
            $('#delete-link-id').val('');
            $('#delete-link-modal').modal('hide');
        },
        error: function () {
            $('#delete-link-id').val('');
            toastErrorMsg('删除分类失败!');
        }
    });
});

/**
 * 获取所有分类的单值代码信息.
 */
var getFirstOrSecondLevelCategories = function (selectedValue) {
    $.ajax({
        type: "GET",
        url: "api/v1/categories/codes/levels/1/2",
        dataType: "json",
        success: function (data) {
            if (data) {
                var categorySelect = $('#category-select');
                categorySelect.html('');
                for (var i = 0; i < data.length; ++i) {
                    if (data[i].value === selectedValue) {
                        categorySelect.append('<option value="' + data[i].value + '" selected>' + data[i].name + '</option>');
                    } else {
                        categorySelect.append('<option value="' + data[i].value + '">' + data[i].name + '</option>');
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
        url: "api/v1/links/orders/max",
        success: function (data) {
            $('#order').val(data + 1);
        }
    });
}

// 监听图标 icon 的值，来实时显示图标.
$('#logo').bind('input propertychange change', function() {
    var iconValue = $.trim($(this).val());
    if (iconValue) {
        $('#preview-logo').attr('src', iconValue);
    }
});

/**
 * 监听查询框的 enter 事件.
 */
$('#search-form input').keydown(function (event) {
    if (event.keyCode === 13) {
        linkTable.fnDraw();
    }
});
