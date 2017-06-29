module.exports = function(ctx, tpl){

    function render(res, tpl){
        var topBarHtml, html,optionHtml,
        optionTpl = [
            "<option {{? it.item.gender == 'm'}}selected{{?}} value=\"m\" >男</option>",
            "<option {{? it.item.gender == 'f'}}selected{{?}} value=\"f\" >女</option>",
            "<option {{? it.item.gender == 's'}}selected{{?}} value=\"s\" >未知</option>"
        ].join("")
        $Config = $.extend($Config, {back: true, title: '个人信息'});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = $Prime.render(tpl.user_person, res.data);
        optionHtml = $Prime.render(optionTpl, res.data);
        html = html.replace(/\[option\]/, optionHtml);
        html = topBarHtml +  html
        $Prime.SPAWrapper("app").html(html);

    }

    function bindUpdate(){
        $("#J_save").on("click", function(){
            var _this = $(this);
            if(_this.hasClass("pending") ) return;
            $.ajax({
                url: $Config.root + "/my/account/update",
                type: "POST",
                data: {
                    birthday: $("input[name='birthday']").val(),
                    gender: $("select[name='gender']").val()
                },
                beforeSend: function(){
                    _this.addClass("pending").text("保存中...");
                }
            }).done(function(res){
                $Prime.isAccess(res);
                if(res.retCode!=000000){
                    alert(res.retMsg);
                    return;
                }

            }).fail(function(){

            }).always(function(){
                _this.removeClass("pending").text("保存");
            });
        })
    }



    $.ajax({
        url: $Config.root + "/my/account",
        type: "GET",
        beforeSend: function(){}
    }).done(function(res){
        if($Prime.isAccess(res)){
            return;
        }
        if(res.retCode!=000000){
            alert(res.retMsg);
            return;
        }

        render(res, tpl);
        bindUpdate();
    }).fail(function(){
    });




}