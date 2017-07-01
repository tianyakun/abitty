package com.abitty.dto;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yak on 17/6/17.
 */
public class ResponseDto implements Serializable {

    private String retCode;

    private String retMsg;

    private String chineseMsg;

    private Map<String, Object> data = Maps.newHashMap();

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addAttribute(String key, Object value) {
        this.data.put(key, value);
    }

    public String getChineseMsg() {
        return chineseMsg;
    }

    public void setChineseMsg(String chineseMsg) {
        this.chineseMsg = chineseMsg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonResponseDto{");
        sb.append("retCode='").append(retCode).append('\'');
        sb.append(", retMsg='").append(retMsg).append('\'');
        sb.append(", chineseMsg='").append(chineseMsg).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
