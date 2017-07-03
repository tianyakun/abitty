module.exports = function(ctx, tpl){
    function render(tpl){
        var topBarHtml, html;
        $Config = $.extend($Config, {back: true, title: '个人信息'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        var buttomTabHtml = $Prime.render(tpl.buttomTab, {active: 'user'});
        html = topBarHtml + $Prime.render(tpl.user, $Config) +  buttomTabHtml;
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