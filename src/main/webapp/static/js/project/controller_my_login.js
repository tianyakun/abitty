module.exports = function(ctx, tpl){
    var Jform,
        Jphone,
        Jvcode,
        Jcode,
        timer;
    $.ajax({
        url: $Config.root + '/auth',
        type: "GET",
        beforeSend: function(){}
    }).done(function(res){
        if(res.retCode == 000000){
            page.redirect("/view/supports");
            //page({dispatch: false});
        }else{
            render(tpl);
            bind();
        }
    });

    function render(tpl){
        var html = tpl.login
        $Prime.SPAWrapper("app").html(html);
    }


    function bind(){
            Jform = $("#J_login_form"),
            Jphone = Jform.find("input[name='phone']"),
            Jvcode = Jform.find("input[name='vcode']"),
            Jcode = $("#J_code"),
            timer = null;
        regPhone = /^1[34578]\d{9}$/;
        errorMsg = {
            phone: "请输入正确的手机号",
            vcode: "请输入验证码",
            serverErr: "服务器发生未知错误,请稍后重试"
        };

        bindSubmit(Jform);
        bindCode(Jcode);
    }




    function getUrlParam(name){
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return decodeURIComponent(r[2]);
        return null; //返回参数值
    }

    function bindSubmit(form){
        form.on("submit", function(){
            if(checkForm()){
                $.ajax({
                    url: $Config.root + "/login",
                    type: "POST",
                    data: {
                        phone: Jphone.val(),
                        verifyCode: Jvcode.val()
                    }
                }).done(function(res){
                    if(res.retCode == 000000){
                        var redirect = getUrlParam("redirect");
                        if(redirect){
                            location.href = redirect;
                        }else{
                            page.redirect("/view/supports?t=" + new Date().getTime());
                        }

                    }else{
                        alert(res.chineseMsg);
                    }
                });
            }
            return false;
        });
    }



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
                el.removeClass("counting").text("发送验证码");
                return;
            }
            el.text(--i+"秒");
        }, 1000)
    }

    function bindCode(code){
        code.on("click", function(){
            var _this = $(this);
            if(!regPhone.test(Jphone.val())){
                alert(errorMsg.phone);
                return;
            }
            if(_this.hasClass("pending") || _this.hasClass("counting")) return;
            $.ajax({
                url: $Config.root + "/verify/send/"+Jphone.val(),
                type: "GET",
                beforeSend: function(){
                    _this.addClass("pending");
                }
            }).done(function(res){
                if(res.retCode == 000000){
                    setTimer(_this);
                }else{
                    alert(res.retMsg);
                }

            }).fail(function(){
                alert(errorMsg.serverErr);
            }).always(function(){
                _this.removeClass("pending");
            });
        })
    }






}