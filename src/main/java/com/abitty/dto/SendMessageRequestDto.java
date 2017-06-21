package com.abitty.dto;

import com.abitty.enums.MessageType;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.Map;

/**
 * Created by yak on 17/6/20.
 */
public class SendMessageRequestDto {
    /**
     * 消息接收地址, 如手机号，邮箱，用户UID等
     */
    @NotNull
    @NotEmpty
    private String messageAddress;

    /**
     * 消息类型
     */
    @NotNull
    private MessageType messageType;

    /**
     * 模板编号
     */
    @NotNull
    @NotEmpty
    private String templateCode;

    /**
     * 消息发送渠道
     */
    @NotNull
    @NotEmpty
    private String messageChannel;

    /**
     * 消息发送内容模板参数集
     */
    private Map<String, String> templateParameters;

    public String getMessageAddress() {
        return messageAddress;
    }

    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(String messageChannel) {
        this.messageChannel = messageChannel;
    }

    public Map<String, String> getTemplateParameters() {
        return templateParameters;
    }

    public void setTemplateParameters(Map<String, String> templateParameters) {
        this.templateParameters = templateParameters;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageSendRequestDto{");
        sb.append("messageAddress='").append(messageAddress).append('\'');
        sb.append(", messageType=").append(messageType);
        sb.append(", templateCode=").append(templateCode);
        sb.append(", messageChannel='").append(messageChannel).append('\'');
        sb.append(", templateParameters=").append(templateParameters);
        sb.append('}');
        return sb.toString();
    }
}
