package com.abitty.wechat;

import com.abitty.constant.WechatConstants;
import com.abitty.transport.http.HttpConstants;
import com.abitty.transport.http.SyncHttpSender;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yak on 17/6/23.
 */
@Component
public class WechatTicketProxy {

    private final static Logger logger = LoggerFactory.getLogger(WechatTicketProxy.class);

    private SyncHttpSender httpSender = SyncHttpSender.createCustomHttpSender(WechatConstants.READ_TIMEOUT, WechatConstants.CONN_TIMEOUT, HttpConstants.DEFAULT_MAX_TOTAL, true);

    public String getAccessToken() {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", WechatConstants.APP_ID);
        paramMap.put("secret", WechatConstants.SECRET);

        logger.info("获取微信access_token请求 paramMap={}", paramMap);
        String response = httpSender.doGet(WechatConstants.ACCESS_TOKEN_URL, paramMap, WechatConstants.CHARSET);
        logger.info("获取微信access_token返回 response={}", response);

        if (Strings.isNullOrEmpty(response)) {
            logger.error("获取微信access_token返回为空");
            return null;
        }

        Map<String, String> responseMap = WechatDataUtil.transJson2Map(response);
        if (MapUtils.isEmpty(responseMap)) {
            logger.error("解析获取微信access_token返回报文异常");
            return null;
        }

        return responseMap.get("access_token");
    }

    public String getJsapiTicket(String accessToken) {
        Preconditions.checkNotNull(accessToken, "accessToken is null");

        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("access_token", accessToken);
        paramMap.put("type", "jsapi");

        logger.info("获取微信jsapi_ticket请求 paramMap={}", paramMap);
        String response = httpSender.doGet(WechatConstants.JSAPI_TICKET_URL, paramMap, WechatConstants.CHARSET);
        logger.info("获取微信jsapi_ticket返回 response={}", response);

        if (Strings.isNullOrEmpty(response)) {
            logger.error("获取微信jsapi_ticket返回为空");
            return null;
        }

        Map<String, String> responseMap = WechatDataUtil.transJson2Map(response);
        if (MapUtils.isEmpty(responseMap)) {
            logger.error("解析获取微信jsapi_ticket返回报文异常");
            return null;
        }

        return responseMap.get("ticket");
    }
}
