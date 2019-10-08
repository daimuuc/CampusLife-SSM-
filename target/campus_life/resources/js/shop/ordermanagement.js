$(function () {

    // 获取订单信息列表的URL
    var getOrderListURL = '/campus_life/shop/getorderlist';
    // 更改订单状态的URL
    var updateOrderStatusURL = '/campus_life/shop/updateorderstatus';

    // 是否正在加载
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    // 页码
    var pageNum = 1;
    // 时间范围id
    var timeId = 0;
    // 订单类别id
    var orderCategoryId = '';
    // 商品名字
    var productName = '';

    // 预先加载3条订单信息
    addItems(pageSize, pageNum);

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 需要查询的商品名字发生变化后，重置页码，清空原先的订单列表，按照新的名字去查询
    $('#search').on('change', function(e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 时间范围发生变化后，重置页码，清空原先的订单列表，按照新的时间范围去查询
    $('#area-search').on('change', function() {
        timeId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 选择新的订单类别之后，重置页码，清空原先的订单列表，按照新的类别去查询
    $('#shoplist-search-div').on(
        'click',
        '.button',
        function(e) {
            orderCategoryId = e.target.dataset.categoryId;
            // 若之前已选定了别的category,则移除其选定效果，改成选定新的
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                orderCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            // 由于查询条件改变，清空订单列表再进行查询
            $('.list-div').empty();
            // 重置页码
            pageNum = 1;
            addItems(pageSize, pageNum);
        });

    // 点击订单的卡片进入
    $('.shop-list').on('click', '.card', function(e) {
        // 订单ID
        var orderId = e.currentTarget.dataset.orderId;
        // 订单状态
        var status = e.currentTarget.dataset.status;

        // 如果订单处于"未完成状态"，则提交后台，修改其状态
        if (status == 0) {
            var url = updateOrderStatusURL + '?' + 'orderId=' + orderId;
            $.confirm('确认交易?', function () {
                $.getJSON(url, function (data) {
                    if (data.success) {
                        $.toast("交易成功");
                        $('.list-div').empty();
                        pageNum = 1;
                        addItems(pageSize, pageNum);
                    }else {
                        $.toast(data.errMsg);
                    }
                    return ;
                });
            });
        }
    });

    // 初始化页面
    $.init();

    /**
     * 获取分页展示的订单列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = getOrderListURL + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&timeId=' + timeId + '&orderCategoryId=' + orderCategoryId
            + '&productName=' + productName;

        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;

        // 访问后台获取相应查询条件下的订单列表
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取当前查询条件下订单的总数
                maxItems = data.count;
                var html = '';
                // 遍历订单信息列表，拼接出卡片集合
                data.orderInfoList.map(function (item, index) {
                    // 订单状态，0：正在进行 、1：已经完成
                    var status = item.status;
                    if (status == 0)
                        status = '正在进行';
                    else
                        status = '已经完成';
                    html += '' + '<div class="card" data-order-id="'
                        + item.id + '" data-status="' + item.status + '">' + '<div class="card-header">'
                        + item.personInfo.name + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.productInfo.singleImageInfo.src + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productInfo.name + '(' + item.productInfo.des + ')'
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        +  new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</p>'
                        + '<span>' + status + '</span></div>'
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
                $.toast(data.errMsg);
                return ;
            }
        });
    }

});