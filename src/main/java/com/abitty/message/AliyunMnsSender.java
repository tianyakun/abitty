package com.abitty.message;

import com.abitty.constant.MessageConstants;
import com.abitty.entity.TblMessageInfo;
import com.abitty.enums.ExceptionEnum;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/6/21.
 */
@Component
public class AliyunMnsSender implements Sender {

    public void send(TblMessageInfo tblMessageInfo) {

        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(MessageConstants.ACCESS_ID, MessageConstants.ACCESS_KEY, MessageConstants.MNS_ENDPOINT);
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(MessageConstants.TOPIC);

        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");

        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(MessageConstants.SIGN_NAME);
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(tblMessageInfo.getTemplateCode());
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("code", tblMessageInfo.getVcode());
        // 3.4 增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(tblMessageInfo.getMessageAddress(), smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);

        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            tblMessageInfo.setChannelMessageId(ret.getMessageId());
            tblMessageInfo.setSendStatus(MessageConstants.SendStatus.SUCCESS);
            tblMessageInfo.setResultCode(ExceptionEnum.SUCCESS.getErrorCode());
            tblMessageInfo.setResultInfo(ExceptionEnum.SUCCESS.getErrorMsg());
        } catch (ServiceException se) {
            tblMessageInfo.setSendStatus(MessageConstants.SendStatus.FAILED);
            tblMessageInfo.setResultCode(ExceptionEnum.MESSAGE_SEND_INVALID.getErrorCode());
            tblMessageInfo.setResultInfo(ExceptionEnum.MESSAGE_SEND_INVALID.getErrorMsg());
        } catch (Exception e) {
            tblMessageInfo.setSendStatus(MessageConstants.SendStatus.FAILED);
            tblMessageInfo.setResultCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            tblMessageInfo.setResultInfo(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
        client.close();
    }
}
