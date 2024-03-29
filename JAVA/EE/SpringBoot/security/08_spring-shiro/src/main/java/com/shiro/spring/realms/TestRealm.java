package com.shiro.spring.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

// Realm分为认证的Realm和授权的Realm
// 认证的Realm需要继承AuthenticatingRealm
// 授权的Realm需要继承AuthorizingRealm,同时实现doGetAuthorizationInfo这个方法
public class TestRealm extends AuthorizingRealm {
    //用于授权的方法.
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        return null;
    }

    //用于认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
