package com.abitty.wechat;

import com.abitty.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/25.
 */
public class WechatProxyTest extends BaseTest{

    @Autowired
    private WechatProxy wechatProxy;

    @Test
    public void testGetAccessToken() throws Exception {
        System.out.println(wechatProxy.getAccessToken());
    }

    @Test
    public void testGetJsapiTicket() throws Exception {
        String token = "XW7GQCkzbNszlbVvwFis54dvtUISDiIt1dNzISmzC75LVBVwdBuWfGceMiODbqlAWkL7d9ghQUPOP6S7V9-mZZDYqGBDabg2Qevaq47nhGa1k-SiFRb64ugdeQsHAKg-PVXcAJAEKM";
        System.out.println(wechatProxy.getJsapiTicket(token));
    }

    @Test
    public void testGetOpenId() throws Exception {
        String code = "021hm1od0iCRFv1mrWod0UOWnd0hm1or";
        System.out.println(wechatProxy.getOpenId(code));
    }
}