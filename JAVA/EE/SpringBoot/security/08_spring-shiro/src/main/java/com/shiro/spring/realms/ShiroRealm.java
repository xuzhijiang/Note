package com.shiro.spring.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

// AuthorizingRealm继承了AuthenticatingRealm,所以可以完成认证和授权2个功能.
// 我们定义Realm一般继承 AuthorizingRealm（授权）即可；其继承了 AuthenticatingRealm（认证），而且也间接继承了 CachingRealm（带有缓存实现）
public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这个token就是传过来的UsernamePasswordToken,携带了从前端表单提交的用户信息.
        // 这个方法抛出的异常正好被com.shiro.spring.controller.ShiroController捕获.
        System.out.println("[ShiroRealm] doGetAuthenticationInfo");

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2. 从 UsernamePasswordToken 中来获取 username(前端表单输入的username)
        String username = upToken.getUsername();
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        // 这里真实项目是从数据库中查询,但是这里为了简单,就不再查询了.
        System.out.println("从数据库中获取: " + username + " 所对应的用户信息.");
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在!");
        }
        //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
        if("monster".equals(username)){
            throw new LockedAccountException("用户被锁定");
        }


        //6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        // 以下信息是从数据库中获取的.
        //1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        Object principal = username;
        //2). credentials: 密码. (这个密码是从数据库中获取的密码)
        Object credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
        if ("admin".equals(username)) {
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e"; // 这些密码是根据username从数据库取的
        } else if("user".equals(username)) {
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        //3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();
        //4). 盐值. 这里是使用用户名作为 盐
        // 因为我们加盐的原因就是想让2个即使密码相同的用户,在数据库中保存的密码依然不同,为了更安全
        // 所以这里的盐值不要搞的相同
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //new SimpleAuthenticationInfo(principal, credentials, realmName); // 不加盐的构造器
        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

        // 把从数据库中获取的密码封装成SimpleAuthenticationInfo,
        // 这样的话有2个对象,一个对象是UsernamePasswordToken,这个对象保存了前台输入的密码.
        // 还有一个叫SimpleAuthenticationInfo,这里面保存了从数据库查询的密码.
        // 然后密码的比对是由Shiro来完成的(比对这两个对象中的密码)
        // 问题来了,怎么比对的呢?哪里比对的呢?
        // 可以通过UsernamePasswordToken的getPassword()方法加断点来追踪是哪里比对的.
        return info;
    }

    //授权会被 shiro 回调的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        // 多Realm的时候,你获取principals.getPrimaryPrincipal()和我们多Realm的定义顺序有关系
        // 因为在LinkedHashMap中放的时候是有顺序的.
        Object principal = principalCollection.getPrimaryPrincipal(); // 获取主要的Principal (说白了就是用户名)

        //2. 根据登录的用户的信息,查找数据库,查询出当前用户的角色有哪些
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(principal)) {
            roles.add("admin");
        }

        //3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);

        //4. 返回 SimpleAuthorizationInfo 对象.
        // 然后shiro会根据用户要访问的url资源,看看访问这个url需要什么角色,
        // 然后从SimpleAuthorizationInfo 对象中拿出当前用户拥有的角色进行比对
        // 看看当前用户是否拥有权限.
        return authorizationInfo;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object password = "123456";  // 原始密码
        // 为什么要加盐呢?因为可能2个人的密码一样,加密之后的字符串也一样,
        // 这个时候还是有一些不安全性的,我们希望,即便是2个人的原始密码一样,
        // 加密之后的结果也不要一样,这个时候就需要加盐.
        ByteSource salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        // 加密1024次之后,在这个基础之上再次加盐得到的最终结果
        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        System.out.println("result: " + result);
    }
}
