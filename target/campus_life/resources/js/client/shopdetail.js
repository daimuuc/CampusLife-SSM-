$(function () {
    // 获取本店铺信息以及商品类别信息列表的URL
    var searchDivUrl = '/campus_life/client/shopinfoandproductcategory?shopId=';
    // 获取商品列表的URL
    var productListUrl = '/campus_life/client/productList';

    // 商店id
    var shopId = getQueryString("shopId");
    // 是否正在加载
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    // 页码
    var pageNum = 1;
    // 产品类别id
    var productCategoryId = '';
    // 产品名字
    var productName = '';

    // 渲染出店铺详情以及商品类别
    getSearchDivData();

    // 预先加载3条产品信息
    addItems(pageSize, pageNum);

    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function(e) {
            // 获取商品类别Id
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                // 若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    // 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
    $('#search').on('change', function(e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击商品的卡片进入该商品的详情页
    $('.list-div').on(
        'click',
        '.card',
        function(e) {
            var productId = e.currentTarget.dataset.productId;
            window.location.href = '/campus_life/client/productdetail?productId='
                + productId;
        });

    // 初始化页面
    $.init();

    /**
     * 渲染出店铺详情以及商品类别
     */
    function getSearchDivData() {
        var url = searchDivUrl + shopId;
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取商铺详情并渲染
                var shopInfo = data.shopInfo;
                $('#shop-cover-pic').attr('src', shopInfo.personInfo.singleImageInfo.src);
                $('#shop-name').html(shopInfo.personInfo.name);
                $('#shop-desc').html(shopInfo.des);
                $('#shop-addr').html(shopInfo.addr);
                $('#shop-phone').html(shopInfo.personInfo.phone);

                // 获取商品类别列表并渲染
                var productCategoryList = data.productCategoryList;
                var html = '';
                // 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
                productCategoryList
                    .map(function(item, index) {
                        html += '<a href="#" class="button" data-product-search-id='
                            + item.id
                            + '>'
                            + item.name
                            + '</a>';
                    });
                // 将商品类别a标签绑定到相应的HTML组件中
                $('#shopdetail-button-div').html(html);
            }else {
                $.toast("获取店铺详情以及商品类别失败：" + data.errMsg);
                return ;
            }
        });
    }

    /**
     * 获取分页展示的产品列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = productListUrl + '?' + 'shopId=' + shopId + '&pageIndex=' + pageNum +
            '&pageSize=' + pageSize + '&productCategoryId=' + productCategoryId + '&productName=' + productName;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;

        // 访问后台获取相应查询条件下的商品列表
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取当前查询条件下商品的总数
                maxItems = data.count
                var html = '';
                // 遍历商品列表，拼接出卡片集合
                data.productList.map(function(item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.id + '>'
                        + '<div class="card-header">' + item.name
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.singleImageInfo.src + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.des
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);
                // 获取目前为止已显示的卡片总数，包含之前已经加载的
                var total = $('.list-div .card').length;
                // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }else {
                $.toast("获取产品列表失败：" + data.errMsg);
                return ;
            }
        });
    }

});