package com.abitty.biz.impl;

import com.abitty.biz.WechatProcessBiz;
import com.abitty.constant.WechatConstants;
import com.abitty.dto.ResponseDto;
import com.abitty.enums.ExceptionEnum;
import com.abitty.wechat.WechatDataUtil;
import com.abitty.wechat.WechatProxy;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yak on 17/6/23.
 */
@Component
public class WechatProcessBizImpl implements WechatProcessBiz{

    private final static Logger logger = LoggerFactory.getLogger(WechatProcessBizImpl.class);

    @Autowired
    private WechatProxy wechatProxy;

    @Override
    public void getTicket(String ticketUrl, ResponseDto responseDto) {
        Preconditions.checkNotNull(ticketUrl, "ticketUrl is null");

        //获取access_token
        String accessToken = wechatProxy.getAccessToken();
        logger.info("获取access_token={}", accessToken);

        if (Strings.isNullOrEmpty(accessToken)) {
            logger.error("获取access_token为空");
            responseDto.setRetCode(ExceptionEnum.ACCESS_TOKEN_INVALID.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.ACCESS_TOKEN_INVALID.getErrorMsg());
            return;
        }

        //获取jsapi_ticket
        String ticket = wechatProxy.getJsapiTicket(accessToken);
        logger.info("获取ticket={}", ticket);

        if (Strings.isNullOrEmpty(ticket)) {
            logger.error("获取access_token为空");
            responseDto.setRetCode(ExceptionEnum.JSAPI_TICKET_INVALID.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.JSAPI_TICKET_INVALID.getErrorMsg());
            return;
        }

        //sha1加密
        String noncestr = WechatDataUtil.randomStr();//随机数
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);//时间戳
//        String url = WechatConstants.ABITTY_CODE_URL + ticketUrl + "&state=STATE";
        Map<String, String> signatureMap = Maps.newTreeMap();

        signatureMap.put("jsapi_ticket", ticket);
        signatureMap.put("noncestr", noncestr);
        signatureMap.put("timestamp", timestamp);
        signatureMap.put("url", ticketUrl);

        String signature = WechatDataUtil.sha1Sign(signatureMap);
        if (Strings.isNullOrEmpty(signature)) {
            logger.error("ticket签名异常");
            responseDto.setRetCode(ExceptionEnum.SIGN_INVALID.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SIGN_INVALID.getErrorMsg());
            return;
        }

        responseDto.addAttribute("jsapi_ticket", ticket);
        responseDto.addAttribute("noncestr", noncestr);
        responseDto.addAttribute("timestamp", timestamp);
        responseDto.addAttribute("signature", signature.toLowerCase());
        responseDto.addAttribute("appid", WechatConstants.APP_ID);

        responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
        responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
    }


}
