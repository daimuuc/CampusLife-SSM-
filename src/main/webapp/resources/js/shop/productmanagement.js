$(function () {
    // 获取商品类别的URL
    var getProductCategoryURL = '/campus_life/shop/getproductcategorylist';
    // 切换商品上下架状态的URL
    var changeStatusURL = '/campus_life/shop/modifyproductinfo';

    // 商品名字
    var name = '';
    // 商品类别
    var productCategoryId = '';

    // 获取商品类别
    getProductCategory();

    // 获取商品信息
    getProductInfo();

    // 需要查询的商品名字发生变化后，清空原先的商品列表，按照新的名字去查询
    $('#search').on('change', function(e) {
        name = e.target.value;
        getProductInfo();
    });

    // 选择新的商品类别之后，清空原先的商品列表，按照新的类别去查询
    $('#productdetail-button-div').on(
        'click',
        '.button',
        function(e) {
            productCategoryId = e.target.dataset.categoryId;
            // 若之前已选定了别的category,则移除其选定效果，改成选定新的
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                productCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            getProductInfo();
        });

    // 将class为product-wrap里面的a标签绑定上点击的事件
    $('.product-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    // 如果有class edit则点击就进入商品信息编辑页面，并带有id参数
                    window.location.href = '/campus_life/shop/productinfo?id='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('status')) {
                    // 如果有class status则调用后台功能上/下架相关商品，并带有id参数
                    changeItemStatus(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('delete')) {
                    // 如果有class preview则去前台展示系统该商品详情页预览商品情况
                    window.location.href = '/campus_life/shop/deleteproductinfo?id='
                        + e.currentTarget.dataset.id;
                }
            });

    // 获取商品类别
    function getProductCategory() {
        $.getJSON(getProductCategoryURL,
            function (data) {
                if (data.success) {
                    // 该店铺的所有商品类别列表
                    var html = '';
                    var productCategoryList = data.productCategoryList;
                    // 遍历商品类别列表，拼接出a标签集
                    productCategoryList
                        .map(function(item, index) {
                            html += '<a href="#" class="button" data-category-id='
                                + item.id
                                + '>'
                                + item.name
                                + '</a>';
                        });
                    $('#productdetail-button-div').html(html);
                }else {
                    $.toast('获取商品类别失败');
                    return;
                }
            })
    }

    // 获取商品信息
    function getProductInfo() {
        // 获取商品信息的URL
        var getProductInfoURL = '/campus_life/shop/getproductinfo' + '?' +
            'name=' + name + '&productcategoryid=' + productCategoryId;
        // 访问后台获取相应查询条件下的商品列表
        $.getJSON(getProductInfoURL, function(data) {
            if (data.success) {
                var html = '';
                // 遍历商品列表
                data.producList.map(function(item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        // 若状态值为0，表明是已下架的商品，操作变为上架(即点击上架按钮上架相关商品)
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    // 拼接每件商品的行信息
                    html += '<div class="row row-product">'
                        + '<div class="col-33">' + item.name + '</div>'
                        + '<div class="col-25">' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</div>'
                        + '<div class="col-35">'
                        + '<a href="#" class="edit" data-id="' + item.id + '">编辑</a>'
                        + '<a href="#" class="status" data-id="' + item.id + '" data-status="'+ contraryStatus
                            + '">' + textOp +'</a>'
                        + '<a href="#" class="delte" data-id="' + item.id + '">删除</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(html);
            }else {
                $.toast('查询商品信息失败：' + data.errMsg);
            }
        });
    }

    // 切换商铺上下架状态
    function changeItemStatus(id, enableStatus) {
        // 定义product json对象并添加productId以及状态(上架/下架)
        var product = {};
        product.id = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            // 上下架相关商品
            $.ajax({
                url : changeStatusURL,
                type : 'POST',
                data : {
                    productInfoStr : JSON.stringify(product),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getProductInfo();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

});