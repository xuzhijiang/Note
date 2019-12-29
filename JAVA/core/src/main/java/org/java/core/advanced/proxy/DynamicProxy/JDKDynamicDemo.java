package org.java.core.advanced.proxy.DynamicProxy;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.*;

public class JDKDynamicDemo {

    @Test
    public void testJDKDynamicPrimitive() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * param1: 用来创建并加载Proxy类的ClassLoader
         * param2: 被代理对象的接口列表，这是JDK强制要求的，被代理的对象必须实现某一个接口,
         * 传的是一个Class[],因为可能有多个接口
         * @return: 返回代理Class对象,这个代理Class对象这个类也是实现了Calculator接口
         */
        Class<?> ProxyClazz = Proxy.getProxyClass(Calculator.class.getClassLoader(), new Class[] {Calculator.class});
        // 得到有参构造器 $Proxy0(InvocationHandler h),构造器里面参数的类型为InvocationHandler.
        Constructor<?> constructor = ProxyClazz.getConstructor(InvocationHandler.class);
        // 动态代理是通过 反射创建代理实例
        Calculator proxy = (Calculator) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 手动new一个目标对象(这种写法不够优雅，属于硬编码。我这次代理A对象，下次想代理B对象还要进来改invoke()方法，太差劲了)
                CalculatorImpl calculatorImpl = new CalculatorImpl();
                System.out.println("Before do.....");
                // 反射执行目标对象方法
                Object result = method.invoke(calculatorImpl, args);
                System.out.println("After do ......");
                // 返回目标对象执行结果
                return result;
            }
        });
        // 使用代理对象
        proxy.add(5, 7);

        System.out.println("代理类的name: " + proxy.getClass());
    }

    @Test
    public void testJDKDynamicImprove() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CalculatorImpl target = new CalculatorImpl();
        // 传入目标对象
        // 1. 根据它的接口生成代理对象, 2. 代理对象调用目标对象的方法
        Calculator proxy = (Calculator) getProxy(target);
        // 使用代理对象
        proxy.add(2, 5);
    }

    private Object getProxy(final Object target) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        Constructor<?> proxyClazzConstructor = proxyClazz.getConstructor(InvocationHandler.class);
        Object obj = proxyClazzConstructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before....");
                Object result = method.invoke(target, args);
                System.out.println("after....");
                return result;
            }
        });
        return obj;
    }

    @Test
    public void testJdkDynamicFinally() {
        CalculatorImpl calculatorImpl = new CalculatorImpl();
        Calculator proxy = (Calculator) getProxyFinally(calculatorImpl);
        System.out.println(proxy.add(2, 5));

        System.out.println("代理类的name: " + proxy.getClass());
    }

    /**
     * 最终的Proxy写法
     * @param target
     * @return
     */
    private Object getProxyFinally(final Object target) {
        Object obj = Proxy.newProxyInstance(target.getClass().getClassLoader(),  /*类加载器*/
                                            target.getClass().getInterfaces(), /*让代理对象和目标对象实现相同接口*/
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
        // jdk的代理类是由ProxyGenerator.generateProxyClass()动态创建出来
        // param1: 生成的类名: $Proxy2 param2: 代理类要实现的接口
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass("$Proxy2", new Class[]{Calculator.class}, 1);

        // 将 proxyClassFile 输出到文件并进行反编译的话就可以的到代理类(可以拖到idea反编译)

        // 反编译可以看到代理类继承了 java.lang.reflect.Proxy 类，并实现了 Calculator 接口，
        // 由此也可以看到 JDK 动态代理为什么需要实现接口，已经继承了 Proxy是不能再继承其余类了。

        // 其中java.lang.reflect.Proxy中有一个成员变量:protected InvocationHandler h; 里面会调用h的invoke(),
        // 动态代理其中实现了Calculator接口 的add方法，此方法里面通过 InvocationHandler 的 invoke() 调用目标对象add方法.
        try {
            FileOutputStream fos = new FileOutputStream("d" + File.pathSeparator + "$Proxy2.class");
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
        System.out.println("add....");
        return a + b;
    }
}
