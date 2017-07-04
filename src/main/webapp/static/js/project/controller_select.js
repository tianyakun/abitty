/**
 * Created by yang on 17/5/26.
 */

module.exports = function(ctx, tpl){

    window.sessionStorage.selectPath = ctx.path;


    function formDataToJson(formdata){
        var result = {},i=0;
        formdata = formdata.split("&");
        for(; i < formdata.length; i++){
            var s = formdata[i].split("=");
            result[s[0]] = decodeURI(s[1]);
        }
        return result;
    }

    function userSelected(id){
        this.wrapper = $("#"+id);
        this.opReduce = this.wrapper.find("[data-op='reduce']");
        this.opAdd = this.wrapper.find("[data-op='add']");
        this.oCount = this.wrapper.find("[data-type='count']");
        this.oResult = this.wrapper.find("[data-type='result']");
        this.data = {
            count: 1
        };
        this.init();
    }
    $.extend(userSelected.prototype, {
        init: function(){
            var self = this;
            this.wrapper.on("tap", "[data-ui='btn']", function(e){
                var type = $(this).data("op");
                switch(type){
                    case "reduce":
                        if(self.data.count==1) return;
                        self.data.count--;
                        break;
                    case "add":
                        self.data.count++;
                        break;
                }
                self.oCount.text(self.data.count);
                self.oResult.val(self.data.count);

            })

        }
    });


    function userSelectTag(id){
        this.wrapper = $("#"+id);
        this.result = this.wrapper.find("[data-type='result']");
        this.data = {
            current: this.wrapper.find("[data-selected='1']")
        }
        this.init();
    }

    $.extend(userSelectTag.prototype, {
        init: function(){
            var self = this;
            this.wrapper.on("tap", "[data-ui='btn']", function(e){
                var _this = $(this);
                self.data.current.removeClass("user-checkbox-selected").data("selected", "0");
                _this.addClass("user-checkbox-selected").data("selected", "1");
                self.data.current = _this;
                self.result.val(_this.data("val"));

            })
        }
    });


    function userSelectMutiple(id){
        var self = this;
        this.wrapper = $("#"+id);
        this.result = this.wrapper.find("[data-type='result']");
        this.data = {
            list: JSON.parse(self.result.val())
        }
        this.init();
    }

    $.extend(userSelectMutiple.prototype, {
        init: function(){
            var self = this;
            this.wrapper.on("tap", "[data-ui='btn']", function(e){
                var _this = $(this);
                _this.toggleClass("user-checkbox-selected");
            })
        }
    });

    function caculate(){
        $("#J_select").on("click", function(){
            var params = $("#J_form").serialize();
            params = formDataToJson(params);
            console.log(params.serviceAtomCount);
            var currentBook = JSON.parse(window.sessionStorage["currentBook"]);
            currentBook = $.extend(currentBook, params);
            window.sessionStorage.currentBook = JSON.stringify(currentBook);
        })
    }

    //订单总金额 = 单价 * 月数 * 件数
    function caculatePrice(){
        try{
            var currentBook = window.sessionStorage["currentBook"]; //避免隐私模式报错
        }catch(e){
            return;
        }
        currentBook       = JSON.parse(currentBook);
        var price         = currentBook.price;
        var timeDom       = $("#J_time");
        var countDom      = $("#J_count");
        var totalDom      = $("#J_total");
        var subQuantity   = $("input[name='subQuantity']");    //单次配送数量
        var totalSub      = $("input[name='totalSub']");       //配送次数
        var totalAmount   = $("input[name='totalAmount']");    //订单总金额
        var totalQuantity = $("input[name='totalQuantity']");  //商品总数
        var totalMouth    = $("input[name='totalMouth']");
        var serviceAtomCount = $("input[name='serviceAtomCount']");


        timeDom.on("change", function(){
            var _this = $(this), _val = _this.val().split('|'), _count = countDom.val();
            var totalPrice = price * _val[0] * _count;
            totalDom.text(totalPrice);
            totalAmount.val(totalPrice);            //订单总金额
            totalSub.val(_val[1]);                  //配送次数
            totalQuantity.val(_count * _val[1]);    //商品总数
            subQuantity.val(_count);                //单次配送数量
            totalMouth.val(_val[0]);
            serviceAtomCount.val(_val[0]);
        });

        countDom.on("change", function(){
            var _this = $(this), _val = _this.val(), time = timeDom.val().split('|');
            var totalPrice = price * time[0] * _val;
            totalDom.text(totalPrice);
            totalAmount.val(totalPrice);    //订单总金额
            totalSub.val(time[1])           //配送次数
            subQuantity.val(_val);          //单次配送数量
            totalQuantity.val(_val * time[1]);  //总数
            totalMouth.val(time[0]);
            serviceAtomCount.val(time[0]);
        });

        //初始化触发一次默认价格计算
        timeDom.trigger("change");

    }


    function initUserSelect(){
        new userSelected("J_user_select_person");
        new userSelected("J_user_select_count");
        new userSelectTag("J_user_select_time");
        caculate();
        caculatePrice();
    }



    if( window.sessionStorage.SelectHtml){
        initUserSelect();
        return;
    }


    function render(tpl){
        var currentBook = window.sessionStorage["currentBook"];
        if(!currentBook){
            location = "/myService";
            return;
        }

        currentBook = JSON.parse(currentBook);

        var html = $Prime.render(tpl.select, currentBook);
        $Config = $.extend($Config, {back: true, title: "一点生活"})
        var topBarHtml = $Prime.render(tpl.topBar, $Config);
        var pageTip = $Prime.render(tpl.pageTip, {pageTip: "填写需求"});
        html = topBarHtml + pageTip + html;
        $Prime.SPAWrapper("app").html(html);
        initUserSelect();
    }
    render(tpl);


}