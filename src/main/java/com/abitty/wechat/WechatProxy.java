package com.abitty.wechat;

import com.abitty.constant.WechatConstants;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblOrderInfo;
import com.abitty.enums.ExceptionEnum;
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
public class WechatProxy {

    private final static Logger logger = LoggerFactory.getLogger(WechatProxy.class);

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

    public String getOpenId(String openIdCode) {
        Preconditions.checkNotNull(openIdCode, "openIdCode is null");

        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("appid", WechatConstants.APP_ID);
        paramMap.put("secret", WechatConstants.SECRET);
        paramMap.put("code", openIdCode);
        paramMap.put("grant_type", "authorization_code");

        logger.info("获取微信openid请求 paramMap={}", paramMap);
        String response = httpSender.doGet(WechatConstants.OPEN_ID_URL, paramMap, WechatConstants.CHARSET);
        logger.info("获取微信openId返回 response={}", response);

        if (Strings.isNullOrEmpty(response)) {
            logger.error("获取微信openid返回为空");
            return null;
        }

        Map<String, String> responseMap = WechatDataUtil.transJson2Map(response);
        if (MapUtils.isEmpty(responseMap)) {
            logger.error("解析获取微信openid返回报文异常");
            return null;
        }

        return responseMap.get("openid");
    }

    public void packageForJsPay(TblOrderInfo tblOrderInfo, ResponseDto responseDto) {
        Map<String, String> data = Maps.newTreeMap();
        data.put("appId", WechatConstants.APP_ID);
        data.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        data.put("nonceStr", WechatDataUtil.randomStr());
        data.put("package", "prepay_id=" + tblOrderInfo.getPayReturnId());
        data.put("signType", "MD5");
        data.put("paySign", WechatDataUtil.md5Sign(data).toUpperCase());

        responseDto.addAttribute("appId", data.get("appId"));
        responseDto.addAttribute("timeStamp", data.get("timeStamp"));
        responseDto.addAttribute("nonceStr", data.get("nonceStr"));
        responseDto.addAttribute("package", data.get("package"));
        responseDto.addAttribute("signType", data.get("signType"));
        responseDto.addAttribute("paySign", data.get("paySign"));

        responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
        responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
    }
}
