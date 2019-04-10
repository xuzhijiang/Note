package org.java.core.advanced.proxy.part1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserServiceCallback implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("start Transaction by cglib..");
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("end Transaction by cglib..");
        return result;
    }
}
