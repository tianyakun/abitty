package com.abitty.biz.impl;

import com.abitty.BaseTest;
import com.abitty.biz.OrderProcessBiz;
import com.abitty.dto.OrderConfirmRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblOrderInfo;
import com.abitty.wechat.WechatPayProxy;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kkk on 17/6/25.
 */
public class OrderProcessBizImplTest extends BaseTest{

    @Autowired
    private OrderProcessBiz orderProcessBiz;

    @Test
    public void testConfirmOrder() throws Exception {
        WechatPayProxy mockWechatPayProxy = mock(WechatPayProxy.class);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                TblOrderInfo tblOrderInfo = (TblOrderInfo) invocationOnMock.getArguments()[0];
                tblOrderInfo.setPayReturnId("PR" + System.currentTimeMillis());
                return null;
            }
        }).when(mockWechatPayProxy).paySubmit(any(TblOrderInfo.class));
        ((OrderProcessBizImpl)orderProcessBiz).setWechatPayProxy(mockWechatPayProxy);


        OrderConfirmRequestDto requestDto = getRequestDto();
        ResponseDto responseDto = new ResponseDto();

        orderProcessBiz.confirmOrder(requestDto, responseDto);

        System.out.println(responseDto);
    }

    private OrderConfirmRequestDto getRequestDto() {
        OrderConfirmRequestDto dto = new OrderConfirmRequestDto();
        dto.setProductNo("PN0010001");
        dto.setServiceAtomCount(3);
        dto.setTotalQuantity(12);
        dto.setTotalAmount(new BigDecimal("280.00"));
        dto.setDeliveryType("weekly");
        dto.setSubQuantity(1);
        dto.setTotalSub(12);
        dto.setReceiverName("麦克斯韦");
        dto.setPhoneNumber("18610915087");
        dto.setAddressProvince("北京");
        dto.setAddressCity("北京市");
        dto.setAddressArea("昌平区");
        dto.setAddressDetail("融泽嘉园");
        dto.setPostcode("100080");
        dto.setRemark("备注");
        dto.setUid("18610915087");
        dto.setIp("127.0.0.1");
        dto.setOpenidCode("xxxxxxxxxxxxx");
        return dto;
    }
}