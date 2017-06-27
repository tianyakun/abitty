package com.abitty.wechat;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/24.
 */
public class WechatDataUtilTest {

    @Test
    public void testTransMap2XML() throws Exception {
        System.out.println(WechatDataUtil.randomStr());
    }

    @Test
    public void testTransXML2Map() throws Exception {
        String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "<appid><![CDATA[wx6567f481349fba16]]></appid>\n" +
                "<mch_id><![CDATA[1480988362]]></mch_id>\n" +
                "<device_info><![CDATA[WEB]]></device_info>\n" +
                "<nonce_str><![CDATA[5XA3XcYxyZMMdmkv]]></nonce_str>\n" +
                "<sign><![CDATA[A938742ADCDC22FA9FE58366D6B892A6]]></sign>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<prepay_id><![CDATA[wx2017062723262604e983737c0691131980]]></prepay_id>\n" +
                "<trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "</xml>";
        System.out.println(WechatDataUtil.transXML2Map(xml));
    }

    @Test
    public void testTransJson2Map() throws Exception {
        String json = "{\"access_token\":\"wErGGE6cLZXWsxIUoIqGfKJKL0dq9s7z7QcMJjYSSrnDD2qJW7Rg4TGpWWMNPS3SPBnqdyDFhxTyq6JJoXclZbLzkECwbLoYehb0lVaKm_nE6SpthMXpSX4iqrYFsN_7NRVaADAJNR\",\"expires_in\":7200}";
        Map<String, String> map = WechatDataUtil.transJson2Map(json);
        System.out.println(map);
    }


}