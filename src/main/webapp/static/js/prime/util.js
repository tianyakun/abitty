var doT = require('./doT');
module.exports = {
    render: function(tpl, data){
        var tempFn = doT.template(tpl);
        return tempFn(data);
    },
    SPAWrapper: function(id){
        return $("#"+id);
    },
    getUrlParam: function(name){
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return decodeURIComponent(r[2]);
        return null; //返回参数值
    },
    isAccess: function(res){
        console.log(typeof res);
        var b = false;
        if(res.retCode == 100009){
            var redirect = window.location.href;
           // window.location.href = "/loginIndex?redirect="+redirect;
            page.redirect("/view/login?redirect="+redirect);
            b = true;
            console.log('登陆失效...');
        }
        return b;
    },
    getUrlAllParam: function(){
        var obj = {};
        var r = window.location.search.substr(1);
        if (r == '') return obj;
        var arr = r.split('&');
        for (var i = 0; i < arr.length; i++) {
            var t = arr[i].split('=');
            if (t[0] in obj) {

            } else {
                obj[t[0]] = decodeURIComponent(t[1]);
            }
        }
        return obj;
    }

}
