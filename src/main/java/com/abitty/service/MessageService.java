package com.abitty.service;

import com.abitty.entity.TblMessageInfo;

/**
 * Created by yak on 17/6/17.
 */
public interface MessageService {
    boolean verify(String messageId, String verifyCode);

    String sendVerifyCode(String phone);

    boolean saveRequestMessage(TblMessageInfo tblMessageInfo);

    void sendTransaction(TblMessageInfo tblMessageInfo);

    void updateSendResult(TblMessageInfo tblMessageInfo);

    boolean validateTransaction(String messageId, String validateCode);
}
