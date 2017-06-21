package com.abitty.biz;

import com.abitty.dto.ResponseDto;
import com.abitty.dto.SendMessageRequestDto;

/**
 * Created by yak on 17/6/20.
 */
public interface MessageProcessBiz {

    /**
     * 消息发送
     * @param messageSendRequestDto
     * @param responseDto
     */
    void sendMessage(SendMessageRequestDto messageSendRequestDto, ResponseDto responseDto);

    /**
     * 验证码校验
     * @param messageId
     * @param validateCode
     * @return
     */
    public boolean checkValidateCode(String messageId, String validateCode);
}
