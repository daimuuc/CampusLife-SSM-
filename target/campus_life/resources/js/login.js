$(function () {
    // 登录响应url
    var loginUrl = '/campus_life/main/loginlogic';
    // 登录次数，累积登录三次失败之后自动弹出验证码要求输入
    var loginCount = 0;

    $('#login').click(function () {
        // 获取输入的手机号
        var phone = $('#phone').val();
        // 获取输入的密码
        var password = $('#psw').val();
        // 获取用户类型
        var role = $('#category').find('option').not(
            function () {
                return !this.selected;
            }
        ).data('value');
        // 获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();
        // 是否需要验证码验证，默认为false,即不需要
        var needVerify = false;
        // 如果登录三次都失败
        if (loginCount >= 3) {
            // 那么就需要验证码校验了
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }

        // 访问后台进行登录验证
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                phone : phone,
                password : password,
                role : role,
                verifyCodeActual : verifyCodeActual,
                //是否需要做验证码校验
                needVerify : needVerify
            },
            success : function(data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (role == 1) {
                        // 若用户类型为顾客，则登录到顾客界面
                        window.location.href = '/campus_life/client/index';
                    } else if (role == 2) {
                        // 若用户类型为商家，则登录到商家界面
                        window.location.href = '/campus_life/shop/index';
                    }
                    else {
                        // 若用户类型为管理员，则登录到管理员界面
                        window.location.href = '/campus_life/admin/index'
                    }
                } else {
                    $.toast('登录失败！' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        // 登录失败三次，需要做验证码校验
                        $('#verifyPart').show();
                    }
                }
            }
        });

    });
});