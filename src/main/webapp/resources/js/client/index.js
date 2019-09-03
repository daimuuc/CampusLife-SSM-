$(function () {

    //定义访问后台，获取头条列表以及商店类别列表的URL
    var url = '/campus_life/client/listindexinfo';

    //访问后台，获取头条列表以及商品类别列表
    $.getJSON(url, function (data) {
        if (data.success) {
            // TODO
            // //获取后台传递过来的头条列表
            // var headLineList = data.headLineList;
            // var swiperHtml = '';
            // //遍历头条列表，并拼接出轮播图组
            // headLineList.map(function(item, index) {
            //     swiperHtml += '' + '<div class="swiper-slide img-wrap">'
            //         + '<a href="' + item.lineLink
            //         + '" external><img class="banner-img" src="' + item.lineImg
            //         + '" alt="' + item.lineName + '"></a>' + '</div>';
            // });
            // //将轮播图组赋值给前端HTML控件
            // $('.swiper-wrapper').html(swiperHtml);
            // //设定轮播图轮换时间为3秒
            // $(".swiper-container").swiper({
            //     autoplay : 3000,
            //     //用户对轮播图进行操作时，是否自动停止autoplay
            //     autoplayDisableOnInteraction : false
            // });

            //获取后台传递过来的商店类别列表
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            //遍历商铺类别列表，拼接出俩俩(col-50)一行的类别
            shopCategoryList.map(function(item, index) {
                categoryHtml += ''
                    + '<div class="col-50 shop-classify" data-category='
                    + item.id + '>' + '<div class="word">'
                    + '<p class="shop-title">' + item.name
                    + '</p>' + '<p class="shop-desc">'
                    + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</p>' + '</div>'
                    + '<div class="shop-classify-img-warp">'
                    + '<img class="shop-img" src="' + item.singleImageInfo.src
                    + '">' + '</div>' + '</div>';
            });
            //将拼接好的类别赋值给前端HTML控件进行展示
            $('.row').html(categoryHtml);
        }else {
            $.toast("获取信息失败");
            return ;
        }
    })

    //若点击"我的"，则显示侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    $('.row').on('click', '.shop-classify', function(e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/campus_life/client/shoplist?id=' + shopCategoryId;
        window.location.href = newUrl;
    });
});