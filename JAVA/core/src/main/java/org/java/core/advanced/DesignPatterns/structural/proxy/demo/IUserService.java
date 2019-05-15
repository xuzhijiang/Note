package org.java.core.advanced.DesignPatterns.structural.proxy.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理模式
 *
 * 代理模式的作用是使用一个代理类来代替原先类进行操作。比较常见的就是aop中就是使用代理模式完成事务的处理。
 *
 * 代理模式分静态代理和动态代理，静态代理的原理就是对目标对象进行封装，最后调用目标对象的方法即可。
 *
 * 动态代理跟静态代理的区别就是动态代理中的代理类是程序运行的时候生成的。
 * Spring中对于接口的代理使用jdk内置的Proxy和InvocationHandler实现，对于类的代理使用cglib完成。
 *
 * 以1个UserService为例，使用jdk自带的代理模式完成计算方法调用时间的需求：
 */
public interface IUserService {// UserService接口
    void printAll();
}

// UserService实现类
class UserService implements IUserService {

    @Override
    public void printAll() {
        System.out.println("print all users");
    }

}

// InvocationHandler策略，这里打印了方法调用前后的时间
class UserInvocationHandler implements InvocationHandler {

    private IUserService userService;

    public UserInvocationHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start: " + System.currentTimeMillis());
        Object result = method.invoke(userService, args);
        System.out.println("end: " + System.currentTimeMillis());
        return result;
    }

}