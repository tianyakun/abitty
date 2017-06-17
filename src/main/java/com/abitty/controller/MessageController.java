package com.abitty.controller;

import com.abitty.enums.ExceptionEnum;
import com.abitty.service.MessageService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by yak on 17/6/17.
 */
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAddress(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String phone = request.getParameter("phone");

            if (Strings.isNullOrEmpty(phone)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                String messageId = messageService.sendVerifyCode(phone);

                Map<String, Object> data = Maps.newHashMap();
                data.put("messageId", messageId);

                resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());
                resultMap.put("data", data);
                return resultMap;
            }

        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }
}
