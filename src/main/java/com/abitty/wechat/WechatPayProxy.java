package com.abitty.wechat;

import com.abitty.constant.WechatConstants;
import com.abitty.entity.TblOrderInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yak on 17/6/23.
 */
@Component
public class WechatPayProxy {

    <appid>wx6567f481349fba16</appid>                                   //微信APPID
    <body>一点生活-纸巾购买</body>                                        //商品支付简单描述【前端传】
    <device_info>WEB</device_info>                                      //设备信息【前端传】
    <mch_id>1480988362</mch_id>                                         //商户号【元数据】
    <nonce_str>coNkWGbVMQw7DLznd37CvVpt6JTl91G0</nonce_str>             //随机字符串
    <out_trade_no>ezcS_gzSrtLKETfswvKxPfMR0</out_trade_no>              //订单号
    <total_fee>188800</total_fee>                                       //价格【前端传】
    <notify_url>http://www.abitty.com</notify_url>                      //签名地址
    <trade_type>JSAPI</trade_type>
    <spbill_create_ip>223.104.38.185</spbill_create_ip>                 //客户端IP
    <openid>oqvfmw8lvfDGpdE9e1KJGacCQfRs</openid>                       //微信用户openid
    <sign>4E981FE71CCBCE29187FA593D31504CB</sign>                       //sign签名

    public void paySubmit(TblOrderInfo tblOrderInfo) {
        HashMap<String, String> data = Maps.newHashMap();
        data.put("appid", );
        data.put("body", tblOrderInfo.getProductBody());
        data.put("out_trade_no", tblOrderInfo.getServPayId());
        data.put("device_info", "WEB");
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(tblOrderInfo.getTotalAmount().doubleValue()*100));
        data.put("spbill_create_ip", tblOrderInfo.getIp());
        data.put("notify_url", WechatConstants.NOTIFY_URL);
        data.put("trade_type", "");
        data.put("product_id", "12");
        // data.put("time_expire", "20170112104120");

        try {
            Map<String, String> r = wxpay.unifiedOrder(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
