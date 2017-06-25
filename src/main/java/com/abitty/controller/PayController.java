package com.abitty.controller;

import com.abitty.biz.PayNotifyBiz;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yak on 17/6/25.
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController {

    private final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PayNotifyBiz payNotifyBiz;

    @RequestMapping(value = "/notify")
    @ResponseBody
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> notifyMap = transformToMap(request.getParameterMap());
        logger.info("支付结果通知 {}", notifyMap);

        String responseBody = payNotifyBiz.receivePayNotify(notifyMap);


        return;
    }

    /**
     * 把 Map<String, String[]> 转成 Map<String, String>
     * @param map
     * @return
     */
    private Map<String, String> transformToMap(Map<String, String[]> map) {
        Map<String, String> result =  Maps.newHashMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if (value == null || value.length == 0) {
                result.put(key, "");
            } else {
                result.put(key, value[0]);
            }
        }
        return result;
    }

}
