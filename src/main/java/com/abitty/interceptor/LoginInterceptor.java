package com.abitty.interceptor;

import com.abitty.entity.TblUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yak on 17/6/17.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = request.getSession();

        TblUser user = (TblUser)session.getAttribute("user");

        if(user != null) {
            return true;
        }

        String callback = request.getRequestURL().toString();
        logger.info("用户未登录, 访问拦截 {}", callback);

//        session.setAttribute("callback", callback);
//        response.sendRedirect(request.getContextPath() + "/loginIndex?callback=" + callback);
        response.sendRedirect(request.getContextPath() + "/loginIndex");
//        request.getRequestDispatcher("/loginIndex").forward(request, response);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
