package com.springboot.cors.controller;

import com.springboot.cors.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

// 或者在控制器（@Controller）上使用注解 @CrossOrigin
// 其中origins表示允许访问的源,*表示任何源,methods表示允许请求的方法.
// @CrossOrigin(origins = "*", methods = {RequestMethod.GET}, maxAge = 3600)
@Controller
public class CrossOriginController {

    // 在方法上（@RequestMapping）使用注解 @CrossOrigin
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}, maxAge = 3600)
    @ResponseBody
    @RequestMapping(value = "/ajax", method = {RequestMethod.GET, RequestMethod.POST})
    public String ajax(HttpServletRequest request) {
        // 打印header
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(String.format("key: %s  -------->   value: %s", key, request.getHeader(key)));
        }

        // 我们这里可以遍历所有传上来的参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            System.out.println(String.format("key: %s  ------>  value: %s", key, request.getParameter(key)));
        }

        // 打印body
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                System.out.println("body: " + new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{code: 0, content: '返回内容'}";
    }

    // 在方法上（@RequestMapping）使用注解 @CrossOrigin
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}, maxAge = 3600)
    @ResponseBody
    @RequestMapping(value = "/fetch", method = {RequestMethod.GET, RequestMethod.POST})
    public User fetch(HttpServletRequest request) {
        // 打印header
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(String.format("key: %s  -------->   value: %s", key, request.getHeader(key)));
        }

        // 我们这里可以遍历所有传上来的参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            System.out.println(String.format("key: %s  ------>  value: %s", key, request.getParameter(key)));
        }

        // 打印body
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                System.out.println("body: " + new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setUsername("xxxooo");
        return user;
    }

    // 在方法上（@RequestMapping）使用注解 @CrossOrigin
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}, maxAge = 3600)
    @ResponseBody
    @RequestMapping(value = "/axios", method = {RequestMethod.GET, RequestMethod.POST})
    public User axios(HttpServletRequest request) {
        // 打印header
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(String.format("key: %s  -------->   value: %s", key, request.getHeader(key)));
        }

        // 我们这里可以遍历所有传上来的参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            System.out.println(String.format("key: %s  ------>  value: %s", key, request.getParameter(key)));
        }

        // 打印body
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                System.out.println("body: " + new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setUsername("xxxooo");
        return user;
    }

    @RequestMapping(value = "/getUser")
    public User getUser() {
        User user = new User();
        user.setUsername("xxxxxooooo");
        return user;
    }

    @GetMapping(value = "/ajaxConfig")
    public String ajaxConfig(Model model) {
        model.addAttribute("info", "我是java配置的跨域");
        return "index";
    }

    @GetMapping(value = "/ajaxResponseHeader")
    public String ajaxResponseHeader(Model model, HttpServletResponse response) {
        // response.addHeader("Access-Control-Allow-Origin", "*"); // 放行哪些原始域,*表示全部
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        model.addAttribute("info", "我是添加header的跨域方式");
        return "index";
    }

    @GetMapping(value = "/jsonp")
    @ResponseBody
    public User jsonp(Model model, HttpServletRequest request, HttpServletResponse response) {
        // callback是传入的 一个函数名
        // String funcname = request.getParameter("callback");
        // Map<String, String[]> params = request.getParameterMap();
        // 将需要返回的数据内容作为这个直接调用函数
        // funcname('你好')
        User user = new User();
        user.setUsername("xxxxxooooo");
        return user;
    }
}
