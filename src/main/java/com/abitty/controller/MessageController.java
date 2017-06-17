package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblCatalog;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.MessageService;
import com.abitty.vo.CatalogVo;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/17.
 */
@Controller
@RequestMapping(value = "/verify")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/send")
    @ResponseBody
    public ResponseDto addAddress(final String phone) {

        logger.info("发送短信验证码请求 phone={}", phone);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(phone)) {
                logger.error("参数校验失败");
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
            } else {
                String messageId = messageService.sendVerifyCode(phone);

                responseDto.addAttribute("messageId", messageId);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("发送短信验证码返回: {}", responseDto);
        return responseDto;
    }
}
