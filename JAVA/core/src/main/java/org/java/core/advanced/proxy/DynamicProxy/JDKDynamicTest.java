package org.java.core.advanced.proxy.DynamicProxy;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.*;

public class JDKDynamicTest {

    @Test
    public void testJDKDynamicPrimitive() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * param1: Calculator的类加载器(当初把Calculator加载进内存的类加载器)
         * param2: 代理对象需要和目标对象实现相同的接口Calculator
         * @return: 返回代理Class对象,这个代理Class对象这个类也是实现了Calculator接口
         */
        Class<?> calculatorProxyClazz = Proxy.getProxyClass(Calculator.class.getClassLoader(), Calculator.class);
        // 得到有参构造器 $Proxy0(InvocationHandler h),构造器里面参数的类型为InvocationHandler.
        Constructor<?> constructor = calculatorProxyClazz.getConstructor(InvocationHandler.class);
        // 反射创建代理实例
        Calculator calculatorProxyImpl = (Calculator) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 手动new一个目标对象
                CalculatorImpl calculatorImpl = new CalculatorImpl();
                // 反射执行目标对象方法
                Object result = method.invoke(calculatorImpl, args);
                // 返回目标对象执行结果
                return result;
            }
        });
        System.out.println(calculatorProxyImpl.add(1, 2));
    }

    @Test
    public void testJDKDynamicImprove() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CalculatorImpl target = new CalculatorImpl();
        // 传入目标对象
        // 1. 根据它的接口生成代理对象, 2. 代理对象调用目标对象的方法
        Calculator calculatorProxyImpl = (Calculator) getProxy(target);
        System.out.println(calculatorProxyImpl.add(2, 5));
    }

    private Object getProxy(final Object target) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //参数1：随便找个类加载器给它， 参数2：目标对象实现的接口，让代理对象实现相同接口
        Class<?> proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        Constructor<?> proxyClazzConstructor = proxyClazz.getConstructor(InvocationHandler.class);
        Object obj = proxyClazzConstructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = method.invoke(target, args);
                return result;
            }
        });
        return obj;
    }

    @Test
    public void testJdkDynamicFinally() {
        CalculatorImpl calculatorImpl = new CalculatorImpl();
        Calculator calculatorProxyImpl = (Calculator) getProxyFinally(calculatorImpl);
        System.out.println(calculatorProxyImpl.add(2, 5));
    }

    private Object getProxyFinally(final Object target) {
        Object obj = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Object result = method.invoke(target, args);
                            return result;
                        }
                    });
        return obj;
    }

    @Test
    public void testOutputClass() {
        // 其实jdk的代理类是由ProxyGenerator.generateProxyClass()动态创建出来
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass("$Proxy2", new Class[]{Calculator.class}, 1);
        // 将 proxyClassFile 输出到文件并进行反编译的话就可以的到代理类。(自己可以反编译一下)
        // 反编译可以看到代理类继承了 Proxy 类，并实现了 ISubject 接口，
        // 由此也可以看到 JDK 动态代理为什么需要实现接口，已经继承了 Proxy是不能再继承其余类了。
        //
        // 动态代理其中实现了ISubject 的 execute() 方法，并通过 InvocationHandler 中的 invoke() 方法来进行调用的。
        // 其中Proxy中有一个成员变量:protected InvocationHandler h;,里面会调用h的invoke(),
        try {
            FileOutputStream fos = new FileOutputStream("D:\\$Proxy2.class");
            fos.write(proxyClassFile);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

interface Calculator {
    int add(int a,int b);
}

class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
