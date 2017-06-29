package com.abitty.controller;

import com.abitty.entity.TblUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kkk on 17/6/11.
 */
@Controller
public class ViewController {

    private final static Logger logger = LoggerFactory.getLogger(ViewController.class);

    @RequestMapping("/loginIndex")
    public String loginIndex(Model model, final HttpServletRequest request) {

        String requestUri = request.getRequestURI();
        logger.info("登录请求 requestUri={}", requestUri);

        model.addAttribute("uid", "");
        return "loginIndex";
    }

    @RequestMapping("/view/**")
    public String view(Model model, HttpSession httpSession, final HttpServletRequest request) {
        String requestUri = request.getRequestURI(); //请求完整路径，可用于登陆后跳转
        logger.info("视图请求 requestUri={}", requestUri);

        TblUser tblUser = (TblUser) httpSession.getAttribute("user");
        if (tblUser != null) {
            model.addAttribute("uid", tblUser.getUid());
        } else {
            model.addAttribute("uid", "");
        }
        return "view";
    }
}
