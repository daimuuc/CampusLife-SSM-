$(function () {
    // 获取购物车信息列表的URL
    var getCartInfoListURL = '/campus_life/client/getcartinfolist';
    // 添加订单信息的URL
    var addOrderInfoURL = '/campus_life/client/addorderinfo';

    // 访问后台获取购物车信息列表并渲染
    getCartInfoList();

    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 点击购买
    $('#cart').click(function () {
        $.getJSON(addOrderInfoURL, function (data) {
            if (data.success) {
                $.toast("购买成功");
                // 清空卡片集合
                $('.list-div').empty();
                return ;
            }else {
                $.toast("购买失败");
                return ;
            }
        })
    });

    // 初始化页面
    $.init();

    // 访问后台获取购物车信息列表并渲染
    function getCartInfoList() {
        $.getJSON(getCartInfoListURL, function (data) {
            if (data.success) {
                // 清空卡片集合
                $('.list-div').empty();
                // 遍历购物车信息Map
                var cartInfoListMap = data.cartInfoListMap;
                var html = '';
                $.each(cartInfoListMap, function (key, value) {
                    var flag = true;
                    // 遍历购物车信息List，拼接出卡片集合
                    value.map(function (item, index) {
                        if (flag) {
                            html += '<div class="card">'
                                + '<div class="card-header" data-shop-id="' + item.productInfo.shopInfo.id + '">' + item.productInfo.shopInfo.personInfo.name + '</div>';
                        }
                        html += '<div class="card-content" data-product-id="' + item.productInfo.id + '">'
                            + '<div class="list-block media-list">'
                            + '<ul> <li class="item-content">'
                            + '<div class="item-media">'
                            + '<img src="' + item.productInfo.singleImageInfo.src + '" width="44">'
                            + '</div>'
                            + '<div class="item-inner">'
                            + '<div class="item-subtitle">' + item.productInfo.des + '</div>'
                            + '</div> </li> </ul> </div> </div>'
                            + '<div class="card-footer">'
                            + '<span>' + item.productInfo.promotionPrice + '元</span>' + '<span  class="cancel" data-cart-id="' + item.id + '">取消商品</span>'
                            + '</div>'
                            + '<hr/>';
                        flag = false;
                    });
                    html += "</div>";
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);

                // 点击跳转到商店详情界面
                $('.card-header').on('click', function (e) {
                    var shopId = e.currentTarget.dataset.shopId;
                    window.location.href = '/campus_life/client/shopdetail?shopId=' + shopId;
                });

                // 点击跳转到商品详情界面
                $(".card-content").on('click', function(e) {
                    var productId = e.currentTarget.dataset.productId;
                    window.location.href = '/campus_life/client/productdetail?productId='
                        + productId;
                });

                // 点击删除该购物车信息
                $(".cancel").on('click', function (e) {
                    // 获取Id
                    var cartId = e.currentTarget.dataset.cartId;
                    // 删除购物车信息的URL
                    var deleteCartInfoURL = '/campus_life/client/deletecartinfo?cartId=' + cartId;

                    // 访问后台，删除购物车信息
                    $.getJSON(deleteCartInfoURL, function (data) {
                        if (data.success) {
                            $.toast("删除成功");
                            window.location.href = '/campus_life/client/cart';
                            return ;
                        }else {
                            $.toast("删除购物车信息失败" + data.errMsg);
                            return ;
                        }
                    });
                })

                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }else {
                $.toast("获取购物车信息列表失败：" + data.errMsg);
                return ;
            }
        });
    }

});