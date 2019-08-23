$(function () {

    // 添加响应url
    var addShopCategoryUrl = '/campus_life/admin/addshopcategory';

    // 提交按钮的事件响应，响应商铺类别添加
    $('#submit').click(
        function () {
            // 获取商铺类别名
            var name = $('#name').val();
            // 获取头像文件流
            var thumbnail = $('#small-img')[0].files[0];

            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append(' name', name);
            formData.append('thumbnail', thumbnail);

            // 将数据提交至后台处理相关操作
            $.ajax(
                {
                    url : addShopCategoryUrl,
                    type : 'POST',
                    data : formData,
                    contentType : false,
                    processData : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            $.toast('提交成功！');
                        } else {
                            $.toast('提交失败！' + data.errMsg);
                        }
                    }
                }
            );

        }
    )

});