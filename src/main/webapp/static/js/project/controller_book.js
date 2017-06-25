/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){

    var access = null;

    function render(tpl, res){
        var data = JSON.parse(window.sessionStorage.currentBook);
        $Config = $.extend($Config, {back: true, title: data.name});
        console.log(data);
        var html = $Prime.render(tpl.products.detail, data);
        var topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = topBarHtml+html;
        $Prime.SPAWrapper("app").html(html);
    }



    function getAccess(){
        $.ajax({
            url: $Config.root + "/order/create",
            type: "GET",
            data: {code : $Prime.getUrlParam("code")},
            beforeSend: function(){}
        }).done(function(res){
            if(res.retCode != 000000){
                alert(res.retMsg);
                return;
            }

            access = res.data;

        }).fail(function(){
            alert("服务器发生未知错误,请稍后重试");
        })
    }
    function bindCreateOrder(){

        $("#J_pay").on("click", function(){
            var _this = $(this);
            if(_this.hasClass("pending")) return;
            var currentBook = JSON.parse(window.sessionStorage["currentBook"]);
            $.ajax({
                url: $Config.root + "/order/create",
                type: "POST",
                data: {
                    productNo:      currentBook.productNo,
                    totalQuantity:  currentBook.totalQuantity,
                    totalAmount:    currentBook.totalAmount * 100,
                    deliveryType:   currentBook.deliveryType,
                    subQuantity:    currentBook.subQuantity,
                    totalSub:       currentBook.totalSub,
                    remark:         currentBook.remark,

                    receiverName:  "老杨",
                    phoneNumber:   $Config.uid,
                    addressProvince: "北京",
                    addressCity:   "北京市",
                    addressArea:   "昌平区",
                    addressDetail: "龙域中路融泽嘉园1号院",
                    postcode:      "102200"
                },
                beforeSend: function(){
                    _this.addClass("pending");
                }
            })
            .done(function(res){

            })
            .always(function(){
                _this.removeClass("pending");
            });
        })
    }

    render(tpl);

    //微信开发权限获取
    getAccess();


}