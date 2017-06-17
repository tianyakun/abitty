package com.abitty.service.impl;

import com.abitty.service.MessageService;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/6/17.
 */
@Component
public class MessageServiceImpl implements MessageService {
    public boolean verify(String messageId, String verifyCode) {
        return false;
    }

    public String sendVerifyCode(String phone) {
        return null;
    }
}
