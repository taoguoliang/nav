<!DOCTYPE html>
<html lang="zh">
<head>
    <title>网址管理 | CXY网址导航</title>
    <#include "commons/head.ftlh"/>
</head>

<body class="page-body">
<div class="page-container">
    <#include "commons/sidebar.ftlh"/>
    <div class="main-content">
        <#include "commons/navbar.ftlh"/>

        <!-- 网址搜索 -->
        <div class="panel panel-default">
            <div id="search-form" class="form-inline">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">分类名称</div>
                        <input type="text" class="form-control" id="search-catetory-name" placeholder="分类名称关键字">
                    </div>
                </div>
                <div class="form-group mleft15">
                    <div class="input-group">
                        <div class="input-group-addon">网址名称</div>
                        <input type="text" class="form-control" id="search-link-name" placeholder="网址名称关键字">
                    </div>
                </div>
                <div class="form-group mleft15">
                    <div class="input-group">
                        <div class="input-group-addon">URL 链接</div>
                        <input type="text" class="form-control" id="search-url" placeholder="URL 链接关键字符">
                    </div>
                </div>
                <button id="search-btn" type="button" class="btn btn-primary search-btn"><i class="fa fa-search"></i> 搜索</button>
            </div>
        </div>

        <!-- 网址管理 -->
        <div class="panel panel-default">
            <span class="panel-title table-title"><i class="fa fa-globe"></i> 网址管理</span>
            <button id="new-link-btn" class="btn btn-blue btn-icon btn-sm add-btn">
                <i class="fa-plus"></i>
                <span>新增</span>
            </button>
            <div class="panel-body">
                <table id="link-table" class="table table-striped table-bordered admin-table" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>所属分类</th>
                        <th>名称</th>
                        <th>URL 链接</th>
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

<div id="new-link-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">新增网址
                    <img id="preview-logo" src="" class="td-img-circle-25p" alt="无效的 LOGO">
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel-body">
                    <form role="form" class="form-horizontal">
                        <input type="hidden" id="linkId" value="">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="category-select">所属分类 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <select id="category-select" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="name">网址名称 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" placeholder="网址的显示名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="url">URL 链接 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="url" placeholder="网址的 URL 链接地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="logo">LOGO 地址 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="logo" value="assets/images/logos/sketchcasts.png"
                                       placeholder="可以是服务中的资源路径，也可以是互联网中的 URL 链接">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="description">描述信息 <span class="form-star">*</span></label>
                            <div class="col-sm-10">
                                <textarea class="form-control" cols="5" id="description"></textarea>
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
                <button id="save-link-btn" type="button" class="btn btn-primary"><i class="fa fa-save"></i> 保存</button>
            </div>
        </div>
    </div>
</div>

<div id="delete-link-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="deleteLinkModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="deleteLinkModal"><i class="fa fa-comment-o"></i> 删除提示</h4>
            </div>
            <div class="modal-body">
                <div class="panel-body">
                    <input type="hidden" id="delete-link-id" value="">
                    <h4>是否确定要删除该网址？</h4>
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
<script src="assets/js/local/link.js"></script>
</body>

</html>
