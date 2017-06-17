package com.abitty.utils;

/**
 * Created by yak on 17/6/17.
 */
public class Constants {

    public class OrderState {
        public static final int INITIAL = 0; //初始化
        public static final int PAID = 1; //已支付
        public static final int SERVING = 2; //配送服务中
        public static final int FINISH = 3; //订单完成
        public static final int CANCEL = 4; //订单取消
    }

    public class SubOrderState {
        public static final int INITIAL = 0; //待发货
        public static final int SENDING = 1; //已发货
        public static final int ARRIVED = 2; //已签收
    }

    public class DeliveryType {
        public static final String DAILY = "daily";
        public static final String WEEKLY = "weekly";
        public static final String MONTHLY = "monthly";
        public static final String YEARLY = "yearly";
    }

    public class ProductState {
        public static final int NEWLY = 0; //新增
        public static final int ON_SALE = 1; //上架
        public static final int SOLD_OUT = 2; //下架
        public static final int DELETE = 3; //删除
    }

}
