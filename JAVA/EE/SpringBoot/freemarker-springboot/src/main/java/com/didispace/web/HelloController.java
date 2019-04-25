package com.didispace.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;

import java.text.SimpleDateFormat;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "MyHost");
        return "index";
    }

    @RequestMapping(path = "/getDate", method = RequestMethod.GET)
    public ModelAndView getDate() {
        // 模板文件的名称，不需要指定后缀
        ModelAndView mv = new ModelAndView("date");
        // 设置模板的名称
        // mv.setViewName("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mv.addObject("date", sdf.format(new Date()).toString());
        return mv;
    }
}
