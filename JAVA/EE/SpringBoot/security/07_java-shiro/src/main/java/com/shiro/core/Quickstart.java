package com.shiro.core;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {
        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        // 获取SecurityManager工厂，使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 得到SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        // 默认是org.apache.shiro.mgt.DefaultSecurityManager
        System.out.println("xzj----->" + securityManager.getClass());
        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) securityManager;
        Collection<Realm> realms = defaultSecurityManager.getRealms();
        System.out.println("xzj------>" + realms);
        Authorizer authorizer = defaultSecurityManager.getAuthorizer();
        System.out.println("xzj------>" + authorizer.getClass());

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        // 把SecurityManager实例绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        // 获取当前的 Subject(当前用户). 调用 SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        // 测试使用 Session 
        // 获取 Session: Subject#getSession()
        Session session = currentUser.getSession();
        session.setAttribute("xzj-someKey", "xzj-aValue");
        String value = (String) session.getAttribute("xzj-someKey");
        if (value.equals("xzj-aValue")) {
            log.info("---> Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        // 测试当前的用户是否已经被认证. 即是否已经登录. 
        // 调动 Subject 的 isAuthenticated() 
        if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            // 认证流程:
            // 这个UsernamePasswordToken对象中的username和password一般是从前端表单传过来的.
            // 然后通过Realm获取数据库中存放的用户名和密码
            // 然后Shiro帮我们把UsernamePasswordToken中的用户名和密码 和 Realm中的密码和用户名 去比对.
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // rememberme
            token.setRememberMe(true);
            try {
            	// 执行登录.
                // 能否登录成功取决于shiro.ini配置文件中的用户名和密码是否和UsernamePasswordToken中的一致)
                currentUser.login(token);
            } 
            // 若没有指定的账户(未知的账户), 则 shiro 将会抛出 UnknownAccountException 异常. 
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return; 
            } 
            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。 
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return; 
            } 
            // 用户被锁定的异常 LockedAccountException
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的 父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("----> User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        // 测试是否有某一个角色. 调用 Subject 的 hasRole 方法.
        //判断是否拥有多个角色：
        // boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        // 然后依次遍历result[index]
        if (currentUser.hasRole("schwartz")) {
            log.info("----> May the Schwartz be with you!");
        } else {
            log.info("----> Hello, mere mortal.");
            return;
        }

        //test a typed permission (not instance-level)
        // 测试用户是否具备某一个行为. 调用 Subject 的 isPermitted() 方法。 
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("----> You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        // 测试用户是否具备某一个行为. 
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("----> You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        // 执行登出. 调用 Subject 的 Logout() 方法. 
        System.out.println("---->" + currentUser.isAuthenticated());
        
        currentUser.logout();
        
        System.out.println("这个用户是否被认证了(因为logout了,所以为false)---->" + currentUser.isAuthenticated());

        System.exit(0);
    }
}
