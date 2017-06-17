package com.abitty.controller;

import com.abitty.dto.LoginDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.MessageService;
import com.abitty.service.UserService;
import com.abitty.utils.ParamChecker;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by yak on 17/6/17.
 */
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAddress(LoginDto loginDto, HttpServletRequest httpServletRequest) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            //参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(loginDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            }

            if (messageService.verify(loginDto.getMessageId(), loginDto.getVerifyCode())) {
                TblUser tblUser = userService.getUserByUid(loginDto.getPhone());

                if (tblUser == null) {
                    tblUser = new TblUser();
                    tblUser.setUid(loginDto.getPhone());
                    tblUser.setPhone(loginDto.getPhone());
                    tblUser.setCreateTime(new Date());
                    tblUser.setLastLoginTime(new Date());

                    userService.add(tblUser);
                } else {
                    tblUser.setLastLoginTime(new Date());
                    userService.update(tblUser);
                }

                httpServletRequest.getSession().setAttribute("uid", tblUser.getUid());

            } else {
                resultMap.put("retCode", ExceptionEnum.VERIFY_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.VERIFY_INVALID.getErrorMsg());
                return resultMap;
            }

            resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());
            return resultMap;
        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

}
