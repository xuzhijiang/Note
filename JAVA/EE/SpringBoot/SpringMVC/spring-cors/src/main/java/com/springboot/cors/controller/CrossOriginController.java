package com.springboot.cors.controller;

import com.springboot.cors.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

// 或者在控制器（@Controller）上使用注解 @CrossOrigin
// 其中origins表示允许访问的源,*表示任何源,methods表示允许请求的方法.
// @CrossOrigin(origins = "*", methods = {RequestMethod.GET}, maxAge = 3600)
@Controller
public class CrossOriginController {

    @RequestMapping(value = "/method01")
    @ResponseBody
    public User notSupportCrossOrigin() {
        return new User("zhangsan===>>>" + new Date());
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}, maxAge = 3600)
    @ResponseBody
    @RequestMapping(value = "/ajax", method = {RequestMethod.GET, RequestMethod.POST})
    public Map ajax(HttpServletRequest request) {
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

        Map<String, Object> map = new HashMap<>();
        map.put("username", "zhangsan");
        map.put("age", 18);
        map.put("gender", "male");
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        return map;
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
    public void jsonp(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // http://localhost:8080/jsonp?callback=foo
        // callback是传入的 一个函数名
         String functionName = request.getParameter("callback");
         response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(functionName + "('你好')"); // 相当于: foo('你哈')
    }
}
