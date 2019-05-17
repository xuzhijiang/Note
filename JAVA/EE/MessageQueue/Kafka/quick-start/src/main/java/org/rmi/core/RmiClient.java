package org.rmi.core;

import org.rmi.core.service.HelloService;

import java.rmi.Naming;

// 调用 RMI 服务
public class RmiClient {

    /**
     * 同样我们也使用一个 main() 方法来调用 RMI 服务，相比发布而言，调用会更加简单，我们只需要知道两个东西：
     * 1. RMI 请求路径、
     * 2. RMI 接口（一定不需要 RMI 实现类，否则就是本地调用了）
     */
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost:1099/org.rmi.core.service.impl.HelloServiceImpl";
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayHello("xzj");
        System.out.println(result);
    }

}
