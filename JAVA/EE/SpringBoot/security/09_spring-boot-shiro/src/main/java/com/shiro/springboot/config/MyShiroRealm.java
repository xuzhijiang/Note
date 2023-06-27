package com.shiro.springboot.config;

import com.shiro.springboot.entity.User;
import com.shiro.springboot.sevice.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 自定义Realm
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 到数据库查询当前登录用户的permission
        // authorizationInfo.addStringPermission("user:add");//添加资源的授权字符串
        // authorizationInfo.addRole();
        //到数据库查询当前登录用户的授权字符串
        //Subject subject = SecurityUtils.getSubject();
        //User user = (User)subject.getPrincipal();
        User user = (User) principals.getPrimaryPrincipal(); // 这里得到的就是下面SimpleAuthenticationInfo中的user
        System.out.println("AuthorizationInfo-------: " + user.getUsername());
        String[] perms = user.getPerms().split(",");
        System.out.println("xzj--------->" + Arrays.toString(perms));
        for (String perm : perms) {
            authorizationInfo.addStringPermission(perm);
        }
        System.out.println("xxxx-------->" + authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑(也就是验证用户输入的账号和密码是否正确)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        System.out.println("----->>userInfo="+ user);
        if(user == null){
            //用户名不存在
            return null; //shiro底层会抛出UnKnowAccountException
        }

        //2.判断密码
        Object password = user.getPassword(); // 尤其要注意这里的密码是从数据库中取出来的密码
        ByteSource salt = ByteSource.Util.bytes(username);
        String realmName = getName();
        return new SimpleAuthenticationInfo(user, password, salt, realmName);
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object password = "123456";  // 原始密码
        ByteSource salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        // 加密1024次之后,在这个基础之上再次加盐得到的最终的保存在数据库中的密码
        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        System.out.println("result: " + result);
    }
}