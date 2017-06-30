package com.abitty.controller;

import com.abitty.biz.MessageProcessBiz;
import com.abitty.dto.LoginDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.MessageService;
import com.abitty.service.UserService;
import com.abitty.utils.ParamChecker;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by yak on 17/6/17.
 */
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageProcessBiz messageProcessBiz;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto login(LoginDto loginDto, HttpServletRequest httpServletRequest) {
        logger.info("用户登录请求 loginDto={}", loginDto);

        ResponseDto responseDto = new ResponseDto();

        try {
            //参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(loginDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
            } else if (!messageProcessBiz.checkValidateCode(loginDto.getPhone(), loginDto.getVerifyCode())) {
                logger.error("短信验证失败");
                responseDto.setRetCode(ExceptionEnum.VERIFY_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.VERIFY_INVALID.getErrorMsg());
            } else {
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

                HttpSession session = httpServletRequest.getSession();
                session.setAttribute("user", tblUser);

//                String callback = (String) session.getAttribute("callback");
//                session.removeAttribute("callback"); // 获取之后移除

                logger.info("用户登录成功 user={}", session.getAttribute("user"));

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("用户登录返回 responseDto={}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        try {

            HttpSession session = request.getSession();

            session.removeAttribute("user");

            logger.info("用户退出,跳转至商城首页");

            response.sendRedirect("/view/supports");
//            request.getRequestDispatcher("/view/supports").forward(request, response);//转发到登录界面

        } catch (Exception e) {
            logger.error("用户退出异常", e);
        }

    }

}
