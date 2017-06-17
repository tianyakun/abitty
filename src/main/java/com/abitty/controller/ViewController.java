package com.abitty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kkk on 17/6/11.
 */
@Controller
//@RequestMapping("/view")
public class ViewController {

    @RequestMapping(value = "/hello")
    public String helloFtl(Model model) {
        model.addAttribute("name", "张三f");
        return "hello";
    }

    @RequestMapping(value = "/myService")
    public String myService(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/myService/:id")
    public String myServiceId(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/supports")
    public String supports(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/supports/:id")
    public String supportsId(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/select")
    public String select(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/book")
    public String book(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/book/Suc")
    public String bookSuc(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "user")
    public String user(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/user/person")
    public String userPerson(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/user/adress")
    public String userAdress(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/feedback")
    public String feedback(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

    @RequestMapping(value = "/feedback/Suc")
    public String feedbackSuc(Model model) {
        model.addAttribute("userName", "张三丰");
        return "view";
    }

}
