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

    }

    @Test
    public void testTransXML2Map() throws Exception {

    }

    @Test
    public void testTransJson2Map() throws Exception {
        String json = "{\"access_token\":\"wErGGE6cLZXWsxIUoIqGfKJKL0dq9s7z7QcMJjYSSrnDD2qJW7Rg4TGpWWMNPS3SPBnqdyDFhxTyq6JJoXclZbLzkECwbLoYehb0lVaKm_nE6SpthMXpSX4iqrYFsN_7NRVaADAJNR\",\"expires_in\":7200}";
        Map<String, String> map = WechatDataUtil.transJson2Map(json);
        System.out.println(map);
    }
}