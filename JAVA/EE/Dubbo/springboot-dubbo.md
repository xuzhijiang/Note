<!-- MarkdownTOC -->

- [使用 SpringBoot+Dubbo 搭建一个简单分布式服务](#使用-springbootdubbo-搭建一个简单分布式服务)
    - [什么是分布式?](#什么是分布式)
    - [什么是 Duboo？](#什么是-duboo)
    - [Dubbo 架构](#dubbo-架构)
    - [什么是 RPC？](#什么是-rpc)
    - [为什么要用 Dubbo？](#为什么要用-dubbo)

<!-- /MarkdownTOC -->

# 什么是 Duboo？

Apache Dubbo |ˈdʌbəʊ| 是一款高性能、轻量级的开源Java RPC 框架，它提供了三大核心能力：`面向接口的远程方法调用`，`智能容错`和`负载均衡`，以及`服务自动注册和发现`。简单来说 Dubbo 是一个分布式服务框架，一个RPC远程服务调用方案.

她最大的特点是按照分层的方式来架构，使用这种方式可以使各个层之间解耦合（或者最大限度地松耦合）。

Dubbo 采用的是一种非常简单的模型，要么是提供方提供服务，要么是消费方消费服务，所以基于这一点可以抽象出服务提供方（Provider）和服务消费方（Consumer）两个角色。

- [官网](http://dubbo.apache.org/zh-cn)
- [GitHub](https://github.com/apache/incubator-dubbo)

![](Dubbo特性.png)

# Dubbo 架构

后面会使用 zookeeper 作为注册中心，这也是 Dubbo 官方推荐的一种方式(现在最新的已经不推荐使用zookeeper了)

![Dubbo 架构](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-9-26/46816446.jpg)

**上述节点简单说明：**

-  **Provider**   暴露服务的服务提供方
- **Consumer**  调用远程服务的服务消费方
- **Registry**  服务注册与发现的注册中心
- **Monitor**   统计服务的调用次数和调用时间的监控中心
- **Container**   服务运行容器

**调用关系说明：**
1. 服务容器负责启动，加载，运行服务提供者。
 2.    服务提供者在启动时，向注册中心注册自己提供的服务。
 3.   服务消费者在启动时，向注册中心订阅自己所需的服务。
 4.  注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
 5.  服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台服务提供者进行调用，如果调用失败，再选另一台调用。
 6.   服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

### 什么是 RPC？

RPC（Remote Procedure Call）—远程过程调用，它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。比如两个不同的服务A,B部署在两台不同的机器上，那么服务 A 如果想要调用服务 B 中的某个方法该怎么办呢？使用 HTTP请求 当然可以，但是可能会比较慢而且一些优化做的并不好。 RPC 的出现就是为了解决这个问题。

### 为什么要用 Dubbo？

如果你要开发分布式程序，你也可以直接基于 HTTP 接口进行通信，但是为什么要用 Dubbo呢？

我觉得主要可以从 Dubbo 提供的下面四点特性来说为什么要用 Dubbo：

1. **负载均衡**——同一个服务部署在不同的机器时该调用那一台机器上的服务
2. **服务调用链路生成**——服务之间互相是如何调用的
3. **服务访问压力以及时长统计**——当前系统的压力主要在哪里，如何来扩容和优化
4. **服务降级**——某个服务挂掉之后调用备用服务

## Dubbo Admin 管理控制台

管理控制台包含：路由规则，动态配置，服务降级，访问控制，权重调整，负载均衡，等管理功能。

GitHub：https://github.com/apache/dubbo-admin

### Frontend Build Setup

```shell
# install dependencies
# 配置淘宝镜像加速
npm install --registry=https://registry.npm.taobao.org

# serve with hot reload at localhost:8081
# 用热加载在localhost:8081进行服务
npm run dev

# build for production with minification
# 为缩小生产而建造
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

### 生产环境配置

1. 下载代码: git clone https://github.com/apache/dubbo-admin.git
2. 在 dubbo-admin-server/src/main/resources/application.properties中指定zookeeper注册中心地址
3. 构建: `mvn clean package`
4. 启动: `mvn --projects dubbo-admin-server spring-boot:run` or `cd dubbo-admin-distribution/target; java -jar dubbo-admin-0.1.jar`
5. 访问 http://localhost:8080

