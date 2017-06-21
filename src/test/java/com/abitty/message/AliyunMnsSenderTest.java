package com.abitty.message;

import com.abitty.BaseTest;
import com.abitty.constant.MessageConstants;
import com.abitty.entity.TblMessageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/21.
 */
public class AliyunMnsSenderTest extends BaseTest{

    @Autowired
    private AliyunMnsSender aliyunMnsSender;

    @Test
    public void testSend() throws Exception {
        TblMessageInfo tblMessageInfo = new TblMessageInfo();
        tblMessageInfo.setTemplateCode(MessageConstants.TEMPLATE_CODE);
        tblMessageInfo.setMessageAddress("18610915087");
        tblMessageInfo.setVcode("123456");

        aliyunMnsSender.send(tblMessageInfo);
    }
}