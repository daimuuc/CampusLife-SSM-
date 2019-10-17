$(function () {
    // 获取头条信息列表的URL
    var getHeadlineListURL = '/campus_life/admin/getheadlinelist';
    // 更新头条信息状态的URL
    var updateHeadlineStatusURL = '/campus_life/admin/updateheadlinestatus';
    // 删除头条信息的URL
    var deleteHeadlineURL = '/campus_life/admin/deleteheadline';

    // 访问后台获取头条信息列表
    $.getJSON(getHeadlineListURL, function (data) {
        if (data.success) {
            // 遍历头条信息列表，拼接出tab1卡片集合
            var html = '';
            data.headlineListZero.map(function (item, index) {
                html += '' + '<div class="card" data-headline-id="'
                    + item.id + '" data-status="' + item.status + '">' + '<div class="card-header">'
                    + item.shopInfo.personInfo.name + '</div>'
                    + '<div class="card-content">'
                    + '<div class="list-block media-list">' + '<ul>'
                    + '<li class="item-content">'
                    + '<div class="item-media">' + '<img src="'
                    + item.singleImageInfo.src + '" width="44">' + '</div>'
                    + '<div class="item-inner">'
                    + '<div class="item-subtitle">' + item.shopInfo.des
                    + '</div>' + '</div>' + '</li>' + '</ul>'
                    + '</div>' + '</div>' + '<div class="card-footer">'
                    + '<p class="color-gray">'
                    + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</p>'
                    + '<span>未审核</span>' + '</div>'
                    + '</div>';
            });
            // 将卡片集合添加到目标HTML组件里
            $('.tab1').append(html);

            // 遍历头条信息列表，拼接出tab2卡片集合
            html = '';
            data.headlineListOne.map(function (item, index) {
                html += '' + '<div class="card" data-headline-id="'
                    + item.id + '" data-status="' + item.status + '">' + '<div class="card-header">'
                    + item.shopInfo.personInfo.name + '</div>'
                    + '<div class="card-content">'
                    + '<div class="list-block media-list">' + '<ul>'
                    + '<li class="item-content">'
                    + '<div class="item-media">' + '<img src="'
                    + item.singleImageInfo.src + '" width="44">' + '</div>'
                    + '<div class="item-inner">'
                    + '<div class="item-subtitle">' + item.shopInfo.des
                    + '</div>' + '</div>' + '</li>' + '</ul>'
                    + '</div>' + '</div>' + '<div class="card-footer">'
                    + '<p class="color-gray">'
                    + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</p>'
                    + '<span>已审核</span>' + '</div>'
                    + '</div>';
            });
            // 将卡片集合添加到目标HTML组件里
            $('.tab2').append(html);

            // 设置tab1、tab2的点击操作
            $(document).on('click','.card', function (e) {
                var headlineId = e.currentTarget.dataset.headlineId;
                var status = e.currentTarget.dataset.status;

                var buttons1 = [
                    {
                        text: '请选择',
                        label: true
                    },
                    {
                        text: '通过',
                        bold: true,
                        color: 'danger',
                        onClick: function() {
                            if (status == 0) {
                                status = 1;
                                $.getJSON(updateHeadlineStatusURL + '/' + headlineId + '/' + status, function (data) {
                                    if (data.success) {
                                        $.toast("通过成功");
                                        window.location.href = '/campus_life/admin/headline';
                                    }else {
                                        $.toast(data.errMsg);
                                    }
                                });
                            }else {
                                $.alert("已通过，请不要重复操作");
                            }

                        }
                    },
                    {
                        text: '删除',
                        onClick: function() {
                            $.getJSON(deleteHeadlineURL + '/' + headlineId, function (data) {
                                if (data.success) {
                                    $.toast("删除成功");
                                    window.location.href = '/campus_life/admin/headline';
                                }else {
                                    $.toast(data.errMsg);
                                }
                            })
                        }
                    }
                ];
                var buttons2 = [
                    {
                        text: '取消',
                        bg: 'danger'
                    }
                ];
                var groups = [buttons1, buttons2];
                $.actions(groups);
            });
        }else {
            $.toast(data.errMsg);
        }
    });

});