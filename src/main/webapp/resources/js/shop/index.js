$(function () {

    // 获取ShopInfo的URL
    var getShopInfoURL = '/campus_life/shop/getshopinfo';
    // 注销登录的URL
    var logoutURL = '/campus_life/main/logout';
    // 购买头条的URL
    var addHeadLineURL = '/campus_life/shop/addheadline';

    // 获取ShopInfo
    $.getJSON(getShopInfoURL, function (data) {
       if (data.success) {
           // 获取personInfo
           var personInfo = data.personInfo;
           $('#user_name').text(' ' + personInfo.name + ' ');
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

    // 点击购买头条
    $('#headline').click(function () {
        $.confirm('确认购买?', function () {
            $.getJSON(addHeadLineURL, function (data) {
                if (data.success) {
                    $.toast("购买成功");
                }else {
                    $.toast(data.errMsg);
                }
                return ;
            });
        });
    });

});