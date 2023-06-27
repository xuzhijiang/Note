package com.shiro.spring.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        // 为什么使用LinkedHashMap,因为它是有顺序的.下面这些配置不能调.
        map.put("/login.jsp", "anon");
        map.put("/shiro/login", "anon");
        map.put("/shiro/logout", "logout");

        // 认证之后(authc),同时具有user这个role才可以访问/user.jsp,此时rememberme就不可以访问/user.jsp和/admin.jsp了
        map.put("/user.jsp", "authc,roles[user]");
        map.put("/admin.jsp", "authc,roles[admin]");
        // 这个/list.jsp不要求认证之后(authc),也就是rememberme也是可以访问的
        map.put("/list.jsp", "user");

        map.put("/**", "authc"); // 其他所有的页面都需要认证才可以访问.

        return map;
    }
}
