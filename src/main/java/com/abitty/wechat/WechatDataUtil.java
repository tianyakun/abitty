package com.abitty.wechat;

import com.abitty.constant.WechatConstants;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yak on 17/6/24.
 */
public class WechatDataUtil {

    private final static Logger logger = LoggerFactory.getLogger(WechatDataUtil.class);

    public static String transMap2XML(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<xml>");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                stringBuilder.append("<").append(entry.getKey()).append(">");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("</").append(entry.getKey()).append(">");
            }
        }
        stringBuilder.append("</xml>");
        return stringBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> transXML2Map(String xml) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        try {
            Map<String, String> map = Maps.newHashMap();
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {
                Element e = (Element) iterator.next();
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (Exception e) {
            logger.error("xml解析异常 xml={}", xml, e);
            return null;
        }
    }

    public static Map<String, String> transJson2Map(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return  (Map<String, String>)JSON.parseObject(json, Map.class);
        } catch (Exception e) {
            logger.error("json解析异常 json={}", json, e);
            return null;
        }
    }

    public static String randomStr(){
//        return String.valueOf((int)((Math.random()*9+1)*100000));
        return String.valueOf(System.currentTimeMillis()) + String.valueOf((int)((Math.random()*9+1)*100));
    }

    public static String sha1Sign(Map<String, String> signMap) {
        try {
            String str = Joiner.on("&").withKeyValueSeparator("=").join(signMap);

            logger.info("sha1签名原串 {}", str);
            return SHA1Util.sign(str);
        } catch (Exception e) {
            logger.error("sha1签名异常");
            return null;
        }
    }

    public static String md5Sign(Map<String, String> signMap) {
        try {
            String str = Joiner.on("&").withKeyValueSeparator("=").join(signMap) + "&key=" + WechatConstants.PAY_KEY;

            return MD5Util.MD5Encode(str, WechatConstants.CHARSET).toUpperCase();
        } catch (Exception e) {
            logger.error("sha1签名异常");
            return null;
        }
    }


}
