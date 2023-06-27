package com.shiro.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShiroController {

    /**
     * 测试方法
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("UserController.hello()");
        return "ok";
    }

    @RequestMapping({"/","/index"})
    public String index(){
        return"index";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
                        @RequestParam(value = "password", defaultValue = "") String password,
                        Model model) throws Exception{
        System.out.println("登录: " + username + " , password: " + password);
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            //2.封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //3.执行登录方法
            try {
                currentUser.login(token);
                //登录成功,跳转到路由 "/"
                return "redirect:/";
            } catch (UnknownAccountException e) {
                // 登录失败:用户名不存在
                model.addAttribute("msg", "用户名不存在");
                return "login";
            } catch (IncorrectCredentialsException e) {
                //登录失败:密码错误
                model.addAttribute("msg", "密码错误");
                return "login";
            }
        }
        return "forward:/index";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userInfo/userAdd")
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userInfo/userList")
    public String userInfo(){
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("jjjjjjj->" + currentUser.isPermitted("user:view"));
        return "userInfo";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userInfo/userDel")
    @RequiresPermissions(value = {"user:del"})
    public String userDel(){
        return "userInfoDel";
    }
}