package org.java.core.advanced.proxy.part1.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 使用动态代理模拟处理事务的拦截器
public class TransactionInterceptor implements InvocationHandler {

    private Object target;

    public void setTarget(Object obj){
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start transaction...");
        method.invoke(target, args);
        System.out.println("end transaction....");
        return null;
    }

}
