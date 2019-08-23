$(
    function () {
        // 获取商铺类别、区域类别和商铺信息的URL
        var infoURL = '/campus_life/shop/info';
        // 修改商铺信息的URL
        var updateShopInfoURL = '/campus_life/shop/updateshopinfo';

        // 获取商铺类别、区域类别和商铺信息
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
                        // 商铺信息
                        var shopInfo = data.shopInfo;

                        // 商铺类别
                        var shopCategoryHtml = '';
                        var optionSelected = shopInfo.shopCategory.id;
                        // 生成前端的HTML商铺类别列表，并默认选择编辑前的商铺类别
                        shopCategoryList.map(function (item, index) {
                            var isSelect = optionSelected === item.id ? 'selected'
                                : '';
                            shopCategoryHtml += '<option data-value="'
                                + item.id
                                + '"'
                                + isSelect
                                + '>'
                                + item.name
                                + '</option>';
                        });
                        $('#shop_category').html(shopCategoryHtml);

                        // 区域类别
                        var areaHtml = '';
                        optionSelected = shopInfo.area.id;
                        // 生成前端的HTML区域类别列表，并默认选择编辑前的区域类别
                        areaList.map(function (item, index) {
                            var isSelect = optionSelected === item.id ? 'selected'
                                : '';
                            areaHtml += '<option data-value="'
                                + item.id
                                + '"'
                                + isSelect
                                + '>'
                                + item.name
                                + '</option>';
                        });
                        $('#area_category').html(areaHtml);

                        // 商铺信息
                        $('#name').val(shopInfo.personInfo.name);
                        $('#phone').val(shopInfo.personInfo.phone);
                        $('#psd').val(shopInfo.personInfo.password);
                        $('#desc').val(shopInfo.des);
                        $('#addr').val(shopInfo.addr);

                    }else {
                        $.toast('获取商铺类别和区域类别失败！' + data.errMsg);
                    }
                }
            );
        }

        // 修改商铺信息
        updateShopInfo();
        function updateShopInfo() {
            $('#submit').click(
                function () {

                    // 创建商铺信息json对象
                    var shopInfo = {};
                    shopInfo.des = $('#desc').val();
                    shopInfo.addr = $('#addr').val();
                    shopInfo.personInfo = {
                      name : $('#name').val(),
                      phone : $('#phone').val(),
                      password : $('#psd').val()
                    };
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

                    // 获取缩略图文件流
                    var thumbnail = $('#small-img')[0].files[0];

                    // 生成表单对象，用于接收参数并传递给后台
                    var formData = new FormData();
                    formData.append('shopInfoStr', JSON.stringify(shopInfo));
                    formData.append('verifyCodeActual', verifyCodeActual);
                    formData.append('thumbnail', thumbnail);

                    // 将数据提交至后台处理相关操作
                    $.ajax(
                        {
                            url : updateShopInfoURL,
                            type : 'POST',
                            data : formData,
                            contentType : false,
                            processData : false,
                            cache : false,
                            success : function(data) {
                                if (data.success) {
                                    $.toast('提交成功！');
                                    $('#captcha_img').click();
                                    window.location.href = "/campus_life/shop/index";
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