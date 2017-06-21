package com.abitty.biz.impl;

import com.abitty.BaseTest;
import com.abitty.biz.MessageProcessBiz;
import com.abitty.constant.MessageConstants;
import com.abitty.dto.ResponseDto;
import com.abitty.dto.SendMessageRequestDto;
import com.abitty.enums.MessageType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/21.
 */
public class MessageProcessBizImplTest extends BaseTest{

    @Autowired
    private MessageProcessBiz messageProcessBiz;

    @Test
    public void testSendMessage() throws Exception {
        SendMessageRequestDto requestDto = new SendMessageRequestDto();
        requestDto.setMessageAddress("18610915087");
        requestDto.setMessageChannel(MessageConstants.MESSAGE_CHANNEL);
        requestDto.setMessageType(MessageType.VALIDATE);
        requestDto.setTemplateCode(MessageConstants.TEMPLATE_CODE);

        ResponseDto responseDto = new ResponseDto();

        messageProcessBiz.sendMessage(requestDto, responseDto);

        System.out.println(responseDto);
    }

    @Test
    public void testCheckValidateCode() {
        String messageId = "M327213112052678656";
        String vcode = "157792";
        System.out.println(messageProcessBiz.checkValidateCode(messageId, vcode));
    }
}