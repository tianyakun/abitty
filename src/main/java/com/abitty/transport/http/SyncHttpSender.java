package com.abitty.transport.http;

import com.abitty.transport.internal.Proxys;
import com.abitty.transport.util.HttpUtils;
import com.abitty.transport.util.NamedThreadFactory;
import com.abitty.transport.util.SSLUtils;
import com.google.common.base.Strings;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author litingpeng
 */
public class SyncHttpSender {

    private static final Logger logger = LoggerFactory.getLogger(SyncHttpSender.class);

    /**
     * 连接池中连接的过期时间
     */
    private static final int MAX_TIME_TO_LIVE = 5 * 60000;

    private CloseableHttpClient httpClient;

    private RequestConfig proxyRequestConfig;

    private boolean forceProxy = false;
    private boolean useProxy;

    private SyncHttpSender(HttpClient httpClient, boolean useProxy) {
        this(httpClient, HttpConstants.DEFAULT_CONNECT_TIMEOUT, HttpConstants.DEFAULT_SOCKET_TIMEOUT, useProxy);
    }

    private SyncHttpSender(HttpClient httpClient, int connectTimeout, int socketTimeout, boolean useProxy) {
        this.httpClient = (CloseableHttpClient) httpClient;
        this.useProxy = useProxy;
        this.proxyRequestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setProxy(Proxys.getProxyHost())
                .build();
    }

    public static SyncHttpSender createDefaultHttpSender() {
        return new Builder().build();
    }

    public static SyncHttpSender createDefaultHttpSender(boolean useProxy) {
        return new Builder().setUseProxy(useProxy).build();
    }

    public static SyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal) {
        return new Builder().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setMaxPoolTotal(maxPoolTotal).build();
    }


    public static SyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, boolean useProxy) {
        return new Builder().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setMaxPoolTotal(maxPoolTotal).setUseProxy(useProxy).build();
    }

    public String doGet(String url, Map<String, String> queryMap, String charset) {
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(HttpUtils.composeUrl(url, queryMap, charset));
            requestPreprocess(httpGet);

            return httpClient.execute(httpGet, DefaultResponseHandler.determinDefaultResponseHandle(charset));
        } catch (Throwable e) {
            logger.error("SyncHttpSender.doGet出现异常", e);
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
        }
        return null;
    }

    public String doGet(String url, Map<String, String> queryMap, Map<String, String> headerMap, String charset, ResponseHandler<String> responseHandler) {
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(HttpUtils.composeUrl(url, queryMap, charset));
            requestPreprocess(httpGet);

            HttpUtils.addHeader(headerMap, httpGet);

            return httpClient.execute(httpGet, responseHandler);
        } catch (Throwable e) {
            logger.error("SyncHttpSender.doGet出现异常", e);
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
        }
        return null;
    }

    public String doPost(String url, String content, String charset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            requestPreprocess(httpPost);
            HttpUtils.addBody(content, charset, httpPost);

            return httpClient.execute(httpPost, DefaultResponseHandler.determinDefaultResponseHandle(charset));
        } catch (Throwable e) {
            logger.error("SyncHttpSender.doPost出现异常", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public String doPost(String url, Map<String, String> headerMap, String content, String charset, ResponseHandler<String> responseHandler) {
        HttpPost httpPost = new HttpPost(url);
        try {
            requestPreprocess(httpPost);
            HttpUtils.addHeader(headerMap, httpPost);
            HttpUtils.addBody(content, charset, httpPost);

            return httpClient.execute(httpPost, responseHandler);
        } catch (Throwable e) {
            logger.error("SyncHttpSender.doPost出现异常", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public String doPost(String url, Map<String, String> headerMap, Map<String, String> params, String charset, ResponseHandler<String> responseHandler) {
        HttpPost httpPost = new HttpPost(url);
        try {
            requestPreprocess(httpPost);
            HttpUtils.addHeader(headerMap, httpPost);
            HttpUtils.addBody(params, charset, httpPost);

            return httpClient.execute(httpPost, responseHandler);
        } catch (Throwable e) {
            logger.error("SyncHttpSender.doPost出现异常", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private void requestPreprocess(HttpRequestBase httpRequestBase) {
        if (forceProxy || (useProxy && Proxys.isProxyOn())) {
            httpRequestBase.setConfig(proxyRequestConfig);
        }
    }

    /**
     * 设置强制代理配置
     *
     * @param proxyRequestConfig
     */
    public void setForceProxyRequestConfig(RequestConfig proxyRequestConfig) {
        this.forceProxy = true;
        this.proxyRequestConfig = proxyRequestConfig;
    }

    public static class Builder {
        private int socketTimeout = HttpConstants.DEFAULT_SOCKET_TIMEOUT;
        private int connectTimeout = HttpConstants.DEFAULT_CONNECT_TIMEOUT;
        private int connectionRequestTimeout = HttpConstants.DEFAULT_CONNECTION_REQUEST_TIMEOUT;
        private int maxPoolTotal = HttpConstants.DEFAULT_MAX_TOTAL;
        private int maxPerRoute = HttpConstants.DEFAULT_MAX_PER_ROUTE;
        private String keyStoreFile = null;
        private String keyPassword = "";
        private String keyAlgorithm = SSLUtils.ALGORITHM_JKS;
        private String trustStoreFile = null;
        private String trustPassword = "";
        private String trustAlgorithm = SSLUtils.ALGORITHM_JKS;
        private boolean useProxy = false;
        private String protocol = null;//null 时, 默认为TLSv1.0

        public Builder setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setMaxPoolTotal(int maxPoolTotal) {
            this.maxPoolTotal = maxPoolTotal;
            return this;
        }

        public Builder setProtocol(String protocol){
            this.protocol = protocol;
            return this;
        }

        public Builder setKeyStoreSuite(String keyStoreFile, String keyPassword, String keyAlgorithm) {
            this.keyStoreFile = keyStoreFile;
            this.keyPassword = keyPassword;
            this.keyAlgorithm = keyAlgorithm;
            return this;
        }


        public Builder setTrustStoreSuite(String trustStoreFile, String trustPassword, String trustAlgorithm) {
            this.trustStoreFile = trustStoreFile;
            this.trustPassword = trustPassword;
            this.trustAlgorithm = trustAlgorithm;
            return this;

        }

        public Builder setUseProxy(boolean useProxy) {
            this.useProxy = useProxy;
            return this;
        }

        public Builder setMaxPerRoute(int maxPerRoute) {
            this.maxPerRoute = maxPerRoute;
            return this;
        }

        public SyncHttpSender build() {
            try {
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(connectTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                        .build();

                SSLContext sslContext;
                SSLConnectionSocketFactory sslConnectionSocketFactory;

                if (Strings.isNullOrEmpty(keyStoreFile) && Strings.isNullOrEmpty(trustStoreFile)) {
                    sslContext = SSLContexts.custom().useSSL().useProtocol(protocol).build();
                    sslContext.init(null, new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
                    sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                } else {
                    sslContext = SSLUtils.buildSSLContext(keyStoreFile, keyPassword, keyAlgorithm, trustStoreFile, trustPassword, trustAlgorithm, protocol);
                    sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                }

                PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                        RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                                .register("https", sslConnectionSocketFactory)
                                .build(),
                        null, null, null, MAX_TIME_TO_LIVE, TimeUnit.MILLISECONDS);
                cm.setMaxTotal(maxPoolTotal);
                cm.setDefaultMaxPerRoute(maxPoolTotal);
                cm.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(socketTimeout).build());

                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, new NamedThreadFactory("httpClient-con-monitor"));
                scheduler.scheduleAtFixedRate(new IdleConnectionMonitor(socketTimeout, cm), 60000, 15000, TimeUnit.MILLISECONDS);

                return new SyncHttpSender(HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build(), connectTimeout, socketTimeout, useProxy);
            } catch (Throwable e) {
                logger.error("SyncHttpSender初始化出现异常", e);
            }
            return null;
        }
    }

    public static class IdleConnectionMonitor implements Runnable {

        private int socketTimeout;

        private PoolingHttpClientConnectionManager connectionManager;

        public IdleConnectionMonitor(int socketTimeout, PoolingHttpClientConnectionManager connectionManager) {
            this.socketTimeout = socketTimeout;
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            logger.debug("release start connect count:=" + connectionManager.getTotalStats().getAvailable());

            connectionManager.closeExpiredConnections();

            connectionManager.closeIdleConnections(socketTimeout * 2, TimeUnit.MILLISECONDS);

            logger.debug("release end connect count:=" + connectionManager.getTotalStats().getAvailable());
        }
    }
}
