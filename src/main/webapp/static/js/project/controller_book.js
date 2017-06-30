/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){

    var access = null;
    var code;

    function render(tpl, res){
        var data = JSON.parse(window.sessionStorage.currentBook);
        $Config = $.extend($Config, {back: true, title: "一点生活"});
        var html = $Prime.render(tpl.products.detail, data);
        var topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = topBarHtml+html;
        $Prime.SPAWrapper("app").html(html);
    }

    function payCall(data){
        WeixinJSBridge.invoke(
            "getBrandWCPayRequest",
            {
                appId: access.appid,
                timeStamp: data.timeStamp,
                nonceStr: data.nonceStr,
                package: data.package,
                signType: data.signType,
                paySign: data.paySign
            },
            function(r){
                if(r.err_msg == "get_brand_wcpay_request:ok" ) {}
            }
        );
    }

    function getAccess(){
        $.ajax({
            url: $Config.root + "/wechat/ticket",
            data: {
                ticketUrl: window.location.href
            },
            type: "GET",
            beforeSend: function(){}
        }).done(function(res){

            if($Prime.isAccess(res)){
                return;
            }

            if(res.retCode != 000000){
                alert(res.retMsg);
                return;
            }
            access = res.data;
            //微信配置
            wx.config({
                appId:  res.data.appid,
                timestamp:  res.data.timestamp,
                nonceStr:  res.data.noncestr,
                signature: res.data.signature,
                jsApiList: [
                    'chooseWXPay'
                ]
            });


        }).fail(function(){
            alert("服务器发生未知错误,请稍后重试");
        })
    }


    function bindGetAdress(){
        $("#J_get_adress").on("click", function(){
            wx.openAddress({
                success: function(res){
                    $("#J_userName").text(res.userName); $("input[name='receiverName']").val(res.userName);
                    $("#J_telNumber").text(res.telNumber); $("input[name='phoneNumber']").val(res.phoneNumber);
                    $("#J_recive_adress").text(res.provinceName+" "+res.cityName+" "+res.countryName);
                    $("input[name='addressProvince']").val(res.provinceName);
                    $("input[name='addressCity']").val(res.cityName);
                    $("input[name='addressArea']").val(res.countryName);
                    $("#J_detailInfo").text(re.detailInfo); $("input[name='addressDetail']").val(res.detailInfo);
                    $("#J_postalCode").text(re.postalCode); $("input[name='postcode']").val(res.postalCode);
                }
            });
        })
    }

    function bindCreateOrder(){
        $("#J_pay").on("click", function(){
            var _this = $(this);
            if(_this.hasClass("pending")) return;
            var currentBook = JSON.parse(window.sessionStorage["currentBook"]);
            $.ajax({
                url: $Config.root + "/order/confirm",
                type: "POST",
                data: {
                    productNo:        currentBook.productNo,
                    totalQuantity:    currentBook.totalQuantity,
                    totalAmount:      currentBook.totalAmount,
                    deliveryType:     currentBook.deliveryType,
                    subQuantity:      currentBook.subQuantity,
                    totalSub:         currentBook.totalSub,
                    remark:           currentBook.remark,
                    serviceAtomCount: currentBook.serviceAtomCount,
                    openidCode:       $Prime.getUrlParam("code"),
                    receiverName:     $("input[name='receiverName']").val(),
                    phoneNumber:      $("input[name='phoneNumber']").val(),
                    addressProvince:  $("input[name='addressProvince']").val(),
                    addressCity:      $("input[name='addressCity']").val(),
                    addressArea:      $("input[name='addressArea']").val(),
                    addressDetail:    $("input[name='addressDetail']").val(),
                    postcode:         $("input[name='postcode']")
                },
                beforeSend: function(){
                    _this.addClass("pending");
                }
            })
            .done(function(res){

                if($Prime.isAccess(res)){
                    return;
                }
                if(res.retCode!=000000){
                    alert(res.retMsg);
                    return;
                }

                if(typeof WeixinJSBridge == 'undefined'){
                    document.addEventListener('WeixinJSBridgeReady', payCall, false);
                }else{
                    payCall(res.data);
                }
            })
            .always(function(){
                _this.removeClass("pending");
            });
        })
    }

    render(tpl);

    //微信开发权限获取
    getAccess();

    bindCreateOrder();


}