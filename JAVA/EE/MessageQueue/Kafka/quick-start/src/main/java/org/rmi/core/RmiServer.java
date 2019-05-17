package org.rmi.core;

import org.rmi.core.service.impl.HelloServiceImpl;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 发布一个 RMI 服务，我们只需做三件事情：
 *
 * 	1. 定义一个 RMI 接口
 *
 * 	2. 编写 RMI 接口的实现类
 *
 * 	3. 通过 JNDI(Java Naming and Directory Interface) 发布 RMI 服务
 */


// 通过 JNDI 发布 RMI 服务

// 发布 RMI 服务，我们需要告诉 JNDI 三个基本信息：
// 1. 域名或 IP 地址（host）、
// 2. 端口号（port）、
// 3. 服务名（service），
// 它们构成了 RMI 协议的 URL（或称为“RMI 地址”）：

//rmi://<host>:<port>/<service>

//如果我们是在本地发布 RMI 服务，那么 host 就是“localhost”。
// 此外，RMI 默认的 port 是“1099”，我们也可以自行设置 port 的值（只要不与其它端口冲突即可）。
// service 实际上是一个基于同一 host 与 port 下唯一的服务名，我们不妨使用 Java 完全类名来表示吧，这样也比较容易保证 RMI 地址的唯一性。

//对于我们的示例而言，RMI 地址为：
// rmi://localhost:1099/org.rmi.core.service.impl.HelloServiceImpl

//我们只需简单提供一个 main() 方法就能发布 RMI 服务，就像下面这样：
public class RmiServer {

    /**
     * 需要注意的是，我们通过 LocateRegistry.createRegistry() 方法在 JNDI 中创建一个注册表，
     * 只需提供一个 RMI 端口号即可。此外，通过 Naming.rebind() 方法绑定 RMI 地址与 RMI 服务实现类，
     * 这里使用了 rebind() 方法，它相当于先后调用 Naming 的 unbind() 与 bind() 方法，
     * 只是使用 rebind() 方法来得更加痛快而已，所以我们选择了它。
     *
     * 运行这个 main() 方法，RMI 服务就会自动发布，剩下要做的就是写一个 RMI 客户端来调用已发布的 RMI 服务。
     */
    public static void main(String[] args) throws Exception {
        int port = 1099;
        String url = "rmi://localhost:1099/org.rmi.core.service.impl.HelloServiceImpl";
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new HelloServiceImpl());
    }

}
