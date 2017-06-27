package com.abitty.constant;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;

/**
 * Created by yak on 17/6/23.
 */
public class WechatConstants {

    public static final String APP_ID = "wx6567f481349fba16";
    public static final String SECRET =  "07d89a3665412fce5e113aa26f180a26";
    public static final String MERCHANT_ID = "1480988362";
    public static final String PAY_KEY = "ztmdytnnlwclztmdytnnlwclztmdytnn";
    public static final String TRADE_TYPE = "JSAPI";

    public static final int READ_TIMEOUT = 60000;
    public static final int CONN_TIMEOUT = 60000;

    public static final String CHARSET = Charsets.UTF_8.name();

    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public static final String OPEN_ID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String ABITTY_CODE_URL = "http://www.abitty.com/?code=";

    public static final String PAY_NOTIFY_URL = "http://www.abitty.com/pay/notify";


}
