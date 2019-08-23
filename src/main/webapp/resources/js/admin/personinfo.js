$(function () {
    // 获取个人信息的URL
    var getPersonInfoUrl = "/campus_life/admin/getpersoninfo";
    // 更新个人信息的URL
    var updatePersonInfoUrl = "/campus_life/admin/updatepersoninfo";

    getPersonInfo();

    // 加载个人原始信息
    function getPersonInfo() {
        $.getJSON(getPersonInfoUrl, function(data) {
            if (data.success) {
                // 若访问成功，则依据后台传递过来的个人信息为表单元素赋值
                var personInfo = data.personInfo;
                $('#name').val(personInfo.name);
                $('#phone').val(personInfo.phone);
                $('#psd').val(personInfo.password);
            }
            else {
                $.toast('加载初始信息失败！');
                return;
            }
        });
    }

    // 响应提交事件
    $('#submit').click(function () {
        // 获取用户名
        var name = $('#name').val();
        // 获取输入的手机号
        var phone = $('#phone').val();
        // 获取输入的密码
        var password = $('#psd').val();
        // 获取头像文件流
        var thumbnail = $('#small-img')[0].files[0];
        // 获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();

        // 检验输入手机号
        if (!isPhoneNum(phone)) {
            $.toast('请输入正确的手机号！');
            return;
        }

        // 检验验证码
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }

        // 生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();
        formData.append('name', name);
        formData.append('phone', phone);
        formData.append('password', password);
        formData.append('thumbnail', thumbnail);
        formData.append('verifyCodeActual', verifyCodeActual);

        // 将数据提交至后台处理相关操作
        $.ajax(
            {
                url : updatePersonInfoUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！' + data.errMsg);
                        $('#captcha_img').click();
                    }
                }
            }
        );

    });
});
