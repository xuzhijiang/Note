package com.shiro.spring.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

// 这里面有一个需要注意:
// 开发的时候往往会在Service上添加一个@Transactional的注解
// 即让service方法开始的时候会有事务,那么这个时候,这个Service已经是一个代理对象了,
// 这个时候你把这些@RequiresRoles注解加到service层是不好用的,这个时候需要加到Controller层
// 就是说你不能让service是代理的代理.因为会发生类型转化异常.
@Service
public class ShiroService {

    // 没有权限的用户访问会抛异常,我们可以使用spring的声明式异常
    // 给我们弄出一个错误的页面,(@ExceptionHandler和@ControllerAdvice-springmvc的内容)
    @RequiresRoles({"admin"})
    public void testMethod(){
        System.out.println("ShiroService--->testMethod: " + new Date());

        // 使用shiro session的好处?
        // 我在ShiroHandler层我用的是HttpSession,但是我在Service层可以使用
        // shiro提供的session来获取ShiroHandler层中HttpSession的数据,
        // 这样提供了一个好处,我既便是在service层,也可以访问到Httpsession的数据,
        // 开发的时候,有的时候还是比较方便的.这个有时候也是shiro session一个
        // 非常重要的应用.
        Session session = SecurityUtils.getSubject().getSession();
        Object val = session.getAttribute("key");
        System.out.println("Service SessionVal: " + val);
    }
}
