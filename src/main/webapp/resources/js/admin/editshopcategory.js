$(function () {
    // 从URL里获取id参数的值
    var id = getQueryString('id');
    // 通过id获取商铺类别的URL
    var getShopCategoryUrl = '/campus_life/admin/getshopcategorybyid?id=' + id;
    // 修改商铺类别的URL
    var modifyShopCategoryUrl = '/campus_life/admin/modifyshopcategory';

    // 获取商铺类别
    getShopCategory();
    function getShopCategory() {
        $.getJSON(getShopCategoryUrl, function (data) {
            if (data.success) {
                var name = data.name;
                $('#name').val(name);
            }else {
                $.toast('加载商铺类别失败！');
                return;
            }
        });
    }

    // 提交按钮的事件响应，响应商铺类别修改
    $("#submit").click(
      function () {
          // 获取商铺类别名
          var name = $('#name').val();
          // 获取缩略图文件流
          var thumbnail = $('#small-img')[0].files[0];

          // 生成表单对象，用于接收参数并传递给后台
          var formData = new FormData();
          formData.append("thumbnail", thumbnail);
          formData.append("name", name);

          // 将数据提交至后台处理相关操作
          $.ajax(
              {
                  url : modifyShopCategoryUrl,
                  type : 'POST',
                  data : formData,
                  contentType : false,
                  processData : false,
                  cache : false,
                  success : function(data) {
                      if (data.success) {
                          $.toast('提交成功！');
                      } else {
                          $.toast('提交失败！');
                      }
                  }
              }
          );
      }
    );
});