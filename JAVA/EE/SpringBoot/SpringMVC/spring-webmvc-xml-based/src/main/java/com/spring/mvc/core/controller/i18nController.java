package com.spring.mvc.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

// 国际化测试
@Controller
public class i18nController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "login";
    }
}