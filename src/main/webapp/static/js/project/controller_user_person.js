module.exports = function(ctx, tpl){
    $("body").css("background-color", "#f4f4f4");
    function render(tpl){

        var topBarHtml, html;
        $Config = $.extend($Config, {back: true, title: '个人信息'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = topBarHtml +  tpl.user_person;
        $Prime.SPAWrapper("app").html(html);

    }
    render(tpl);
}