/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){

    function bindStoreSelect(){
        $("#J_list").on("click", ".J_item", function(e){
            var id = $(this).data("id");
        })
    }
    function render(tpl, res){
        res = typeof res == "string"?JSON.parse(res):res;
        if(res.retCode != 000000){
            alert(res.retMsg);
            return;
        }
        var html, topBarHtml;

        html = $Prime.render(tpl.supports, res.data);
        $Config = $.extend($Config, {title: "一点生活"});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        var buttomTabHtml = $Prime.render(tpl.buttomTab, {active: 'supports'});
        html = topBarHtml+html + buttomTabHtml;
        $Prime.SPAWrapper("app").html(html);

        bindStoreSelect();
    }

    $.ajax({
        url: $Config.root + "/catalog/list",
        type: "GET",
        beforeSend: function(){
        }
    }).done(function(res){
        render(tpl, res);
    }).fail(function(){

    });
}