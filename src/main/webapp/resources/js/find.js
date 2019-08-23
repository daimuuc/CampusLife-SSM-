$(function () {

    // 注册响应url
    var findUrl = '/campus_life/main/findlogic';

    // 提交按钮的事件响应，响应用户注册
    $('#submit').click(
        function () {
            // 获取输入的手机号
            var phone = $('#phone').val();
            // 获取输入的新密码
            var password = $('#psd').val();
            // 获取输入的验证密码
            var cpassword = $('#cpsd').val();
            // 获取用户类型
            var role = $('#category').find('option').not(
                function () {
                    return !this.selected;
                }
            ).data('value');
            // 获取输入的验证码
            var verifyCodeActual = $('#j_captcha').val();

            // 检验输入手机号
            if (!isPhoneNum(phone)) {
                $.toast('请输入正确的手机号！');
                return;
            }

            // 检验输入密码
            if (password != cpassword) {
                $.toast('两次密码不一致！');
                return;
            }

            // 检验验证码
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }

            // 访问后台
            $.ajax({
                url : findUrl,
                cache : false,
                type : "POST",
                dataType : 'json',
                data : {
                    phone : phone,
                    password : password,
                    role : role,
                    verifyCodeActual : verifyCodeActual,
                },
                success : function(data) {
                    if (data.success) {
                        $.toast('修改成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('修改失败！' + data.errMsg);
                        $('#captcha_img').click();
                    }
                }
            });
        }
    )
});