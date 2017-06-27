package com.abitty.wechat;

import com.abitty.BaseTest;
import com.abitty.constant.WechatConstants;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

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

    @Test
    public void testPackageForJsPay() throws Exception {
        Map<String, String> data = Maps.newTreeMap();
        data.put("appId", WechatConstants.APP_ID);
        data.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        data.put("nonceStr", WechatDataUtil.randomStr());
        data.put("package", "prepay_id=" + "xxxxxx");
        data.put("signType", "MD5");
        data.put("paySign", WechatDataUtil.md5Sign(data).toUpperCase());

        System.out.println(data);
        System.out.println(data.get("package"));
    }
}