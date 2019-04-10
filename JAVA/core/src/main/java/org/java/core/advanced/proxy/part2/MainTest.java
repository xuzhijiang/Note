package org.java.core.advanced.proxy.part2;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class MainTest {

    public static void main(String[] args){
        testJDKDynamic();

        testOutputClass();
    }

    private static void testOutputClass() {
        // 其实jdk的代理类是由ProxyGenerator.generateProxyClass()动态创建出来
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass("$Proxy2",
                new Class[]{ISubject.class}, 1);
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

    private static void testJDKDynamic() {
        // 首先传入被代理类的类类型构建代理处理器
        CustomizeHandle handle = new CustomizeHandle(ISubjectImpl.class);
        // 接着使用 Proxy 的newProxyInstance 方法动态创建代理类对象。第一个参数为类加载器，
        // 第二个参数为代理类需要实现的接口列表，最后一个则是处理器。
        ISubject subject = (ISubject) Proxy.newProxyInstance(handle.getClass().getClassLoader(),
                new Class[]{ISubject.class},
                handle);
        subject.exec();
    }
}
