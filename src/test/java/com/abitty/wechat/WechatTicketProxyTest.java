package com.abitty.wechat;

import com.abitty.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/24.
 */
public class WechatTicketProxyTest extends BaseTest {

    @Autowired
    private WechatTicketProxy wechatTicketProxy;

    @Test
    public void testGetAccessToken() throws Exception {
        wechatTicketProxy.getAccessToken();
    }

    @Test
    public void testGetJsapiTicket() throws Exception {
        System.out.println(wechatTicketProxy.getJsapiTicket("wErGGE6cLZXWsxIUoIqGfKJKL0dq9s7z7QcMJjYSSrnDD2qJW7Rg4TGpWWMNPS3SPBnqdyDFhxTyq6JJoXclZbLzkECwbLoYehb0lVaKm_nE6SpthMXpSX4iqrYFsN_7NRVaADAJNR"));
    }
}