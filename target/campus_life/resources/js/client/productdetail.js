$(
  function () {
      // 从地址栏的URL里获取productId
      var productId = getQueryString('productId');
      // 获取商品信息的URL
      var productUrl = '/campus_life/client/productdetailinfo?productId=' + productId;
      // 添加购物车信息的URL
      var cartUrl = '/campus_life/client/addCartInfo?productId=' + productId;
      //  购物车Id
      var cartId = -1;

      // 访问后台获取该商品的信息并渲染
      $.getJSON(productUrl, function (data) {
          if (data.success) {
              // 获取商品信息
              var product = data.product;

              //// 给商品信息相关HTML控件赋值
              // 商品缩略图
              $('#product-img').attr('src', product.singleImageInfo.src);
              // 商品更新时间
              $('#product-time').text(
                  new Date(product.lastEditTime).Format("yyyy-MM-dd"));
              // 商品积分
              if (product.point != undefined) {
                  $('#product-point').text('购买可得' + product.point + '积分');
              }
              // 商品名称
              $('#product-name').text(product.name);
              // 商品简介
              $('#product-desc').text(product.des);
              // 商品价格展示逻辑，主要判断原价现价是否为空 ，所有都为空则不显示价格栏目
              if (product.normalPrice != undefined
                  && product.promotionPrice != undefined) {
                  // 如果现价和原价都不为空则都展示，并且给原价加个删除符号
                  $('#price').show();
                  $('#normalPrice').html(
                      '<del>' + '￥' + product.normalPrice + '</del>');
                  $('#promotionPrice').text('￥' + product.promotionPrice);
              } else if (product.normalPrice != undefined
                  && product.promotionPrice == undefined) {
                  // 如果原价不为空而现价为空则只展示原价
                  $('#price').show();
                  $('#promotionPrice').text('￥' + product.normalPrice);
              } else if (product.normalPrice == undefined
                  && product.promotionPrice != undefined) {
                  // 如果现价不为空而原价为空则只展示现价
                  $('#promotionPrice').text('￥' + product.promotionPrice);
              }

              //// 遍历商品详情图列表，并生成批量img标签
              var imgListHtml = '';
              product.mutipleImageInfoList.map(function (item, index) {
                  imgListHtml += '<div> <img src="' + item.src
                      + '" width="100%" /></div>';
              });
              $('#imgList').html(imgListHtml);

              // 获取cartId，判断该商品是否加入购物车
              cartId = data.cartId;
              if (cartId != -1)
                  $("#cart").text('取消添加');
          }else {
              $.toast(data.errMsg)
              return ;
          }
      })

      // 添加到购物车
      $("#cart").click(function () {
          if (cartId != -1) {
              // 删除购物车信息的URL
              var deleteCartInfoURL = '/campus_life/client/deletecartinfo?cartId=' + cartId;
              // 访问后台，删除购物车信息
              $.getJSON(deleteCartInfoURL, function (data) {
                  if (data.success) {
                      $.toast("删除成功");
                      cartId = -1;
                      $("#cart").text('加入购物车');
                      return ;
                  }else {
                      $.toast("删除购物车信息失败" + data.errMsg);
                      return ;
                  }
              });
          }else {
              // 访问后台添加购物车信息
              $.getJSON(cartUrl, function (data) {
                  if (data.success) {
                      $.toast('添加成功');
                      $("#cart").text('取消添加');
                      cartId = data.cartId;
                      return ;
                  }else {
                      $.toast('添加失败：' + data.errMsg);
                      return ;
                  }
              })
          }
      });

      // 点击后打开右侧栏
      $('#me').click(function() {
          $.openPanel('#panel-right-demo');
      });
      $.init();
  }  
);