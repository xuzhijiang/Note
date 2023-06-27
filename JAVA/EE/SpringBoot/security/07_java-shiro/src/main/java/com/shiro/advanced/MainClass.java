package com.shiro.advanced;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;

import java.util.Collection;

public class MainClass {

    public static void main(String[] args) {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();

        // 设置PermissionResolver
        CustomPermissionResolver permissionResolver = new CustomPermissionResolver();
        authorizer.setPermissionResolver(permissionResolver);

        // 设置RolePermissionResolver
        CustomRolePermissionResolver rolePermissionResolver = new CustomRolePermissionResolver();
        authorizer.setRolePermissionResolver(rolePermissionResolver);

        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();

        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) securityManager;
        defaultSecurityManager.setAuthorizer(authorizer);
        // 设置realms (IniRealm直接加载ini,然后根据ini文件中配置的用户名和密码来判断用户是否可以登录成功)
        defaultSecurityManager.setRealm(new IniRealm("classpath:shiro-permission.ini"));
        Collection<Realm> realms = defaultSecurityManager.getRealms();
        System.out.println("xzj------>" + realms);

        // 设置SecurityManager到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        // 得到Subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        // 登录
        subject.login(token);

        System.out.println("xxxxx----->" + subject.isPermitted("user:update"));

        //新增权限
        System.out.println(subject.isPermitted("+user1+2"));
        //查看权限
        subject.isPermitted("+user1+8");
        //新增及查看
        subject.isPermitted("+user2+10");
        //没有删除权限
        subject.isPermitted("+user1+4");
        //通过MyRolePermissionResolver解析得到的权限
        subject.isPermitted("menu:view");

        // 检验是否有权限,如果没有权限,会抛出 UnauthorizedException 异常。
        subject.checkPermission("user:create");
        subject.checkPermissions("user:delete", "user:update");
        subject.checkPermissions("user:view");
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

}
