package org.java.core.advanced.proxy.StaticProxy;

// 代理类 ProxyImplement 也实现了 InterfaceA
// 这种叫静态代理
public class ProxyImplement implements InterfaceA {

    InterfaceA interfaceA;

    public ProxyImplement(){
        interfaceA = new RealImplement();
    }

    @Override
    public void exec() {
        System.out.println("do sth before...");
        interfaceA.exec();
        System.out.println("do sth end...");
    }
}
