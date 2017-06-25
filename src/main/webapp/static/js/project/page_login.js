$(function(){
    var Jform = $("#J_login_form"),
        Jphone = Jform.find("input[name='phone']"),
        Jvcode = Jform.find("input[name='vcode']"),
        JmessageId,
        Jcode = $("#J_code"),
        timer = null;
        regPhone = /^1[34578]\d{9}$/;
        errorMsg = {
            phone: "请输入正确的手机号",
            vcode: "请输入验证码",
            serverErr: "服务器发生未知错误,请稍后重试"
        };
    Jform.on("submit", function(){
        if(checkForm()){
            $.ajax({
                url: $Config.root + "/login",
                type: "POST",
                data: {
                    phone: Jphone.val(),
                    verifyCode: Jvcode.val(),
                    messageId: JmessageId
                }
            }).done(function(res){
                if(res.retCode == 000000){
                    location.href = "/view/myService"
                }else{
                    alert(res.retMsg);
                }
            });
        }
    });

    function checkForm(){
        var isValidate = false;
        if(!regPhone.test(Jphone.val())){
            alert(errorMsg.phone);
        }else if(Jvcode.val() == ""){
            alert(errorMsg.vcode);
        }else{
            isValidate = true;
        }
        return isValidate;
    }

    function setTimer(el){
        var i = 59;
        var timer = null;
        if(timer) return;
        el.addClass("counting").text(i+"秒");
        timer = setInterval(function(){
            if(i==0){
                clearInterval(timer);
                _this.removeClass("counting");
                return;
            }
            el.text(--i+"秒");
        }, 1000)
    }

    Jcode.on("click", function(){
        var _this = $(this);

        if(!regPhone.test(Jphone.val())){
            alert(errorMsg.phone);
            return;
        }
        if(_this.hasClass("pending") || _this.hasClass("counting")) return;
        $.ajax({
            url: $Config.root + "/verify/send",
            type: "GET",
            data: {
                phone: Jphone.val()
            },
            beforeSend: function(){
                _this.addClass("pending");
            }
        }).done(function(res){
            if(res.retCode == 000000){
                setTimer(_this);
                JmessageId = res.data.messageId;
            }else{
                alert(res.retMsg);
            }

        }).fail(function(){
            alert(errorMsg.serverErr);
        }).always(function(){
            _this.removeClass("pending");
        });
    })




});