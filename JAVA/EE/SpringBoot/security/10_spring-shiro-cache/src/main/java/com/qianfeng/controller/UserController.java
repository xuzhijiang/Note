package com.qianfeng.controller;

import com.qianfeng.pojo.User;
import com.qianfeng.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        System.out.println("goto login page");
        return "login";
    }

    @PostMapping("/login")
    public String loginLogic(User user){
        System.out.println("login logic");
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据,创建用于登录的令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 开启记住我
        token.setRememberMe(true);
        // 执行登录方法, 登录失败会抛出异常，则交由异常解析器处理
        subject.login(token);
        return "index";// 登录成功，跳转首页
        // return "redirect:/users";
    }

    @GetMapping("/all")
    public String queryAllUsers(HttpSession session){
        System.out.println("query all users");
        // https://www.bilibili.com/video/BV1q7411r7WQ?p=23
        // shiro session的类型: ShiroHttpSession, 是HttpSession的子类
        // ShiroHttpSession保存了用户是否认证了,保存了用户的凭证,以及登录用户的权限信息,所以后续判断是否登录,是否有权限访问资源,只需要调用Session的方法即可
        System.out.println(session.getClass());
        session.setAttribute("name","松松");
        return "index";
    }

    @GetMapping("users")
    public String queryAllUsers(Model model){
        List<User> users = userService.selectAllUsers();
        model.addAttribute("users",users);
        return "forward:/users.jsp";
    }

    @RequestMapping("/perms/error")
    public String permsError(){
        System.out.println("权限不足");
        return "perm_error";
    }

    @GetMapping("/regist")
    public String registPage(){
        System.out.println("goto regist page");
        return "regist";
    }

    @PostMapping("/regist")
    public String regist(User user){
        userService.insertUser(user);
        return "redirect:/user/login";
        //return "redirect:/user/users";
    }

    /**
     * 登出： 清除 cookie  session  缓存
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "index";
    }
}