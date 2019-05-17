package org.rmi.core.service.impl;

import org.rmi.core.service.HelloService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// 编写 RMI 接口的实现类

/**
 * 实现HelloService 是一件非常简单的事情，但需要注意的是，我们必须让实现类继承 java.rmi.server.UnicastRemoteObject 类，
 *
 * 此外，必须提供一个构造器，并且构造器必须抛出 java.rmi.RemoteException 异常。
 *
 * 我们既然使用 JVM 提供的这套 RMI 框架，那么就必须按照这个要求来实现，
 * 否则是无法成功发布 RMI 服务的，一句话：我们得按规矩出牌！
 *
 * 为了满足 RMI 框架的要求，我们确实做了很多额外的工作（继承了 UnicastRemoteObject 类，抛出了 RemoteException 异常），
 *
 * 但这些工作阻止不了我们发布 RMI 服务的决心！我们可以通过 JVM 提供的 JNDI（Java Naming and Directory Interface，Java 命名与目录接口）这个 API 轻松发布 RMI 服务。
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    private static final long serialVersionUID = 1L;

    public HelloServiceImpl() throws RemoteException { }

    public String sayHello(String name) throws RemoteException {
        return String.format("Hello %s", name);
    }
}
