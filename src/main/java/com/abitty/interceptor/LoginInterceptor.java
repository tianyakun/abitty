package com.abitty.interceptor;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yak on 17/6/17.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestUri = request.getRequestURI(); //请求完整路径，可用于登陆后跳转
        String contextPath = request.getContextPath();  //项目下完整路径
        String url = requestUri.substring(contextPath.length()); //请求页面

        logger.info("登录拦截器处理请求: requestUri={}", requestUri);

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                logger.info("interceptor cookie: name={} value={}", cookie.getName(), cookie.getValue(), cookie.getPath());
            }
        }

        HttpSession session = request.getSession();

        TblUser user = (TblUser)session.getAttribute("user");

        if(user != null){  //判断用户是否存在，不存在返回登录界面，继续拦截，存在通过拦截，放行到访问页面
            logger.info("用户已登录, uid={}", user.getUid());
            return true;
        }

        /**
         * 拦截目录下请求，是否为ajax请求
         *   是：返回用户未登录
         *   否：跳转至登录界面
         */
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            //如果是ajax请求响应头会有，x-requested-with
            logger.info("用户未登录,ajax请求拦截,返回用户未登录!");

            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                writer = response.getWriter();
                ResponseDto responseDto = new ResponseDto();
                responseDto.setRetCode(ExceptionEnum.NOT_LOGIN.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.NOT_LOGIN.getErrorMsg());
                String json = JSON.toJSONString(responseDto);
                logger.info("拦截返回 {}", json);
                writer.print(json);
            } catch (IOException e) {
                logger.error("response error",e);
            } finally {
                IOUtils.closeQuietly(writer);
            }
        }else{
            logger.info("用户未登录,普通请求拦截,跳转至登录页!");
            request.getRequestDispatcher("/view/login").forward(request, response);//转发到登录界面
        }
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
