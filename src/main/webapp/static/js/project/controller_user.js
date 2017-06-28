module.exports = function(ctx, tpl){

    function render(tpl){
        if( window.sessionStorage.UserHtml){
            $Prime.SPAWrapper("app").html( window.sessionStorage.UserHtml);
            return;
        }

        var topBarHtml, html;
        $Config = $.extend($Config, {back: true, title: '个人信息'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = topBarHtml +  tpl.user;
        $Prime.SPAWrapper("app").html(html);
        window.sessionStorage.UserHtml = html;

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