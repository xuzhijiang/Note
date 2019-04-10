package org.java.core.advanced.proxy.part1;

import net.sf.cglib.proxy.Enhancer;
import org.java.core.advanced.proxy.part1.interceptor.TransactionInterceptor;
import org.java.core.advanced.proxy.part1.service.UserService;
import org.java.core.advanced.proxy.part1.service.impl.UserServiceImpl;

import java.lang.reflect.Proxy;

public class TestDynamicProxy {

    public static void main(String[] args) {
//        testJDKProxy();

        testCGLibProxy();
    }

    private static void testCGLibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new UserServiceCallback());
        // create a proxy object for UserServiceImpl
        UserService userService = (UserService) enhancer.create();
        // 实际调用的是new UserServiceCallback()的intercept方法.
        userService.removeUser();
    }

    private static void testJDKProxy() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        UserServiceImpl userService = new UserServiceImpl();
        interceptor.setTarget(userService);
        // Proxy 提供静态方法用于创建动态代理类和实例
        // create a proxy object for UserService.
        // 每个代理实例都具有一个关联的InvocationHandler,这里也就是interceptor
        // 对代理实例调用方法时，这个方法会调用InvocationHandler实现类的invoke方法。
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                interceptor);
        // userServiceProxy调用的addUser实际上是interceptor里面的target对象的addUser()
        userServiceProxy.addUser();
    }
}
