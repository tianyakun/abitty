package com.abitty.controller;

import com.abitty.biz.WechatProcessBiz;
import com.abitty.dto.ResponseDto;
import com.abitty.enums.ExceptionEnum;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yak on 17/6/23.
 */
@Controller
@RequestMapping(value = "/wechat")
public class WechatController {

    public static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatProcessBiz wechatProcessBiz;

    @RequestMapping(value = "/ticket")
    @ResponseBody
    public ResponseDto jsapiTicket(HttpServletRequest request) {

        ResponseDto responseDto = new ResponseDto();

        try {

            String ticketUrl = request.getParameter("ticketUrl");
            logger.info("JS-SDK权限获取请求 ticketUrl={}", ticketUrl);


            if (Strings.isNullOrEmpty(ticketUrl)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
                return responseDto;
            }

            wechatProcessBiz.getTicket(ticketUrl, responseDto);

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("JS-SDK权限获取返回: {}", responseDto);
        return responseDto;
    }
}
