package org.rmi.core.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

// 定义一个 RMI 接口

/**
 * RMI 接口实际上还是一个普通的 Java 接口，只是 RMI 接口必须继承 java.rmi.Remote，
 * 此外，每个 RMI 接口的方法必须声明抛出一个 java.rmi.RemoteException 异常
 *
 * 继承了 Remote 接口，实际上是让 JVM 得知该接口是需要用于远程调用的，
 * 抛出了 RemoteException 是为了让调用 RMI 服务的程序捕获这个异常。
 * 毕竟远程调用过程中，什么奇怪的事情都会发生（比如：断网）。
 *
 * 需要说明的是，RemoteException 是一个“受检异常”，在调用的时候必须使用 try...catch... 自行处理。
 */
public interface HelloService extends Remote {
    String sayHello(String name) throws RemoteException;
}
