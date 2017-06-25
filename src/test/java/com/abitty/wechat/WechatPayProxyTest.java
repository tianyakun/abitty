package com.abitty.wechat;

import com.abitty.BaseTest;
import com.abitty.constant.WechatConstants;
import com.abitty.entity.TblOrderInfo;
import com.abitty.utils.Sequence;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kkk on 17/6/25.
 */
public class WechatPayProxyTest extends BaseTest{

    @Autowired
    private WechatPayProxy wechatPayProxy;

    @Test
    public void testPaySubmit() throws Exception {
        WechatProxy mockWechatProxy = mock(WechatProxy.class);
        when(mockWechatProxy.getOpenId(anyString())).thenReturn("mockOpenid");
        wechatPayProxy.setWechatProxy(mockWechatProxy);

        TblOrderInfo tblOrderInfo = getTblOrderInfo();

        wechatPayProxy.paySubmit(tblOrderInfo);

        System.out.println(tblOrderInfo);
    }

    @Test
    public void testPackageJs() throws Exception {

    }

    private TblOrderInfo getTblOrderInfo() {
        TblOrderInfo order = new TblOrderInfo();
        order.setProductBody("一点生活-商品");
        order.setPayId(Sequence.next() + "");
        order.setTotalAmount(BigDecimal.TEN);
        order.setIp("127.0.0.1");
        order.setOpenidCode("xxxvcvxcvxzv");
        return order;
    }
}