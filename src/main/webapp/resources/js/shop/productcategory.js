$(function () {
    // 获取商品类别信息的URL
    var getProductCategoryUrl = "/campus_life/shop/getproductcategory";
    // 添加商品类别信息的URL
    var addProductCategoryUrl = "/campus_life/shop/addproductcategory";
    // 删除商品类别信息的URL
    var deleteProductCategoryUrl = "/campus_life/shop/deleteproductcategory";

    // 获取商品类别信息
    getList();
    function getList() {
        $.getJSON(getProductCategoryUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var tempHtml = '';
                dataList.map(function(item, index) {
                    tempHtml += ''
                        + '<div class="row row-product-category now">'
                        + '<div class="col-33 product-category-name">'
                        + item.name
                        + '</div>'
                        + '<div class="col-33">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '</div>'
                        + '<div class="col-33"><a href="#" class="button delete" data-id="'
                        + item.id
                        + '">删除</a></div>'
                        + '</div>';
                });
                $('.category-wrap').append(tempHtml);
            }
            else {
                $.toast('加载商品类别信息失败！');
                return;
            }
        });
    }

    // 响应新增事件
    $('#new').click(
        function() {
            var tempHtml = '<div class="row row-product-category temp">'
                + '<div class="col-33"><input class="category-input category" type="text" placeholder="类别名字"></div>'
                + '<div class="col-33"><input class="category-input priority" type="text" placeholder="编辑时间"></div>'
                + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
                + '</div>';
            $('.category-wrap').append(tempHtml);
        });

    // 响应提交事件
    $('#submit').click(function() {
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.name = $(item).find('.category').val();
            if (tempObj.name) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url : addProductCategoryUrl,
            type : 'POST',
            data : JSON.stringify(productCategoryList),
            contentType : 'application/json',
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                } else {
                    $.toast('提交失败！');
                }
            }
        });
    });

    // 响应新增删除事件
    $('.category-wrap').on('click', '.row-product-category.temp .delete',
        function(e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();

        });

    // // TODO
    // // 响应原始删除事件
    // $('.category-wrap').on('click', '.row-product-category.now .delete',
    //     function(e) {
    //         var target = e.currentTarget;
    //         $.confirm('确定么?', function() {
    //             $.ajax({
    //                 url : deleteAreaUrl,
    //                 type : 'POST',
    //                 data : {
    //                     id : target.dataset.id
    //                 },
    //                 dataType : 'json',
    //                 success : function(data) {
    //                     if (data.success) {
    //                         $.toast('删除成功！');
    //                         getList();
    //                     } else {
    //                         $.toast('删除失败！');
    //                     }
    //                 }
    //             });
    //         });
    //     });
});