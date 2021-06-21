<!DOCTYPE html>
<html lang="zh">
<head>
    <title>分类管理 | CXY网址导航</title>
    <#include "commons/head.ftlh"/>
</head>

<body class="page-body">
<div class="page-container">
    <#include "commons/sidebar.ftlh"/>
    <div class="main-content">
        <#include "commons/navbar.ftlh"/>

        <!-- 分类搜索 -->
        <div class="panel panel-default">
            <div id="search-form" class="form-inline">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">分类名称</div>
                        <input type="text" class="form-control" id="search-catetory-name" placeholder="请输入分类名称关键字">
                    </div>
                </div>
                <button id="search-btn" type="button" class="btn btn-primary search-btn"><i class="fa fa-search"></i> 搜索</button>
            </div>
        </div>

        <!-- 分类管理 -->
        <div class="panel panel-default">
            <span class="panel-title table-title"><i class="fa fa-tag"></i> 分类管理</span>
            <button id="new-category-btn" class="btn btn-blue btn-icon btn-sm add-btn">
                <i class="fa-plus"></i>
                <span>新增</span>
            </button>
            <div class="panel-body">
                <table id="category-table" class="table table-striped table-bordered admin-table" cellspacing="0"
                       width="100%">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>所属页面</th>
                        <th>最后更新时间</th>
                        <th>显示顺序</th>
                        <th width="120px">操作</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>

        <#include "commons/footer.ftlh"/>
    </div>
</div>

<div id="new-category-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">新增分类 <i id="preview-icon" class=""></i></h4>
            </div>
            <div class="modal-body">
                <div class="panel-body">
                    <form role="form" class="form-horizontal">
                        <input type="hidden" id="categoryId" value="">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="name">分类名称 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" placeholder="分类名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="icon">图标 icon <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="icon" value="linecons-globe"
                                       placeholder="请参考 linecons 或者 Font Awesome 的图标填写,如：linecons-globe">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="par-cate-select">父分类</label>
                            <div class="col-sm-10">
                                <select id="par-cate-select" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="page">所属页面 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <select id="page" class="form-control">
                                    <option value="index" selected>首页</option>
                                    <option value="internet">互联网</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="order">显示顺序 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="order" placeholder="用于表示网址分类的显示顺序">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> 取消</button>
                <button id="save-category-btn" type="button" class="btn btn-primary"><i class="fa fa-save"></i> 保存</button>
            </div>
        </div>
    </div>
</div>

<div id="delete-category-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="deleteCategoryModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="deleteCategoryModal"><i class="fa fa-comment-o"></i> 删除提示</h4>
            </div>
            <div class="modal-body">
                <div class="panel-body">
                    <input type="hidden" id="delete-category-id" value="">
                    <h4>是否确定要删除该分类？</h4>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> 取消</button>
                <button id="do-delete-btn" type="button" class="btn btn-primary"><i class="fa fa-trash"></i> 确认删除</button>
            </div>
        </div>
    </div>
</div>

<#include "commons/script.ftlh"/>
<script src="assets/js/local/category.js"></script>
</body>

</html>
