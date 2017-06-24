package com.abitty.transport.internal;

import com.ning.http.client.*;
import com.ning.http.client.filter.IOExceptionFilter;
import com.ning.http.client.filter.RequestFilter;
import com.ning.http.client.filter.ResponseFilter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author litingpeng
 */
public class GwAsyncHttpClientConfig extends AsyncHttpClientConfig {

    private final AsyncHttpClientConfig config;

    private boolean useProxy;

    private ProxyServerSelector proxyServerSelector;

    public GwAsyncHttpClientConfig(AsyncHttpClientConfig config, boolean useProxy) {
        this.config = config;
        this.useProxy = useProxy;
    }

    @Override
    public int getMaxConnections() {
        return config.getMaxConnections();
    }

    @Override
    public int getMaxConnectionsPerHost() {
        return config.getMaxConnectionsPerHost();
    }

    @Override
    public int getConnectTimeout() {
        return config.getConnectTimeout();
    }

    @Override
    public int getWebSocketTimeout() {
        return config.getWebSocketTimeout();
    }

    @Override
    public int getReadTimeout() {
        return config.getReadTimeout();
    }

    @Override
    public int getPooledConnectionIdleTimeout() {
        return config.getPooledConnectionIdleTimeout();
    }

    @Override
    public int getRequestTimeout() {
        return config.getRequestTimeout();
    }

    @Override
    public boolean isFollowRedirect() {
        return config.isFollowRedirect();
    }

    @Override
    public int getMaxRedirects() {
        return config.getMaxRedirects();
    }

    @Override
    public boolean isAllowPoolingConnections() {
        return config.isAllowPoolingConnections();
    }

    @Override
    public String getUserAgent() {
        return config.getUserAgent();
    }

    @Override
    public boolean isCompressionEnforced() {
        return config.isCompressionEnforced();
    }

    @Override
    public ExecutorService executorService() {
        return config.executorService();
    }

    @Override
    public ProxyServerSelector getProxyServerSelector() {
        return config.getProxyServerSelector();
    }

    @Override
    public SSLContext getSSLContext() {
        return config.getSSLContext();
    }

    @Override
    public AsyncHttpProviderConfig<?, ?> getAsyncHttpProviderConfig() {
        return config.getAsyncHttpProviderConfig();
    }

    @Override
    public Realm getRealm() {
        return config.getRealm();
    }

    @Override
    public List<RequestFilter> getRequestFilters() {
        return config.getRequestFilters();
    }

    @Override
    public List<ResponseFilter> getResponseFilters() {
        return config.getResponseFilters();
    }

    @Override
    public List<IOExceptionFilter> getIOExceptionFilters() {
        return config.getIOExceptionFilters();
    }

    @Override
    public int getMaxRequestRetry() {
        return config.getMaxRequestRetry();
    }

    @Override
    public boolean isAllowPoolingSslConnections() {
        return config.isAllowPoolingSslConnections();
    }

    @Override
    public boolean isDisableUrlEncodingForBoundedRequests() {
        return config.isDisableUrlEncodingForBoundedRequests();
    }

    @Override
    public boolean isValid() {
        return config.isValid();
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return config.getHostnameVerifier();
    }

    @Override
    public int getIoThreadMultiplier() {
        return config.getIoThreadMultiplier();
    }

    @Override
    public boolean isStrict302Handling() {
        return config.isStrict302Handling();
    }

    @Override
    public boolean isUseRelativeURIsWithConnectProxies() {
        return config.isUseRelativeURIsWithConnectProxies();
    }

    @Override
    public int getConnectionTTL() {
        return config.getConnectionTTL();
    }

    @Override
    public boolean isAcceptAnyCertificate() {
        return config.isAcceptAnyCertificate();
    }

    @Override
    public String[] getEnabledProtocols() {
        return config.getEnabledProtocols();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return config.getEnabledCipherSuites();
    }

    @Override
    public Integer getSslSessionCacheSize() {
        return config.getSslSessionCacheSize();
    }

    @Override
    public Integer getSslSessionTimeout() {
        return config.getSslSessionTimeout();
    }
}
