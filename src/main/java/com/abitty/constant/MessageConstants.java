package com.abitty.constant;

/**
 * Created by yak on 17/6/20.
 */
public class MessageConstants {

    public final static String ACCESS_ID = "LTAIJKNcPw0A7USD";
    public final static String ACCESS_KEY = "uYC2IZJ4y73rKmLgYIN8yRzX1o7dLp";
    public final static String MNS_ENDPOINT = "http://1188864690562589.mns.cn-beijing.aliyuncs.com/";
    public final static String TOPIC = "sms.topic-cn-beijing";
    public static final String SIGN_NAME = "一点生活Abitty";
    public final static String TEMPLATE_CODE = "SMS_71341019";

    public final static String MESSAGE_CHANNEL = "aliyun-mns";

    public static class SendStatus {
        public static final int INIT = 0; //初始化
        public static final int PENDING = 1; //处理中
        public static final int SUCCESS = 2; //发送成功
        public static final int FAILED = 3; //发送失败
    }

    public static class CodeValidateStatus {
        public static final int NO_VERIFY = 0; //未校验
        public static final int VERIFIED = 1; //已校验
        public static final int INEFFECTIVE = 2; //已失效
    }
}
