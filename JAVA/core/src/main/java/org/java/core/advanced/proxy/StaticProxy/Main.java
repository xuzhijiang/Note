package org.java.core.advanced.proxy.StaticProxy;

public class Main {
    public static void main(String[] args) {
        InterfaceA interfaceA = new ProxyImplement();
        // 代理方式调用者其实都不知道被代理对象的存在
        interfaceA.exec();
    }
}
