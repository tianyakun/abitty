package com.abitty.transport.http;

import com.google.common.base.Joiner;

/**
 * @author litingpeng
 */
public class HttpConstants {

    /**
     * 默认报警阈值，当池中可用连接数小于5% 则报警
     */
    public static final double SHRESHOLD = 0.02;

    /*
     * 默认超时
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;

    /*
     * 默认连接超时
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 30000;

    /**
     * 默认连接池取连接超时时间
     */
    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 3000;

    /**
     * 单个路由默认最大连接数
     */
    public static final int DEFAULT_MAX_PER_ROUTE = 20;

    /**
     * 连接池默认最大连接数
     */
    public static final int DEFAULT_MAX_TOTAL = 50;

    public static final String GBK_CHARSET = "GBK";

    /**
     * UA
     */
    public static final String CUSTOM_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0";

    public static final Joiner.MapJoiner URL_JOINER = Joiner.on("&").withKeyValueSeparator("=");
}
