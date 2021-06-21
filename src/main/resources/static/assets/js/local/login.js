/**
 * 显示提示信息.
 *
 * @param msg 消息
 */
var toastErrorMsg = function (msg) {
    toastr.error(msg, {
        'closeButton': true,
        'debug': false,
        'positionClass': 'toast-top-full-width',
        'onclick': null,
        'showDuration': '300',
        'hideDuration': '1000',
        'timeOut': '5000',
        'extendedTimeOut': '1000',
        'showEasing': 'swing',
        'hideEasing': 'linear',
        'showMethod': 'fadeIn',
        'hideMethod': 'fadeOut'
    });
}

/**
 * 执行登录.
 */
var doLogin = function () {
    var password = $('#passwd').val();
    if (!password || $.trim(password).length === 0) {
        toastErrorMsg('登录密码不能为空！');
        return;
    }

    $.ajax({
        type: 'POST',
        url: 'session/login',
        data: {
            'username': 'admin',
            'password': password
        },
        contentType: 'application/x-www-form-urlencoded',
        success: function (data) {
            window.location.href = 'dashboard';
        },
        error: function () {
            toastErrorMsg('登录密码错误!');
        }
    });
}

/**
 * 监听登录按钮.
 */
$('#login-btn').on('click', function () {
    doLogin();
});

/**
 * 监听 enter 事件.
 */
$('#login-form input').keydown(function (event) {
    if (event.keyCode === 13) {
        doLogin();
    }
});
