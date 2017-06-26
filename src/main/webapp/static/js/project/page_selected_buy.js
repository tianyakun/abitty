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
        "user_person": getTpl('user_person.html')
    }
    tpl = $.extend(tpl,includes);


    //前端权限校验跳转有弊端,必须等到JS, DOM加载完毕后才能跳转
    function isLogin(ctx, next){
        !$Config.uid ? location.href="/loginIndex?redirect="+ window.location : next();
    }

    //当前用户订购服务列表
    page('/view/myService', isLogin, function(ctx){
        require('./controller_my_service')(ctx, tpl);
    })

    //当前用户订购服务详情
    page('/view/myService/:id', isLogin, function(ctx){
        require('./controller_my_service_detail')(ctx, tpl);
    })

    //APP服务列表 EX: 纸巾,酸奶
    page('/view/supports', function(ctx){
        require('./controller_support')(ctx, tpl);
    })

    //服务产品列表 EX: A纸巾,B纸巾
    page('/view/supports/:id', function(ctx){
        require('./controller_products')(ctx, tpl);
    })

    page('/view/products/:id', function(ctx){
        require('./controller_products_detail')(ctx, tpl);
    });

    //服务需求填写
    page('/view/select', isLogin, function(ctx){
        require('./controller_select')(ctx, tpl);
    })

    //服务下单
    page('/view/book', isLogin, function(ctx){
        require('./controller_book')(ctx, tpl);
    })

    //服务需求反馈
    page('/view/feedback', isLogin, function(ctx){
        require('./controller_feedback')(ctx,tpl);
    });


    page('/view/user', isLogin, function(ctx){
        require('./controller_user')(ctx, tpl);
    });


    page('/view/user/person', isLogin, function(ctx){
        require('./controller_user_person')(ctx, tpl);
    });

    page();


})

