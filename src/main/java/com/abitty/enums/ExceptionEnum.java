package com.abitty.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by yak on 17/6/14.
 */
public enum ExceptionEnum {

    SUCCESS("000000", "SUCCESS", "成功"),

    PARAM_INVALID("100001", "PARAM_INVALID", "参数错误"),

    ORDER_NOT_EXIST("100002", "ORDER_NOT_EXIST", "订单不存在"),
    ORDER_STATUS_INVALID("100003", "ORDER_STATUS_INVALID", "订单状态错误"),

    MESSAGE_SEND_INVALID("100004", "MESSAGE_SEND_INVALID", "短信发送失败"),
    VERIFY_INVALID("100005", "VERIFY_INVALID", "验证码错误"),
    ACCESS_TOKEN_INVALID("100006", "ACCESS_TOKEN_INVALID", "获取微信access_token失败"),
    JSAPI_TICKET_INVALID("100007", "JSAPI_TICKET_INVALID", "获取微信jsapi_ticket失败"),
    SIGN_INVALID("100008", "SIGN_INVALID", "获取微信jsapi_ticket失败"),


    SYSTEM_ERROR("999999", "SYSTEM_ERROR", "系统错误");

    private String errorCode;
    private String errorMsg;
    private String chineseMessage;

    ExceptionEnum(String errorCode, String errorMsg, String chineseMessage) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.chineseMessage = chineseMessage;
    }

    /**
     * 获取错误码code
     *
     * @return String
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * 获取中文错误信息
     *
     * @return
     */
    public String getChineseMessage() {
        return chineseMessage;
    }


    //缓存
    private final static Map<String, ExceptionEnum> errorCodeMappingCache = Maps.newHashMap();

    static {
        for (ExceptionEnum enums : ExceptionEnum.values()) {
            errorCodeMappingCache.put(enums.getErrorCode(), enums);
        }
    }

    public static ExceptionEnum getExceptionEnumByErrorCode(String errorCode) {
        return errorCodeMappingCache.get(errorCode);
    }
}
