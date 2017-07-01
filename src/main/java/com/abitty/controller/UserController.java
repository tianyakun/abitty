package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.dto.UserDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.UserService;
import com.abitty.vo.UserVo;
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
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by yak on 17/6/12.
 */
@Controller
@RequestMapping(value = "/my/account")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "")
    @ResponseBody
    public ResponseDto getUser(HttpServletRequest request, HttpSession session) {

        TblUser tblUser = (TblUser) session.getAttribute("user");

        logger.info("查看用户信息请求 uid={}", tblUser.getUid());

        ResponseDto responseDto = new ResponseDto();

        try {
            UserVo vo = new UserVo();
            vo.setUid(tblUser.getUid());
            vo.setGender(tblUser.getGender());
            if (tblUser.getBirthday() == null) {
                vo.setBirthday("");
            } else {
                vo.setBirthday(DATE_FORMAT.format(tblUser.getBirthday()));
            }

            responseDto.addAttribute("item", vo);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());

        } catch (Exception e) {
            logger.error("查看用户信息异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("查看用户信息返回 responseDto={}", responseDto);

        return responseDto;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto updateAddress(UserDto userDto, HttpSession session) {

        TblUser tblUser = (TblUser) session.getAttribute("user");

        logger.info("修改用户信息请求 uid={} userDto={}", tblUser.getUid(), userDto);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (!Strings.isNullOrEmpty(userDto.getGender())) {
                tblUser.setGender(userDto.getGender());
            }

            if (!Strings.isNullOrEmpty(userDto.getBirthday())) {
                tblUser.setBirthday(DATE_FORMAT.parse(userDto.getBirthday()));
            }

            userService.update(tblUser);

            session.setAttribute("user", tblUser);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
        }

        logger.info("修改用户信息返回 {}", responseDto);
        return responseDto;
    }

}
