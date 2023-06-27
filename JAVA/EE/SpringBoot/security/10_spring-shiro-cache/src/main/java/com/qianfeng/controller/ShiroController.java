package com.qianfeng.controller;

import org.apache.shiro.authz.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ShiroController
 * @Description: 测试shiro的各种校验 注解
 * @Author 臧红久
 * @Date 2019/10/15
 * @Version V1.0
 **/
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @RequiresAuthentication//需要合法的用户身份，"记住我不能通过校验"
    @RequestMapping("test1")
    public String test1(){
        System.out.println("通过验证，已经正常登录");
        return "index";
    }

    @RequiresUser // 需要合法的用户身份 或 记住我
    @RequestMapping("test2")
    public String test2(){
        System.out.println("通过校验，有记住我或已登录");
        return "index";
    }

    @RequiresAuthentication
    @RequiresPermissions(value={"user:query","user:delete"},logical = Logical.OR)
    @RequestMapping("/test3")
    public String test3(){
        System.out.println("已登录，有user:query权限 或 有user:delete权限");
        return "index";
    }

    @RequiresAuthentication
    @RequiresPermissions(value={"user:query","user:delete"},logical = Logical.AND)
    @RequestMapping("/test32")
    public String test3_2(){
        System.out.println("已登录，有user:query权限 且 有user:delete权限");
        return "index";
    }

    @RequiresAuthentication
    @RequiresRoles(value={"banzhang","student"},logical = Logical.OR)
    @RequestMapping("/test4")
    public String test4(){
        System.out.println("已登录，是班长 或 是学生");
        return "index";
    }

    @RequiresAuthentication
    @RequiresRoles(value={"banzhang","student"},logical = Logical.AND)
    @RequestMapping("/test42")
    public String test4_2(){
        System.out.println("已登录,是学生且是班长");
        return "index";
    }

    @RequestMapping("/ses")
    public String test_session(HttpSession session){
        System.out.println(session.getClass());
        session.setAttribute("name","松松");
        return "index";
    }
}
