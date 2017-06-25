package com.abitty.biz.impl;

import com.abitty.biz.PayNotifyBiz;

import java.util.Map;

/**
 * Created by yak on 17/6/25.
 */
public class PayNotifyBizImpl implements PayNotifyBiz {

    @Override
    public String receivePayNotify(Map<String, String> notifyMap) {

//        String xml=responseMap.get("content");
//
//        logger.info("接收到微信异步回调数据:{}",xml);
//
//        Map<String,String> map=Wechat3DataUtils.transXML2Map(xml);
//        if (!StringUtils.equals("SUCCESS", map.get("return_code"))) {
//            logger.error("微信tounch端支付回调返回错误信息{}", map.get("return_msg"));
//            return null;
//        }
//        if (!wechat3SecurityProxy.verify(servId, map)) {
//            logger.error("微信tounch端支付回调验签错误");
//            return null;
//        }
//        if (!StringUtils.equals("SUCCESS", map.get("result_code"))) {
//            logger.error("微信tounch端支付回调参数result_code={},err_code={},err_code_des={}", new Object[]{map.get("result_code"),map.get("err_code"), map.get("err_code_des")});
//            return null;
//        }
//
//        // 以下字段在 return_code 和 result_code都为 SUCCESS的时候有返回 微信V3只有成功才有回调结果
//        TblPayinfo tblPayinfo = new TblPayinfo();
//        tblPayinfo.setServId(servId);
//        tblPayinfo.setServPayId(map.get("out_trade_no"));
//        tblPayinfo.setServPayReturnId(map.get("transaction_id"));
//        tblPayinfo.setConfirmAmount(BankUtils.toYuan(map.get("total_fee")));
//        tblPayinfo.setPayStatus(PayConstant.PayState.SUCCESS);
//        tblPayinfo.setErrorCode(ExceptionEnum.SUCCESS.getErrorCode());
//        tblPayinfo.setErrorInfo("支付成功");
//        return tblPayinfo;





        return null;
    }
}
