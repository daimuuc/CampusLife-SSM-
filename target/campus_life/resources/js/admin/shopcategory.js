$(function () {

    // 获取商铺类别的URL
    var getShopCategoryUrl = "/campus_life/admin/getshopcategory";

    // 获取商铺类别
    getList();
    function getList() {
        $.getJSON(getShopCategoryUrl, function (data) {
           if (data.success) {
               var shopCategoryList = data.shopCategoryList;
               var tempHtml = '';
               shopCategoryList.map(function (item, index) {
                   tempHtml += '' + '<div class="row row-product">'
                       + '<div class="col-33">'
                       + item.name
                       + '</div>'
                       + '<div class="col-25">'
                       + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                       + '</div>'
                       + '<div class="col-35">'
                       + '<a href="#" class="edit" data-id="'
                       + item.id
                       + '">编辑</a>'
                       + '<a href="#" class="preview" data-id="'
                       + item.id
                       + '">删除</a>'
                       + '</div>'
                       + '</div>';
               });
               $('.product-wrap').html(tempHtml);
           }
           else {
               $.toast('加载商铺类别失败！');
               return;
           }
        });
    }

    // 将class为product-wrap里面的a标签绑定上点击的事件
    $('.product-wrap')
        .on(
            'click',
            'a',
            function (e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    // 如果有class edit则点击就进入商铺类别编辑页面，并带有id参数
                    window.location.href = '/campus_life/admin/editshopcategory?id='
                    + e.currentTarget.dataset.id;
                } else if (target.hasClass('preview')) {
                    // TODO
                    // 删除操作
                }
            }
        );

});