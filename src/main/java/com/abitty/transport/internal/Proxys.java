package com.abitty.transport.internal;

import com.ning.http.client.ProxyServer;
import com.ning.http.client.ProxyServerSelector;
import com.ning.http.util.ProxyUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Proxys
 *
 * @author andang.ye
 */
public class Proxys {

    private static final Logger LOGGER = LoggerFactory.getLogger(Proxys.class);

    private static Map<String,String> PROXY_CONFIG;

    private static HttpHost PROXY_HOST;

    private static ProxyServerSelector PROXY_SERVER_SELECTOR;

    static {
        try {
//            PROXY_CONFIG = MapConfig.get("proxy_config.properties").asMap(); todo
            PROXY_HOST = new HttpHost(PROXY_CONFIG.get("proxyHost"), Integer.parseInt(PROXY_CONFIG.get("port")));
            PROXY_SERVER_SELECTOR = ProxyUtils.createProxyServerSelector(new ProxyServer(PROXY_CONFIG.get("proxyHost"), Integer.parseInt(PROXY_CONFIG.get("port"))));
        } catch (Throwable e) {
            LOGGER.warn("未正确配置代理,如果需要,请参考:");
        }
    }

    public static HttpHost getProxyHost() {
        return PROXY_HOST;
    }

    public static ProxyServerSelector getProxyServerSelector(){
        return PROXY_SERVER_SELECTOR;
    }

    public static boolean isProxyOn() {
        return PROXY_CONFIG!=null && "on".equalsIgnoreCase(PROXY_CONFIG.get("proxySwitch"));
    }
}
