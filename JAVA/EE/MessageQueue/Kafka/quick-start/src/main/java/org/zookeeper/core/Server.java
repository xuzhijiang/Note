package org.zookeeper.core;

import org.rmi.core.service.HelloService;
import org.rmi.core.service.impl.HelloServiceImpl;

// 发布服务

/**
 * 我们先需要使用 ZooKeeper 的客户端工具创建一个持久性 ZNode，名为“/registry”，
 * 该节点是不存放任何数据的，可使用如下命令：create /registry null
 *
 * 我们尝试使用 ZooKeeper 实现了一个简单的RMI服务高可用性解决方案，
 * 通过ZooKeeper 注册所有服务提供者发布的 RMI 服务，让服务消费者监听 ZooKeeper 的 Znode，
 * 从而获取当前可用的 RMI 服务。此方案局限于 RMI 服务，对于任何形式的服务（比如：WebService），也提供了一定参考。
 *
 * 如果再配合 ZooKeeper 自身的集群，那才是一个相对完美的解决方案，对于 ZooKeeper 的集群，请读者自行实践。
 */
public class Server {

    // 注意：在运行 Server 类的 main() 方法时，一定要使用命令行参数来指定 host 与 port,例如:
    // java Server localhost 1099
    // java Server localhost 2099
    // 以上两条 Java 命令可在本地运行两个 Server 程序

    // 也可以两次运行这个类，只不过要修改port

    // 当然也可以同时运行更多的 Server 程序，只要 port 不同就行。
    public static void main(String[] args) throws Exception {
//        if (args .length != 2) {
//            System. err.println("please using command: java Server <rmi_host> <rmi_port>");
//            System. exit(-1);
//        }

//        String host = args [0];
//        int port = Integer.parseInt (args [1]);

        String host = "localhost";
        int port = 1099;
//        int port = 2099;

        ServiceProvider provider = new ServiceProvider();

        HelloService helloService = new HelloServiceImpl();
        //我们需要调用 ServiceProvider 的 publish() 方法来发布 RMI 服务，
        // 发布成功后也会自动在 ZooKeeper 中注册 RMI 地址。
        provider.publish(helloService , host , port );

        Thread.sleep(Long.MAX_VALUE);
    }
}