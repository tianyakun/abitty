/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){
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
        }).done(function(){

        }).fail(function(){
            alert("服务器发生未知错误,请稍后重试");
        })
    }
    function bindCreateOrder(){
        $("#J_pay").on("click", function(){
            var _this = $(this);
            if(_this.hasClass("pending")) return;
            $.ajax({
                url: $Config.root + "/order/create",
                type: "POST",
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