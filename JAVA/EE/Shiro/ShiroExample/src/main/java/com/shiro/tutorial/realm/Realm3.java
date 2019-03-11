package com.shiro.tutorial.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Realm3 implements Realm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getName() {
        return "Realm3";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //得到用户名
        String username = (String) token.getPrincipal();
        // 得到密码
        String password = new String((char[])token.getCredentials());

        System.out.println("realm: " + getName() + " - username: " + username + " - password: " + password);
        if(!"zhang".equals(username)){
            throw new UnknownAccountException();
        }

        if(!"123".equals(password)){
            throw new IncorrectCredentialsException();
        }

        //如果身份认证验证成功，返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(username + "@qq.com", password, getName());
    }

}
