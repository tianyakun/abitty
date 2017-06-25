package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.enums.ExceptionEnum;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yak on 17/6/25.
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController {

    private final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @RequestMapping(value = "/notify")
    @ResponseBody
    public void notify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("微信支付结果通知 {}", httpServletRequest);


        return;
    }

}
