package com.shiro.core;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

// 这些东西不用记忆,以后不这么用.只是了解过程.
public class TestShiro {
    // 测试shiro，Hello World
    public static void main(String[] args) {
        // 创建 "SecurityFactory",加载ini配置,并通过它创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro1.ini");
        // shiro核心：SecurityManager
        SecurityManager securityManager = factory.getInstance();

        // 将SecurityManager托管到SecurityUtils工具类中(ops:之后可以不必关心SecurityManager)
        SecurityUtils.setSecurityManager(securityManager);

        // subject作用：直接由用户使用，调用功能简单，其底层调用Securitymanager的相关流程
        Subject subject = SecurityUtils.getSubject();

        // 通过subject获取当前用户的登录状态（ops:从session中同步信息）
        System.out.println(subject.isAuthenticated());
        System.out.println("principal:"+subject.getPrincipal());

        // 通过subject 身份认证
        UsernamePasswordToken token = new UsernamePasswordToken("lisi", "456");

        subject.login(token);//登录过程

        System.out.println("principal:"+subject.getPrincipal());
        System.out.println(subject.isAuthenticated());

        //角色校验
        boolean isAdmin = subject.hasRole("admin");
        System.out.println(isAdmin);

        //权限校验
        System.out.println(subject.isPermitted("a:b"));
        System.out.println("user:insert:"+subject.isPermitted("user:insert"));

        // 登出：身份信息，登录状态信息，权限信息，角色信息，会话信息 全部抹除
        subject.logout();

        System.out.println(subject.isAuthenticated());
        System.out.println("principal:"+subject.getPrincipal());
    }
}
