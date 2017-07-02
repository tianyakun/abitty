require('../../css/common.css');
require('../../css/service_page.css');

window.page = require('../prime/page');
window.$Prime = require('../prime/util');


$(function(){

    function getTpl(filename){
        return require("../tpl/"+filename)
    }
    var includes = {
        "topBar": getTpl("topBar.html"),
        "pageTip": getTpl("pageTip.html"),
        "buttomTab": getTpl("bottomTab.html")
    }
    var tpl = {
        "products": {
            "list": getTpl("productsList.html"),
            "detail": getTpl("productDetail.html")
        },
        "myService": getTpl("myService.html"),
        "supports": getTpl("productsSupport.html"),
        "productsDetail": getTpl("products_detail.html"),
        "select": getTpl("select.html"),
        "feedBack": getTpl('feedback.html'),
        "user": getTpl('user.html'),
        "user_person": getTpl('user_person.html'),
        "login": getTpl('login.html')
    }
    tpl = $.extend(tpl,includes);

    function setBg(color){
        $("body").css("background-color", color);
    }

    //前端权限校验跳转有弊端,必须等到JS, DOM加载完毕后才能跳转
    function isLogin(ctx, next){
        if(!$Config.uid){
            page.redirect("/view/login?redirect="+ window.location);
        }else{
            next();
        }
    }


    page('/view/login', function(ctx){
       // $Prime.SPAWrapper("app").html("");
        require('./controller_my_login')(ctx, tpl);
        setBg("transparent");
    });

    //当前用户订购服务列表
    page('/view/myService', function(ctx){
       // $Prime.SPAWrapper("app").html("");
        require('./controller_my_service')(ctx, tpl);
        setBg("transparent");
    })

    //当前用户订购服务详情
    page('/view/myService/:id', function(ctx){
       // $Prime.SPAWrapper("app").html("");
        require('./controller_my_service_detail')(ctx, tpl);
        setBg("transparent");
    })

    //APP服务列表 EX: 纸巾,酸奶
    page('/view/supports', function(ctx){
        require('./controller_support')(ctx, tpl);
        setBg("transparent");
    })

    //服务产品列表 EX: A纸巾,B纸巾
    page('/view/supports/:id', function(ctx){
        //$Prime.SPAWrapper("app").html("");
        require('./controller_products')(ctx, tpl);
    })

    page('/view/products/:id', function(ctx){
       // require('./controller_products_detail')(ctx, tpl);
        setBg("transparent");
    });

    //服务需求填写
    page('/view/select', isLogin,  function(ctx){

       // $Prime.SPAWrapper("app").html("");
        require('./controller_select')(ctx, tpl);
        setBg("transparent");
    })

    //服务下单
    page('/view/book',  function(ctx){
       // $Prime.SPAWrapper("app").html("");
        require('./controller_book')(ctx, tpl);
        setBg("#f4f4f4");
    })

    //服务需求反馈
    page('/view/feedback', function(ctx){
        //$Prime.SPAWrapper("app").html("");
        require('./controller_feedback')(ctx,tpl);
        setBg("transparent");
    });


    page('/view/user', function(ctx){
        $Prime.SPAWrapper("app").html("");
        require('./controller_user')(ctx, tpl);
        setBg("#f4f4f4");
    });


    page('/view/user/person', function(ctx){
       // $Prime.SPAWrapper("app").html("");
        require('./controller_user_person')(ctx, tpl);
        setBg("#f4f4f4");
    });

    page();


})

