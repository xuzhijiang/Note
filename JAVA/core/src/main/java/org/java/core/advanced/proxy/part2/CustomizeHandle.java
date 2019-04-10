package org.java.core.advanced.proxy.part2;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomizeHandle implements InvocationHandler {

    private Object target;

    // 其中构造方法传入被代理类的类类型。其实传代理类的实例或者是类类型并没有强制的规定，
    // 传类类型的是因为被代理对象应当由代理创建而不应该由调用方创建。
    public CustomizeHandle(Class clazz){
        try {
            this.target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        System.out.println("class: " + proxy.getClass());
        return result;
    }

    private void before(){
        System.out.println("execute method before");
    }

    private void after(){
        System.out.println("execute method after");
    }

}
