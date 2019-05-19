package org.zookeeper.core;

import org.rmi.core.service.HelloService;

// 调用服务

// 通过调用 ServiceConsumer 的 lookup() 方法来查找 RMI 远程服务对象。
// 我们使用一个“死循环”来模拟每隔 3 秒钟调用一次远程方法。
/**
 *  使用方法根据以下步骤验证 RMI 服务的高可用性：
 *
 * 	1. 运行两个 Server 程序，一定要确保 port 是不同的。
 *
 * 	2. 运行一个 Client 程序。
 *
 * 	3. 停止其中一个 Server 程序，并观察 Client 控制台的变化（停止一个 Server 不会导致 Client 端调用失败）。
 *
 * 	4. 重新启动刚才关闭的 Server 程序，继续观察 Client 控制台变化（新启动的 Server 会加入候选）。
 *
 * 	5. 先后停止所有的 Server 程序，还是观察 Client 控制台变化（Client 会重试连接，多次连接失败后，自动关闭）。
 */
public class Client {

    public static void main(String[] args) throws Exception {
        ServiceConsumer consumer = new ServiceConsumer();

        while (true ) {
            /**
             * 通过调用 ServiceConsumer 的 lookup() 方法来查找 RMI 远程服务对象。
             * 我们使用一个“死循环”来模拟每隔 3 秒钟调用一次远程方法。
             */
            HelloService helloService = consumer .lookup();
            String result = helloService .sayHello("Jack");
            System.out.println(result );
            Thread.sleep(3000);
        }
    }
}