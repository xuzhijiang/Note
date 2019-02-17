package com.jinxuliang.spring_mvc_controller_demo.controller;

import com.jinxuliang.spring_mvc_controller_demo.domain.DemoObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

// 用于展示转发和重定向功能的编程技巧

// 请特别注意一下在请求之间如何传送数据。
@Controller
@RequestMapping("/redir")
public class RedirectController {

    //显示转发传过来的数据
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request){
        //将数据传给视图
        model.addAttribute("message",request.getAttribute("message"));
        return "redir/index";
    }

    //实现转发
    @RequestMapping("/forward")
    public String forward(HttpServletRequest request){
        //转发时需要保存的数据
        request.setAttribute("message","从/forward转发过来");
        return "forward:/redir/index";
    }


    //重定向的实现(使用redirect实现）

    //从Session中提取数据，然后显示在视图中
    @RequestMapping("/showInfo")
    public String showInfo(@ModelAttribute("demo_obj") DemoObject obj,
                           Model model){
        //将数据传给视图
        model.addAttribute("demo_obj",obj);
        return "redir/show";
    }

    //重定向过程中传送数据
    @RequestMapping("/redirToShow")
    public String redirect(RedirectAttributes ra){
        DemoObject obj=new DemoObject(100l,"hello");
        // 发生了重定向的两个请求之间，也可以保存数据，这是通过Session实现的。
        //保存重定向数据到Session中
        ra.addFlashAttribute("demo_obj",obj);
        return "redirect:/redir/showInfo";
    }
}