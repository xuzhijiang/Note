package com.qianfeng.controller;

import org.apache.shiro.authz.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 权限校验相关的注解 需要在mvc.xml中配置 AuthorizationAttributeSourceAdvisor,否则权限注解不可以使用
// AuthorizationAttributeSourceAdvisor作用其实就是利用aop,给类生成代理类,然后在前后进行增强.
@Controller
@RequestMapping("/order")
public class OrderController {

    @RequiresUser
    @RequestMapping("/query")
    public String queryOrder(){
        System.out.println("query Order");
        return "index";
    }
    @RequiresAuthentication
    @RequiresRoles(value = {"banzhang","student"},logical = Logical.OR)
    @RequestMapping("/save")
    public String saveOrder(){
        System.out.println("save Order");
        return "index";
    }

//    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @RequiresPermissions(value={"user:delete","user:query"},logical = Logical.AND)
    @RequestMapping("/delete")
    public String deleteOrder(){
        System.out.println("delete Order");
        return "index";
    }

    @RequiresAuthentication
    @RequestMapping("/update")
    public String updateOrder(){
        System.out.println("update Order");
        return "index";
    }
}
