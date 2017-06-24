package com.abitty.transport.util;

import com.abitty.transport.http.HttpConstants;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.HttpHeaders;
import com.ning.http.client.AsyncHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author litingpeng
 */
public class HttpUtils {

    /**
     * 增加HTTP header
     *
     * @param headerMap
     * @param httpRequestBase
     */
    public static void addHeader(Map<String, String> headerMap, HttpRequestBase httpRequestBase) {
        if (!CollectionUtils.isEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //增加默认 @see http://wiki.apache.org/HttpComponents/LessonsLearned
        httpRequestBase.addHeader(HttpHeaders.USER_AGENT, HttpConstants.CUSTOM_USER_AGENT);
    }

    /**
     * 增加HTTP header
     *
     * @param headerMap
     * @param builder
     */
    public static void addHeader(Map<String, String> headerMap, AsyncHttpClient.BoundRequestBuilder builder){
        if(!CollectionUtils.isEmpty(headerMap)){
            for(Map.Entry<String, String> entry : headerMap.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 增加请求参数
     *
     * @param queryMap
     * @param builder
     */
    public static void addQueryParameter(Map<String, String> queryMap, String charset, AsyncHttpClient.BoundRequestBuilder builder){
        if(!CollectionUtils.isEmpty(queryMap)){
            Map<String, String> encodedQueryMap = HttpUtils.encodeParameters(queryMap, charset);
            for(Map.Entry<String, String> entry : encodedQueryMap.entrySet()){
                builder.addQueryParam(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 增加请求体
     *
     * @param content
     * @param charset
     * @param httpPost
     * @throws UnsupportedEncodingException
     */
    public static void addBody(String content, String charset, HttpPost httpPost) throws UnsupportedEncodingException {
        if(!Strings.isNullOrEmpty(content)){
            httpPost.setEntity(new StringEntity(content, charset));
        }
    }

    /**
     * 增加请求体
     *
     * @param params map
     * @param charset
     * @param httpPost
     * @throws UnsupportedEncodingException
     */
    public static void addBody(Map<String,String> params, String charset, HttpPost httpPost) throws UnsupportedEncodingException {
        if(params==null) {
            return;
        }

        List<NameValuePair> list = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(list, charset));
    }

    /**
     * 增加请求体
     *
     * @param content
     * @param charset
     * @param builder
     * @throws UnsupportedEncodingException
     */
    public static void addBody(String content, String charset, AsyncHttpClient.BoundRequestBuilder builder) throws UnsupportedEncodingException {
        if(!Strings.isNullOrEmpty(content)){
            builder.setBody(content.getBytes(charset));
        }
    }

    /**
     * 构造URL查询
     *
     * @param url
     * @param queryMap
     * @param charset
     * @return
     * @throws URISyntaxException
     */
    public static String composeUrl(String url, Map<String, String> queryMap, String charset) throws URISyntaxException {
        if(queryMap == null || queryMap.size() == 0){
            return url;
        }
        Map<String, String> toSendMap = encodeParameters(queryMap, charset);
        String requestUrl = url;
        int questionMarkPos = url.indexOf("?");
        if (questionMarkPos == -1) {
            requestUrl += "?" + HttpConstants.URL_JOINER.join(toSendMap);
        } else {
            requestUrl = requestUrl.substring(0, questionMarkPos + 1) + HttpConstants.URL_JOINER.join(toSendMap);
        }
        return requestUrl;
    }

    /**
     * 对参数进行URLencode
     *
     * @param parameters
     * @param charset
     * @return
     */
    public static Map<String, String> encodeParameters(Map<String, String> parameters, final String charset) {
        Map<String, String> encodedMap = Maps.transformValues(parameters, new Function<String, String>() {
            @Override
            public String apply(String input) {
                try {
                    return URLEncoder.encode(input, charset);
                } catch (UnsupportedEncodingException e) {
                    Throwables.propagate(e);
                }
                throw new IllegalArgumentException("unknown Exception");
            }
        });
        return encodedMap;
    }
}
