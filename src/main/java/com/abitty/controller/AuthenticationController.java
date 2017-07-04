package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by yak on 17/7/1.
 */
@Controller
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/auth")
    @ResponseBody
    public ResponseDto auth(HttpServletRequest request) {

        logger.info("用户鉴权请求");

        ResponseDto responseDto = new ResponseDto();

        try {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    logger.info("auth cookie: name={} value={}", cookie.getName(), cookie.getValue());
                }
            }

            HttpSession session = request.getSession();
            logger.info("auth sessionId={}", session.getId());

            TblUser user = (TblUser)session.getAttribute("user");

            if(user != null){  //判断用户是否存在，不存在返回登录界面，继续拦截，存在通过拦截，放行到访问页面
                logger.info("用户已登录, uid={}", user.getUid());
                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            } else {
                logger.info("用户未登录");
                responseDto.setRetCode(ExceptionEnum.NOT_LOGIN.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.NOT_LOGIN.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.NOT_LOGIN.getChineseMessage());
            }
        } catch (Exception e) {
            logger.error("用户鉴权请求异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("用户鉴权返回 {}", responseDto);
        return responseDto;
    }

}
