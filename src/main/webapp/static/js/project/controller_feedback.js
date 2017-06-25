module.exports = function(ctx, tpl){
    if( window.sessionStorage.feedBackHtml){
        $Prime.SPAWrapper("app").html( window.sessionStorage.feedBackHtml);
        return;
    }


    function render(tpl){
        var html = tpl.feedBack
        $Prime.SPAWrapper("app").html(html);
        window.sessionStorage.feedBackHtml = html;
    }

    render(tpl);
}