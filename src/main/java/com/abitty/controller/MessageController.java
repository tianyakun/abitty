package com.abitty.controller;

import com.abitty.biz.MessageProcessBiz;
import com.abitty.constant.MessageConstants;
import com.abitty.dto.SendMessageRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.enums.ExceptionEnum;
import com.abitty.enums.MessageType;
import com.abitty.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yak on 17/6/17.
 */
@Controller
@RequestMapping(value = "/verify")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageProcessBiz messageProcessBiz;

    @RequestMapping(value = "/send/{phone}")
    @ResponseBody
    public ResponseDto addAddress(@PathVariable("phone") final String phone) {

        logger.info("发送短信验证码请求 phone={}", phone);

        ResponseDto responseDto = new ResponseDto();

        try {
            SendMessageRequestDto messageSendRequestDto = new SendMessageRequestDto();
            messageSendRequestDto.setMessageAddress(phone);
            messageSendRequestDto.setMessageType(MessageType.VALIDATE);
            messageSendRequestDto.setMessageChannel(MessageConstants.MESSAGE_CHANNEL);
            messageSendRequestDto.setTemplateCode(MessageConstants.TEMPLATE_CODE);

            messageProcessBiz.sendMessage(messageSendRequestDto, responseDto);

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("发送短信验证码返回: {}", responseDto);
        return responseDto;
    }
}
