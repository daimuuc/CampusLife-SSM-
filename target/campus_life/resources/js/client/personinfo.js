$(function () {

    // 获取个人信息的URL
    var getPersonInfoUrl = '/campus_life/client/getpersoninfo';
    // 修改个人信息的URL
    var updatePersonInfoUrl = '/campus_life/client/updatepersoninfo';

    // 获取个人信息并渲染
    $.getJSON(getPersonInfoUrl, function (data) {
        if (data.success) {
            var personInfo = data.personInfo;
            $('#name').val(personInfo.name);
            $('#phone').val(personInfo.phone);
            $('#psd').val(personInfo.password);
        }else {
            $.toast("获取个人信息失败: " + data.errMsg);
            return ;
        }
    });

    // 更新个人信息
    $("#submit").click(
      function () {
          // 生成表单对象，用于接收参数并传递给后台
          var formData = new FormData();

          // 创建个人信息json对象
          var personInfo = {};
          personInfo.name =  $('#name').val();
          personInfo.phone = $('#phone').val();
          personInfo.password = $('#psd').val();
          // 检验输入手机号
          if (!isPhoneNum(personInfo.phone)) {
              $.toast('请输入正确的手机号！');
              return;
          }
          formData.append('personInfoStr', JSON.stringify(personInfo));

          // 获取头像文件流
          var thumbnail = $('#small-img')[0].files[0];
          formData.append('thumbnail', thumbnail);

          // 获取输入的验证码
          var verifyCodeActual = $('#j_captcha').val();
          // 检验验证码
          if (!verifyCodeActual) {
              $.toast('请输入验证码！');
              return;
          }
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
                          $.toast('修改成功！');
                          $('#captcha_img').click();
                      } else {
                          $.toast('修改失败！' + data.errMsg);
                          $('#captcha_img').click();
                      }
                  }
              }
          );
      }
    );

});