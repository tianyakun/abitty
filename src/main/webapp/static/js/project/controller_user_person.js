module.exports = function(ctx, tpl){

    function render(res, tpl){
        var topBarHtml, html,optionHtml,
        optionTpl = [
            "<option {{? it.item.gender == 'm'}}selected{{?}} value=\"m\" >男</option>",
            "<option {{? it.item.gender == 'f'}}selected{{?}} value=\"f\" >女</option>",
            "<option {{? it.item.gender == 's'}}selected{{?}} value=\"s\" >保密</option>"
        ].join("")
        $Config = $.extend($Config, {back: true, title: '个人信息', uid: window.localStorage["uid"]});
        topBarHtml = $Prime.render(tpl.topBar, $Config);
        html = $Prime.render(tpl.user_person, res.data);
        optionHtml = $Prime.render(optionTpl, res.data);
        html = html.replace(/\[option\]/, optionHtml);
        var buttomTabHtml = $Prime.render(tpl.buttomTab, {active: 'user'});
        html = topBarHtml +  html +  buttomTabHtml;
        $Prime.SPAWrapper("app").html(html);

    }

    function bindUpdate(){
        $("select[name='gender'],input[name='birthday']").on("change", function(){
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
                    _this.addClass("pending");
                }
            }).done(function(res){
                $Prime.isAccess(res);
                if(res.retCode!=000000){
                    alert(res.retMsg);
                    return;
                }

            }).fail(function(){

            }).always(function(){
                _this.removeClass("pending");
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