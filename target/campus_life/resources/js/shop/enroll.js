$(
    function () {

        // 商铺注册URL
        var enrollURL = '/campus_life/shop/enroll';
        // 获取商铺类别和区域类别URL
        var infoURL = '/campus_life/shop/info';

        // 获取商铺类别和区域类别
        info();
        function info() {
            $.getJSON(
                infoURL,
                function (data) {
                    if (data.success) {
                        // 商铺类别
                        var shopCategoryList = data.shopCategoryList;
                        // 区域类别
                        var areaList = data.areaList;

                        // 商铺类别
                        var shopCategoryHtml = '';
                        shopCategoryList.map(function (item, index) {
                            shopCategoryHtml += '<option data-value="'
                                + item.id
                                + '">'
                                + item.name
                                + '</option>';
                        });
                        $('#shop_category').html(shopCategoryHtml);

                        // 区域类别
                        var areaHtml = '';
                        areaList.map(function (item, index) {
                            areaHtml += '<option data-value="'
                                + item.id
                                + '">'
                                + item.name
                                + '</option>';
                        });
                        $('#area_category').html(areaHtml);

                    }else {
                        $.toast('获取商铺类别和区域类别失败！' + data.errMsg);
                    }
                }
            );
        }

        // 注册
        enroll();
        function enroll() {
            $('#submit').click(
                function () {

                    // 创建商铺信息json对象
                    var shopInfo = {};
                    shopInfo.des = $('#desc').val();
                    shopInfo.addr = $('#addr').val();
                    shopInfo.shopCategory = {
                      id : $('#shop_category').find('option').not(
                          function() {
                              return !this.selected;
                          }).data('value')
                    };
                    shopInfo.area = {
                        id : $('#area_category').find('option').not(
                            function() {
                                return !this.selected;
                            }).data('value')
                    }

                    // 获取输入的验证码
                    var verifyCodeActual = $('#j_captcha').val();

                    // 生成表单对象，用于接收参数并传递给后台
                    var formData = new FormData();
                    formData.append('shopInfoStr', JSON.stringify(shopInfo));
                    formData.append('verifyCodeActual', verifyCodeActual);

                    // 将数据提交至后台处理相关操作
                    $.ajax(
                        {
                            url : enrollURL,
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

                }
            );
        }

    }
);