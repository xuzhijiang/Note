package org.java.core.advanced.proxy.DynamicProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class CGLibDynamicProxyDemo {

    @Test
    public void testCGLibProxy1() {
        Enhancer enhancer = new Enhancer();
        // 设置生成代理类的父类class对象, 传入的不能为接口
        enhancer.setSuperclass(CalculatorImpl.class);
        // 设置生成代理类的父类class对象
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                // 在拦截器内部，调用目标方法前进行前置和后置增强处理
                System.out.println("方法拦截增强逻辑-前置处理执行");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("方法拦截增强逻辑-后置处理执行");
                return result;
            }
        });
        // 生成代理类并实例化
        Calculator calculatorProxyImpl = (Calculator) enhancer.create();
        // 生成代理类并实例化
        // 实际调用的是new UserServiceCallback()的intercept方法.
        calculatorProxyImpl.add(3, 4);
    }
}
