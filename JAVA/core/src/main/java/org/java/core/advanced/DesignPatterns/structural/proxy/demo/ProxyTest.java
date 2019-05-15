package org.java.core.advanced.DesignPatterns.structural.proxy.demo;

import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        IUserService userService = new UserService();
        UserInvocationHandler userInvocationHandler = new UserInvocationHandler(userService);
        IUserService proxy = (IUserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                new Class[] {IUserService.class}, userInvocationHandler);
        proxy.printAll();
    }

}
