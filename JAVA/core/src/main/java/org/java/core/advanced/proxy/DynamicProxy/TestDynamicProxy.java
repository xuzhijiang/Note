package org.java.core.advanced.proxy.DynamicProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * cglib 是对一个小而快的字节码库, `ASM` 的封装。
 * 他的特点是继承于被代理类(不能传入接口)，这就要求被代理类不能被 `final` 修饰。
 */
public class TestDynamicProxy {

    @Test
    public void testCGLibProxy1() {
        Enhancer enhancer = new Enhancer();
        // 传入的不能为接口
        enhancer.setSuperclass(CalculatorImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("start Transaction by cglib..");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("end Transaction by cglib..");
                return result;
            }
        });
        // 创建CalculatorImpl的代理类
        Calculator calculatorProxyImpl = (Calculator) enhancer.create();
        // 实际调用的是new UserServiceCallback()的intercept方法.
        calculatorProxyImpl.add(3, 4);
    }
}
