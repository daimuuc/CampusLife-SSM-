$(
    function () {
        // 从URL里获取productId参数的值
        var productId = getQueryString('id');
        // 获取商品类别的URL
        var getProductCategoryURL = '/campus_life/shop/getproductcategorylist';
        // 添加商品信息的URL
        var addProductInfoURL = '/campus_life/shop/addproductinfo';
        // 修改商品信息的URL
        var updateProductInfoURL = '/campus_life/shop/modifyproductinfo';
        // 获取商品信息的URL
        var getProductInfoURL = '/campus_life/shop/getproductinfobyid?id=' + productId;
        // 由于商品添加和编辑使用的是同一个页面，
        // 该标识符用来标明本次是添加还是编辑操作
        var isEdit = false;
        if (productId) {
            // 若有productId则为编辑操作
            isEdit = true;
        }

        // 初始化函数
        init();

        // 响应提交事件
        submitProductInfo();


        // 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
        // 且控件总数未达到6个，则生成新的一个文件上传控件
        $('.detail-img-div').on('change', '.detail-img:last-child', function() {
            if ($('.detail-img').length < 6) {
                $('#detail-img').append('<input type="file" class="detail-img">');
            }
        });

        // 初始化函数
        function init() {
            if (isEdit) {
                // 编辑操作
                $.getJSON(getProductInfoURL,
                    function (data) {
                        if (data.success) {
                            // 从返回的JSON当中获取product对象的信息，并赋值给表单
                            var product = data.productInfo;
                            $('#name').val(product.name);
                            $('#normal-price').val(product.normalPrice);
                            $('#promotion-price').val(product.promotionPrice);
                            $('#point').val(product.point);
                            $('#des').val(product.des);

                            // 该店铺的所有商品类别列表
                            var optionHtml = '';
                            var optionArr = data.productCategoryList;
                            var optionSelected = product.productCategory.id;
                            // 生成前端的HTML商品类别列表
                            optionArr
                                .map(function(item, index) {
                                    var isSelect = optionSelected === item.id ? 'selected'
                                        : '';
                                    optionHtml += '<option data-value="'
                                        + item.id
                                        + '" '
                                        + isSelect
                                        + '>'
                                        + item.name
                                        + '</option>';
                                });
                            $('#product-category').html(optionHtml);
                        }else {
                            $.toast('获取商品信息失败');
                            return;
                        }
                    })
            }else {
                // 添加操作
                $.getJSON(getProductCategoryURL,
                    function (data) {
                        if (data.success) {
                            // 该店铺的所有商品类别列表
                            var optionHtml = '';
                            var optionArr = data.productCategoryList;
                            // 生成前端的HTML商品类别列表
                            optionArr
                                .map(function(item, index) {
                                    optionHtml += '<option data-value="'
                                        + item.id
                                        + '" >'
                                        + item.name
                                        + '</option>';
                                });
                            $('#product-category').html(optionHtml);
                        }else {
                            $.toast('获取商品类别失败');
                            return;
                        }
                    })
            }
        }

        // 响应提交事件
        function submitProductInfo() {
                $('#submit').click(
                    function () {
                        // 生成表单对象，用于接收参数并传递给后台
                        var formData = new FormData();

                        // 创建商品信息json对象
                        var productInfo = {};
                        if (isEdit)
                            productInfo.id = productId;
                        productInfo.name = $('#name').val();
                        productInfo.normalPrice = $('#normal-price').val();
                        productInfo.promotionPrice = $('#promotion-price').val();
                        productInfo.point = $('#point').val();
                        productInfo.des = $('#des').val();
                        productInfo.productCategory = {
                            id : $('#product-category').find('option').not(
                                function() {
                                    return !this.selected;
                                }).data('value')
                        };
                        formData.append('productInfoStr', JSON.stringify(productInfo));

                        // 获取输入的验证码
                        var verifyCodeActual = $('#j_captcha').val();
                        if (!verifyCodeActual) {
                            $.toast('请输入验证码！');
                            return;
                        }
                        formData.append('verifyCodeActual', verifyCodeActual);

                        // 获取缩略图文件流
                        var thumbnail = $('#small-img')[0].files[0];
                        formData.append('thumbnail', thumbnail);

                        // 遍历商品详情图控件，获取里面的文件流
                        $('.detail-img').map(
                            function(index, item) {
                                // 判断该控件是否已选择了文件
                                if ($('.detail-img')[index].files.length > 0) {
                                    // 将第i个文件流赋值给key为productImgi的表单键值对里
                                    formData.append('productImg' + index,
                                        $('.detail-img')[index].files[0]);
                                }
                            });

                        // 将数据提交至后台处理相关操作
                        $.ajax(
                            {
                                url : isEdit ? updateProductInfoURL : addProductInfoURL,
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