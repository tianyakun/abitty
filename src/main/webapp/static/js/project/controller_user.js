module.exports = function(ctx, tpl){
    function render(tpl){
        var topBarHtml, html;
        $Config = $.extend($Config, {back: true, title: '个人信息'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = topBarHtml + $Prime.render(tpl.user, $Config);
        $Prime.SPAWrapper("app").html(html);
    }



    //function bind(){
    //    $("#J_login_out").on("click", function(){
    //        var _this = $(this);
    //        $.ajax({
    //            url: $Config.root + "/logout",
    //            t
    //        });
    //    })
    //}

    render(tpl);
}