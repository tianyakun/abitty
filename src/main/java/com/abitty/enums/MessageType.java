package com.abitty.enums;

/**
 * Created by yak on 17/6/19.
 */
public enum MessageType {

    /**
     * 含验证码的短信
     */
    VALIDATE(0, "VALIDATE"),

    /**
     * 无验证码通知短信
     */
    NOTIFY(1, "NOTIFY");

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private MessageType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public static MessageType toMessageType(int code){
        MessageType[] list = MessageType.values();
        for(MessageType messageType : list){
            if(messageType.getCode() == code)
                return messageType;
        }
        return null;
    }

}
