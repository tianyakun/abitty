package com.abitty.controller;

import com.abitty.dto.UserDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.UserService;
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
import java.util.Map;

/**
 * Created by yak on 17/6/12.
 */
@Controller
@RequestMapping(value = "/my")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/account")
    @ResponseBody
    public Map<String, Object> getUser(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String uid = request.getParameter("uid");

            if (Strings.isNullOrEmpty(uid)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {

                TblUser tblUser = userService.getUserByUid(uid);

                Map<String, Object> data = Maps.newHashMap();
                data.put("item", tblUser);

                resultMap.put("data", data);
                resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());

                return resultMap;
            }

        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAddress(UserDto userDto) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {

            if (Strings.isNullOrEmpty(userDto.getUid())) {
                logger.error("uid is null");
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            }

            TblUser tblUser = userService.getUserByUid(userDto.getUid());
            tblUser.setBirthday(userDto.getBirthday());
            tblUser.setGender(userDto.getGender());
            userService.update(tblUser);

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
