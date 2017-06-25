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
            return wechatPayProxy.getReceiveNotifyResponse(false);
        }

        TblOrderInfo dbOrderInfo = orderService.getByPayId(notifyOrderInfo.getPayId());
        if (dbOrderInfo == null) {
            logger.error("未找到原支付订单 payId={}", notifyOrderInfo.getPayId());
            return wechatPayProxy.getReceiveNotifyResponse(false);
        }

        if (dbOrderInfo.getTotalAmount().compareTo(notifyOrderInfo.getTotalAmount()) != 0) {
            logger.error("订单金额校验失败 payId={} dbAmount={} notifyAmount={}", notifyOrderInfo.getPayId(), dbOrderInfo.getTotalAmount(), notifyOrderInfo.getTotalAmount());
            return wechatPayProxy.getReceiveNotifyResponse(false);
        }

        if (AbittyConstants.OrderState.INITIAL == dbOrderInfo.getStatus()) {
            dbOrderInfo.setStatus(AbittyConstants.OrderState.PAY_SUCCESS);

            if (orderService.paySuccess(dbOrderInfo)) {
                logger.info("订单支付成功 更新入库成功");
                return wechatPayProxy.getReceiveNotifyResponse(true);
            } else {
                return wechatPayProxy.getReceiveNotifyResponse(false);
            }
        }

        return wechatPayProxy.getReceiveNotifyResponse(true);
    }
}
