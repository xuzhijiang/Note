package com.shiro.tutorial.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class Realm2 implements Realm {
    @Override
    public String getName() {
        return "realm2";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户名
        String username = (String) token.getPrincipal();
        // 得到密码
        String password = new String((char[])token.getCredentials());

        System.out.println("realm: " + getName() + " - username: " + username + " - password: " + password);
        if(!"error_account".equals(username)){
            throw new UnknownAccountException();
        }

        if(!"123".equals(password)){
            throw new IncorrectCredentialsException();
        }

        //如果身份认证验证成功，返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
