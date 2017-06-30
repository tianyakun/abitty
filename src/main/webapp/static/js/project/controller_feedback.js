module.exports = function(ctx, tpl){

    function render(tpl){
        var html = tpl.feedBack
        $Prime.SPAWrapper("app").html(html);
    }

    function bind(){
        var ipt = $("input[name='feedBackUrl']");
        $("#J_save").on("click", function(){
            var _this = $(this);
            if(_this.hasClass("pending")) return;
            if(!ipt.val()) return;
            if(ipt.val().length>1024){
                alert("字数太多啦");
            }
            $.ajax({
                url: $Config.root + "/feedback",
                type: "POST",
                data: {content: ipt.val()},
                beforeSend: function(){
                    _this.addClass("pending");
                }
            }).done(function(res){
                if(res.retCode != 000000){
                    alert(res.retMsg);
                    return;
                }
                alert("我们已收到您的需求,我们会加紧准备.");
                window.location.href = "/view/supports"
            }).always(function(){
                _this.removeClass("pending");
            })
        });
    }

    render(tpl);
    bind();
}