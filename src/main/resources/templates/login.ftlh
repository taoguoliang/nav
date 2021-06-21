<!DOCTYPE html>
<html lang="zh">
<head>
    <title>登录 | CXY网址导航</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="author" content="blinkfox"/>
    <meta name="keywords" content="CXY网址导航">
    <meta name="description" content="CXY网址导航">

    <link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css" type="text/css">
    <link rel="stylesheet" href="assets/css/fonts/fontawesome/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="assets/css/xenon-core.css" type="text/css">
    <link rel="stylesheet" href="assets/css/xenon-components.css" type="text/css">
    <link rel="stylesheet" href="assets/css/xenon-skins.css" type="text/css">
    <link rel="stylesheet" href="assets/css/nav.css" type="text/css">
    <script src="assets/js/jquery-1.11.1.min.js" type="text/javascript"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js" type="text/javascript"></script>
    <script src="assets/js/respond.min.js" type="text/javascript"></script>
    <![endif]-->
</head>

<body class="page-body lockscreen-page">

<div class="login-container">
    <div class="row">
        <div class="col-sm-offset-3 col-sm-6">
            <form id="login-form" class="lockcreen-form fade-in-effect in">
                <div class="user-thumb">
                    <img src="assets/images/avator.jpg" class="img-responsive img-circle"/>
                </div>
                <div class="form-group">
                    <h3>欢迎登录</h3>
                    <p>请输入管理员账户的登录密码</p>
                    <div class="input-group">
                        <input type="text" class="form-control input-dark" name="username" id="username" style="display: none;"/>
                        <input type="password" class="form-control input-dark" name="passwd" id="passwd"
                               placeholder="请输入登录密码"/>
                        <span class="input-group-btn">
                            <button type="button" id="login-btn" class="btn btn-primary"><i class="fa fa-sign-in"></i> 登录</button>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/TweenMax.min.js" type="text/javascript"></script>
<script src="assets/js/resizeable.js" type="text/javascript"></script>
<script src="assets/js/joinable.js" type="text/javascript"></script>
<script src="assets/js/xenon-api.js" type="text/javascript"></script>
<script src="assets/js/xenon-toggles.js" type="text/javascript"></script>
<script src="assets/js/xenon-custom.js" type="text/javascript"></script>
<script src="assets/js/toastr/toastr.min.js" type="text/javascript"></script>
<script src="assets/js/local/login.js" type="text/javascript"></script>
</body>

</html>
