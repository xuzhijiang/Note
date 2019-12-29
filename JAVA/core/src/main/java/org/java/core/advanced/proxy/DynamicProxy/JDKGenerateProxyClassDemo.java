package org.java.core.advanced.proxy.DynamicProxy;

import sun.misc.ProxyGenerator;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * JDK在运行时生成的字节码类。
 * JDK生成Proxy类的字节码是通过ProxyGenerator.generateProxyClass生成的，这是一个小工具生成字节码，并输出到文件
 */
public class JDKGenerateProxyClassDemo {
    public static void main(String[] args) throws IOException {
        // 这个接口正是我们传递进去的被代理对象的接口列表,生成的代理类是Proxy子类,
        byte[] proxyBytes = ProxyGenerator.generateProxyClass("Proxy0", CalculatorImpl.class.getInterfaces());
        ByteArrayInputStream bais = new ByteArrayInputStream(proxyBytes);
        FileOutputStream fos = new FileOutputStream("d:/$Proxy0.class");

        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = bais.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        fos.close();
        bais.close();
    }
}
