package com.abitty.transport.http;

import com.abitty.transport.internal.GwAsyncHttpClientConfig;
import com.abitty.transport.util.CompatibleSSLUtils;
import com.abitty.transport.util.HttpUtils;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Map;

/**
 * @author litingpeng
 */
public class AsyncHttpSender{

    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpSender.class);

    private AsyncHttpClient asyncClient;

    private AsyncHttpSender(AsyncHttpClient asyncClient){
        this.asyncClient = asyncClient;
    }

    /**
     * @return
     */
    public static AsyncHttpSender createDefaultHttpSender() {
        return createCustomHttpSender(HttpConstants.DEFAULT_SOCKET_TIMEOUT, HttpConstants.DEFAULT_CONNECT_TIMEOUT, HttpConstants.DEFAULT_MAX_TOTAL, false);
    }

    /**
     * @return
     */
    public static AsyncHttpSender createDefaultHttpSender(boolean useProxy) {
        return createCustomHttpSender(HttpConstants.DEFAULT_SOCKET_TIMEOUT, HttpConstants.DEFAULT_CONNECT_TIMEOUT, HttpConstants.DEFAULT_MAX_TOTAL, useProxy);
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal) {
        return createCustomHttpSender(socketTimeout, connectTimeout, maxPoolTotal, false);
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, boolean useProxy) {
        try {
            SSLContext sslContext = CompatibleSSLUtils.getDefaultSSLContext();

            GwAsyncHttpClientConfig.Builder builder = new GwAsyncHttpClientConfig.Builder();
            builder.setConnectTimeout(connectTimeout);
            builder.setRequestTimeout(socketTimeout);
            builder.setAllowPoolingConnections(true);
            builder.setCompressionEnforced(true);
            builder.setPooledConnectionIdleTimeout(3 * 60 * 1000);
            builder.setMaxConnections(maxPoolTotal);
            builder.setSSLContext(sslContext);
            return new AsyncHttpSender(new AsyncHttpClient(new GwAsyncHttpClientConfig(builder.build(), useProxy)));
        } catch(Throwable e){
            logger.error("AsyncHttpSender初始化出现异常", e);
        }
        return null;
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @param trustStoreFile
     * @param trustPassword
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, String trustStoreFile, String trustPassword) {
        return createCustomHttpSender(socketTimeout, connectTimeout, maxPoolTotal, trustStoreFile, trustPassword, false);
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @param trustStoreFile
     * @param trustPassword
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, String trustStoreFile, String trustPassword, boolean useProxy) {
        try {
            SSLContext sslContext = CompatibleSSLUtils.buildSSLContext(trustStoreFile, trustPassword);

            GwAsyncHttpClientConfig.Builder builder = new GwAsyncHttpClientConfig.Builder();
            builder.setConnectTimeout(connectTimeout);
            builder.setRequestTimeout(socketTimeout);
            builder.setAllowPoolingConnections(true);
            builder.setCompressionEnforced(true);
            builder.setPooledConnectionIdleTimeout(3 * 60 * 1000);
            builder.setMaxConnections(maxPoolTotal);
            builder.setSSLContext(sslContext);
            return new AsyncHttpSender(new AsyncHttpClient(new GwAsyncHttpClientConfig(builder.build(), useProxy)));
        } catch(Throwable e){
            logger.error("AsyncHttpSender初始化出现异常", e);
        }
        return null;
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @param keyStoreFile
     * @param keyPassword
     * @param trustStoreFile
     * @param trustPassword
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, String keyStoreFile, String keyPassword, String trustStoreFile, String trustPassword) {
        return createCustomHttpSender(socketTimeout, connectTimeout, maxPoolTotal, false);
    }

    /**
     * @param socketTimeout
     * @param connectTimeout
     * @param maxPoolTotal
     * @param keyStoreFile
     * @param keyPassword
     * @param trustStoreFile
     * @param trustPassword
     * @return
     */
    public static AsyncHttpSender createCustomHttpSender(int socketTimeout, int connectTimeout, int maxPoolTotal, String keyStoreFile, String keyPassword, String trustStoreFile, String trustPassword, boolean useProxy) {
        try {
            SSLContext sslContext = CompatibleSSLUtils.buildSSLContext(keyStoreFile, keyPassword, trustStoreFile, trustPassword);

            GwAsyncHttpClientConfig.Builder builder = new GwAsyncHttpClientConfig.Builder();
            builder.setConnectTimeout(connectTimeout);
            builder.setRequestTimeout(socketTimeout);
            builder.setAllowPoolingConnections(true);
            builder.setCompressionEnforced(true);
            builder.setPooledConnectionIdleTimeout(3 * 60 * 1000);
            builder.setMaxConnections(maxPoolTotal);
            builder.setSSLContext(sslContext);
            return new AsyncHttpSender(new AsyncHttpClient(new GwAsyncHttpClientConfig(builder.build(), useProxy)));
        } catch(Throwable e){
            logger.error("AsyncHttpSender初始化出现异常", e);
        }
        return null;
    }

    /**
     * @param url
     * @param queryMap
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doGet(String url, Map<String, String> queryMap, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.prepareGet(url);

        HttpUtils.addQueryParameter(queryMap, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

    /**
     * @param url
     * @param queryMap
     * @param headerMap
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doGet(String url, Map<String, String> queryMap, Map<String, String> headerMap, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.prepareGet(url);

        HttpUtils.addHeader(headerMap, builder);
        HttpUtils.addQueryParameter(queryMap, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

    /**
     * @param url
     * @param content
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doPost(String url, String content, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.preparePost(url);

        HttpUtils.addBody(content, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

    /**
     * @param url
     * @param content
     * @param headerMap
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doPost(String url, String content, Map<String, String> headerMap, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.preparePost(url);

        HttpUtils.addHeader(headerMap, builder);
        HttpUtils.addBody(content, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

    /**
     * @param url
     * @param queryMap
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doPost(String url, Map<String, String> queryMap, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.preparePost(url);

        HttpUtils.addQueryParameter(queryMap, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

    /**
     * @param url
     * @param queryMap
     * @param headerMap
     * @param charset
     * @return
     * @throws IOException
     */
    public ListenableFuture<Response> doPost(String url, Map<String, String> queryMap, Map<String, String> headerMap, String charset) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = asyncClient.preparePost(url);

        HttpUtils.addHeader(headerMap, builder);
        HttpUtils.addQueryParameter(queryMap, charset, builder);

        return asyncClient.executeRequest(builder.build());
    }

}
