package com.abitty.wechat;

import com.abitty.constant.WechatConstants;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblOrderInfo;
import com.abitty.enums.ExceptionEnum;
import com.abitty.transport.http.HttpConstants;
import com.abitty.transport.http.SyncHttpSender;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yak on 17/6/23.
 */
@Component
public class WechatPayProxy {

    private final static Logger logger = LoggerFactory.getLogger(WechatPayProxy.class);

    @Autowired
    private WechatProxy wechatProxy;

    private SyncHttpSender httpSender = SyncHttpSender.createCustomHttpSender(WechatConstants.READ_TIMEOUT, WechatConstants.CONN_TIMEOUT, HttpConstants.DEFAULT_MAX_TOTAL, true);

    public void paySubmit(TblOrderInfo tblOrderInfo) {
        try {
            String openid = wechatProxy.getOpenId(tblOrderInfo.getOpenidCode());
            Preconditions.checkNotNull(openid, "get openid is null");

            Map<String, String> data = Maps.newTreeMap();
            data.put("appid", WechatConstants.APP_ID);
            data.put("body", tblOrderInfo.getProductBody());
            data.put("device_info", "WEB");
            data.put("mch_id", WechatConstants.MERCHANT_ID);
            String nonceStr = WechatDataUtil.randomStr();
            data.put("nonce_str", nonceStr);
            data.put("out_trade_no", tblOrderInfo.getPayId());
            data.put("total_fee", String.valueOf((int)tblOrderInfo.getTotalAmount().doubleValue() * 100));
            data.put("notify_url", WechatConstants.NOTIFY_URL);
            data.put("trade_type", WechatConstants.TRADE_TYPE);
            data.put("spbill_create_ip", tblOrderInfo.getIp());
            data.put("openid", openid);
            data.put("sign", WechatDataUtil.md5Sign(data).toUpperCase());

            String requestXml = WechatDataUtil.transMap2XML(data);

            logger.info("微信统一下单请求 {}", requestXml);
            String responseXml = httpSender.doPost(WechatConstants.UNIFIED_ORDER_URL, requestXml, WechatConstants.CHARSET);
            logger.info("微信统一下单返回 {}", responseXml);

            if (Strings.isNullOrEmpty(responseXml)) {
                logger.error("微信统一下单返回为空");
                tblOrderInfo.setErrorCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                tblOrderInfo.setErrorMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
                return;
            }

            Map<String, String> responseMap = WechatDataUtil.transXML2Map(requestXml);
            if (MapUtils.isEmpty(responseMap)) {
                logger.error("微信统一下单返回报文解析异常");
                tblOrderInfo.setErrorCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                tblOrderInfo.setErrorMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
                return;
            }


            if (!"SUCCESS".equals(responseMap.get("return_code"))) {
                logger.error("微信统一下单失败 return_code={}", responseMap.get("return_code"));
                tblOrderInfo.setErrorCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                tblOrderInfo.setErrorMsg(responseMap.get("return_code"));
                return;
            }

            // 以下字段在 return_code为 SUCCESS的时候有返回
            //验签 todo

            if (!"SUCCESS".equals("result_code")) {
                logger.error("微信统一下单失败 result_code={}", responseMap.get("result_code"));
                tblOrderInfo.setErrorCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                tblOrderInfo.setErrorMsg(responseMap.get("result_code"));
                return;
            }

            String payReturnId = responseMap.get("prepay_id");
            logger.info("微信统一下单返回 prepay_id={}", payReturnId);

          tblOrderInfo.setPayReturnId(payReturnId);
        } catch (Exception e) {
            logger.error("微信统一下单请求异常", e);
        }
    }

    public WechatProxy getWechatProxy() {
        return wechatProxy;
    }

    public void setWechatProxy(WechatProxy wechatProxy) {
        this.wechatProxy = wechatProxy;
    }
}
