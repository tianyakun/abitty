package com.abitty.dto;

/**
 * Created by yak on 17/6/17.
 */
public class LoginDto {

    private String phone;

    private String messageId;

    private String verifyCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginDto{");
        sb.append("phone='").append(phone).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", verifyCode='").append(verifyCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
