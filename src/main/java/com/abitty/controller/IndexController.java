package com.abitty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by kkk on 17/6/11.
 */
@Controller
public class IndexController {
    @RequestMapping("")
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/view/supports");
    }

}
