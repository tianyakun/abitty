package com.abitty.biz.impl;

import com.abitty.biz.PayNotifyBiz;
import com.abitty.constant.AbittyConstants;
import com.abitty.entity.TblOrderInfo;
import com.abitty.service.OrderService;
import com.abitty.wechat.WechatPayProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yak on 17/6/25.
 */
@Component
public class PayNotifyBizImpl implements PayNotifyBiz {

    private final static Logger logger = LoggerFactory.getLogger(PayNotifyBizImpl.class);

    @Autowired
    private WechatPayProxy wechatPayProxy;

    @Autowired
    private OrderService orderService;

    @Override
    public String receivePayNotify(Map<String, String> notifyMap) {

        logger.info("支付结果通知 {}", notifyMap);
        TblOrderInfo notifyOrderInfo = wechatPayProxy.receiveNotify(notifyMap);
        logger.info("支付结果通知解析 {}", notifyOrderInfo);

        if (notifyOrderInfo == null) {
            logger.error("支付结果解析失败");

            return null;
        }

        boolean notifySucc = false;
        boolean needUpdate = false;

        TblOrderInfo dbOrderInfo = orderService.getByPayId(notifyOrderInfo.getPayId());
        if (dbOrderInfo == null) {
            logger.error("未找到原支付订单 payId={}", notifyOrderInfo.getPayId());
        } else {
            if (AbittyConstants.OrderState.INITIAL == dbOrderInfo.getStatus()) {
                needUpdate = true;
                notifySucc = true;
            } else {
                needUpdate = false;
                notifySucc = true;
            }
        }

        if (needUpdate) {
            dbOrderInfo.setStatus(AbittyConstants.OrderState.PAY_SUCCESS);
        }

        return wechatPayProxy.getReceiveNotifyResponse(notifySucc);
    }
}
