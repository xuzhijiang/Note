package com.springboot.core.controller;

import com.springboot.core.bean.DemoObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

// 用于展示转发(forward)和重定向(redirect)
@Controller
@RequestMapping("/redRoot")
public class RedirectController {

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request){
        // get转发传过来的数据
        model.addAttribute("message",request.getAttribute("message"));
        return "redirect/index"; // 返回redirect/index这个模板
    }

    /**
     * 实现转发
     */
    @RequestMapping("/forward")
    public String forward(HttpServletRequest request){
        request.setAttribute("message","从/forward转发过来");
        return "forward:/redirect/index";
    }

    //重定向的实现(使用redirect实现）
    @RequestMapping("/showInfo")
    public String showInfo(@ModelAttribute("demo_obj") DemoObject obj,
                           Model model){
        //从Session中提取数据，然后显示在视图中
        model.addAttribute("demo_obj",obj);
        return "redirect/show";
    }

    //重定向过程中传送数据
    @RequestMapping("/redirToShow")
    public String redirect(RedirectAttributes ra){
        DemoObject obj=new DemoObject(100L,"hello");
        // 发生了重定向的两个请求之间，也可以保存数据，这是通过Session实现的。
        //保存重定向数据到Session中
        ra.addFlashAttribute("demo_obj",obj);
        return "redirect:/redirect/showInfo";
    }
}