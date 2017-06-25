module.exports = function(ctx, tpl){

    function render(tpl){
        $("body").css("background-color", "#f4f4f4");
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
    render(tpl);
}