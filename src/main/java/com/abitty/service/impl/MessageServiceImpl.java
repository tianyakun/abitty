package com.abitty.service.impl;

import com.abitty.constant.MessageConstants;
import com.abitty.dao.TblMessageInfoMapper;
import com.abitty.entity.TblMessageInfo;
import com.abitty.enums.MessageType;
import com.abitty.message.Sender;
import com.abitty.service.MessageService;
import com.abitty.utils.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by yak on 17/6/17.
 */
@Component
public class MessageServiceImpl implements MessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private TblMessageInfoMapper tblMessageInfoMapper;

    @Autowired
    private Sender aliyunMnsSender;

    public boolean saveRequestMessage(TblMessageInfo tblMessageInfo) {
        logger.info("消息请求数据入库处理，参数 " + tblMessageInfo.toString());

        tblMessageInfo.setMessageId("M" + Sequence.next());
        tblMessageInfo.setSendStatus(MessageConstants.SendStatus.PENDING);
        tblMessageInfo.setValidateStatus(MessageConstants.CodeValidateStatus.NO_VERIFY);
        tblMessageInfo.setCreateTime(new Date());

        tblMessageInfoMapper.insertSelective(tblMessageInfo);
        return true;
    }

    public void sendTransaction(TblMessageInfo tblMessageInfo) {
        if (MessageType.VALIDATE.getCode() == tblMessageInfo.getMessageType()) {
            //生成验证码
            tblMessageInfo.setVcode(createMessageCode());

            //发送
            aliyunMnsSender.send(tblMessageInfo);
        } else {
            throw new RuntimeException("unsupport message type");
        }
    }

    public void updateSendResult(TblMessageInfo tblMessageInfo) {
        tblMessageInfo.setSendTime(new Date());

        tblMessageInfoMapper.updateByPrimaryKeySelective(tblMessageInfo);
    }

    public boolean validateTransaction(String messageId, String validateCode) {
        logger.info("验证码校验 messageId={} validateCode={}", messageId, validateCode);

        TblMessageInfo tblMessageInfo = tblMessageInfoMapper.selectByMessageId(messageId);

        if (tblMessageInfo == null) {
            logger.error("TblMessageInfo查询为null, messageId={}", messageId);
            return false;
        }

        if (MessageConstants.CodeValidateStatus.NO_VERIFY == tblMessageInfo.getValidateStatus()
                && validateCode.equals(tblMessageInfo.getVcode())) {
            tblMessageInfo.setValidateStatus(MessageConstants.CodeValidateStatus.VERIFIED);
            tblMessageInfoMapper.updateByPrimaryKeySelective(tblMessageInfo);
            return true;
        }

        return false;
    }

    private String createMessageCode(){
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }
}
