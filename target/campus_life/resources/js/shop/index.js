$(function () {

    // 获取ShopInfo的URL
    var getShopInfoURL = '/campus_life/shop/getshopinfo';
    // 注销登录的URL
    var logoutURL = '/campus_life/main/logout';

    // 获取ShopInfo
    $.getJSON(getShopInfoURL, function (data) {
       if (data.success) {
           // 获取shopInfo
           var shopInfo = data.shopInfo;
           // shopInfo为空则跳转到商铺注册界面
           if (shopInfo == null)
               window.location.href = "/campus_life/shop/enroll";
       }
       else {
           $.toast('加载店铺信息失败！');
           // 清除session
           $.ajax({
               url : logoutURL,
               type : "POST",
               async : false,
               cache : false,
               dataType : 'json',
               success : function(data) {
                   if (data.success) {
                       // 清除成功后退出到登录界面
                       window.location.href = "/campus_life/main/login";
                       return false;
                   }
               },
               error : function(data, error) {
                   alert(error);
               }
           });
       }
    });

});