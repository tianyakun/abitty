package com.abitty.transport.http;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 默认响应Handler
 *
 * @author wenlong.du
 */
public class DefaultResponseHandler implements ResponseHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultResponseHandler.class);

    private String charsetName;

    public DefaultResponseHandler(String charsetName) {
        this.charsetName = charsetName;
    }

    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK) {
            logger.error("DefaultResponseHandler statusCode={}", response.getStatusLine());
            return null;
        }

        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, charsetName);
    }

    public static DefaultResponseHandler determinDefaultResponseHandle(String charset){
        DefaultResponseHandler responseHandler = SUPPORT_RESPONSE_HANDLER.get(charset);
        if(responseHandler == null){
            throw new UnsupportedOperationException("DefaultResponseHandler未支持charset=" + charset);
        }
        return responseHandler;
    }

    private static Map<String, DefaultResponseHandler> SUPPORT_RESPONSE_HANDLER = Maps.newHashMap();
    static{
        SUPPORT_RESPONSE_HANDLER.put(Charsets.UTF_8.name(), new DefaultResponseHandler(Charsets.UTF_8.name()));
        SUPPORT_RESPONSE_HANDLER.put(Charsets.UTF_16.name(), new DefaultResponseHandler(Charsets.UTF_16.name()));
        SUPPORT_RESPONSE_HANDLER.put(Charsets.ISO_8859_1.name(), new DefaultResponseHandler(Charsets.ISO_8859_1.name()));
        SUPPORT_RESPONSE_HANDLER.put(HttpConstants.GBK_CHARSET, new DefaultResponseHandler(HttpConstants.GBK_CHARSET));
    }
}
