/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){




    function render(tpl, res){
        res = typeof res == "string"?JSON.parse(res):res;
        if(res.retCode != 000000){
            alert(res.retMsg);
            return;
        }
         var title, html, topBarHtml;
         title  = $Prime.getUrlParam("title");
         html = $Prime.render(tpl.products.list, res.data);
         $Config = $.extend($Config, {back: true, title: title});
         topBarHtml = $Prime.render(tpl.topBar, $Config);
         html = topBarHtml+html;
         $Prime.SPAWrapper("app").html(html);

    }



    var api = $Config.root + "/product/list/"+ctx.path.split("/")[ctx.path.split("/").length-1];
    $.ajax({
      url: api,
      type: "GET",
      beforeSend: function(){}
    }).done(function(res){
        render(tpl, res);
    }).fail(function(){

    });
}