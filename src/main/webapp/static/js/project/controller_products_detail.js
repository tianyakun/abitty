module.exports = function(ctx, tpl){


    var currentData = null;
    function render(res, tpl){
        if(res.retCode != 000000){
            alert(res.retMsg);
            return;
        }
        var topBarHtml, html;
        $Config = $.extend($Config, {back: true, title: '一点生活'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);

        html = topBarHtml + $Prime.render( tpl.productsDetail, res.data);
        $Prime.SPAWrapper("app").html(html);
        currentData = res.data.item;
    }

    function bind(){
        $("#J_start_book").on("click", function(){
            window.sessionStorage["currentBook"] = JSON.stringify(currentData);
        })
    }
    console.log();

   
    $.ajax({
        url: $Config.root + "/product/detail/"+ ctx.params.id,
        type: "GET",
        data: {
            __: new Date().getTime()
        },
        beforeSend: function(){}
    }).done(function(res){
       render(res, tpl);
       bind();
    }).fail(function(){

    });

}