package org.zookeeper.core;

import org.rmi.core.service.HelloService;
import org.rmi.core.service.impl.HelloServiceImpl;

// 发布服务
//
//我们需要调用 ServiceProvider 的 publish() 方法来发布 RMI 服务，发布成功后也会自动在 ZooKeeper 中注册 RMI 地址。
public class Server {

    // 注意：在运行 Server 类的 main() 方法时，一定要使用命令行参数来指定 host 与 port,例如:
    // java Server localhost 1099
    // java Server localhost 2099
    // 以上两条 Java 命令可在本地运行两个 Server 程序，当然也可以同时运行更多的 Server 程序，只要 port 不同就行。
    public static void main(String[] args) throws Exception {
        if (args .length != 2) {
            System. err.println("please using command: java Server <rmi_host> <rmi_port>");
            System. exit(-1);
        }

        String host = args [0];
        int port = Integer.parseInt (args [1]);

        ServiceProvider provider = new ServiceProvider();

        HelloService helloService = new HelloServiceImpl();
        provider.publish(helloService , host , port );

        Thread. sleep(Long.MAX_VALUE);
    }
}