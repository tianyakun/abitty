package com.abitty.service;

/**
 * Created by yak on 17/6/17.
 */
public interface MessageService {
    boolean verify(String messageId, String verifyCode);

    String sendVerifyCode(String phone);
}
