package com.abitty.controller;

import com.abitty.entity.TblUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by kkk on 17/6/11.
 */
@Controller
public class ViewController {

    @RequestMapping("/view/*")
    public String myService(Model model, HttpSession httpSession) {
        TblUser tblUser = (TblUser) httpSession.getAttribute("user");
        if (tblUser != null) {
            model.addAttribute("uid", tblUser.getUid());
        }
        return "view";
    }
}
