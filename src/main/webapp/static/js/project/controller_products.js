/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){

    function bindStoreSelect(){
        $("#J_list").on("click", ".J_item", function(e){
            var id = $(this).data("id");
            var idSelector = "#J_json_"+id;
        })
    }


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
         window.sessionStorage[StorageKey+"ProductHtml"] = html;
         bindStoreSelect();
    }

    var StorageKey = ctx.params.id+"_";
    if( window.sessionStorage[StorageKey+"ProductHtml"]){
        $Prime.SPAWrapper("app").html( window.sessionStorage[StorageKey+"ProductHtml"]);
        bindStoreSelect();
        return;
    }


    var api = $Config.root + "/product/list/"+ctx.path.split("/")[2];
    $.ajax({
      url: api,
      type: "GET",
      beforeSend: function(){}
    }).done(function(res){
        render(tpl, res);
    }).fail(function(){

    });
}