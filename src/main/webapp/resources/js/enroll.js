$(function () {

    // 注册响应url
    var enrollUrl = '/campus_life/main/enrolllogic';

    // 提交按钮的事件响应，响应用户注册
    $('#submit').click(
        function () {
            // 创建个人基本信息json对象，并从表单里获取对应的属性值
            var personInfo = {};
            personInfo.name = $('#name').val();
            personInfo.phone = $('#phone').val();
            personInfo.password = $('#psd').val();
            personInfo.role = $('#category').find('option').not(
                function () {
                    return !this.selected;
                }
            ).data('value');

            // 获取头像文件流
            var thumbnail = $('#small-img')[0].files[0];

            // 获取输入的验证码
            var verifyCodeActual = $('#j_captcha').val();

            // 检验输入手机号
            if (!isPhoneNum(personInfo.phone)) {
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
            formData.append('personInfoStr', JSON.stringify(personInfo));
            formData.append('thumbnail', thumbnail);
            formData.append('verifyCodeActual', verifyCodeActual);

            // 将数据提交至后台处理相关操作
            $.ajax(
                {
                url : enrollUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                            $.toast('提交成功！');
                            $('#captcha_img').click();
                            // window.location.href = '/campus_life/main/login';
                        } else {
                            $.toast('提交失败！' + data.errMsg);
                            $('#captcha_img').click();
                        }
                    }
                }
            );

        }
    )
});