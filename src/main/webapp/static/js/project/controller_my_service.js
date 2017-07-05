module.exports = function(ctx, tpl){
    function render(tpl, res){
        var html = $Prime.render(tpl.myService, res.data);
        if(res.data.list.length != 0){
            $Config = $.extend($Config, {back: false, title: "", uid: window.localStorage["uid"] } );
            var topBarHtml = $Prime.render(tpl.topBar, $Config );
            var buttomTabHtml = $Prime.render(tpl.buttomTab, {active: 'myservice'});
            html = topBarHtml+html+ buttomTabHtml;
        }
        $Prime.SPAWrapper("app").html(html);
        bindAction();
    }


    function bindAction(){
        $("#J_list").on("click", ".J_start_service", function(){
            var btn = $(this);
            if(btn.hasClass("pending")) return;
            $.ajax({
                url: "/api/v1/startService",
                type: "get",
                data: {
                    id: btn.data("id")
                },
                beforeSend: function(){
                    btn.addClass("pending");
                }
            }).done(function(res){
                if(res.errno == 10000) window.location.reload();
            }).fali(function(){

            })
        });

        $("#J_book").on("click", function(){
            var order = $(this).data("order");
            window.sessionStorage["currentBook"] = $("#J_"+order).val();
            return true;
        })
    }


    $.ajax({
        url: $Config.root + "/order/list",
        type: "GET",
        data: {_:new Date().getTime()},
        beforeSend: function(){

        }
    }).done(function(res){
        if($Prime.isAccess(res)){
            return;
        }
        if(res.retCode != 000000){
            alert(res.retMsg);
            return;
        }
        render(tpl, res);
    }).fail(function(){

    });



}