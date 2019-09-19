# 微服务架构解决方案

- 第一套微服务架构解决方案: Spring Boot + Spring Cloud Netflix (采用基于Netflix eureka-尤里卡的服务注册与发现)
- 第二套微服务架构解决方案: Spring Boot + Dubbo(RPC框架) + Zookeeper(Dubbo的服务治理是采用基于Zookeeper的服务注册与发现)

>微服务架构围绕4大问题:

1. 客户端如何访问这么多服务: 通过API 网关
2. 服务与服务之间如何通信: 分为同步通信和异步通信,其中同步通信方案有http方式(apache http client),rpc(dubbo-只支持java, Apache Thrift, gRPC等),异步通信方式有kafka,RabbitMQ,RockekMQ.
3. 这么多服务如何管理(管理的其实就是管理服务的地址),如何服务治理(知道某个服务何时上线,何时下线,何时宕机,每个时刻的状态,以及知道某个服务的地址,端口等.),这时就引入服务注册与发现.这里的服务注册与发现分为基于客户端的服务注册(Apache Zookeeper)与发现和基于服务端的服务注册与发现(Netflix Eureka).
4. 服务挂了怎么办: 重试机制,服务熔断,服务降级,服务限流.

>分布式架构设计(微服务架构设计)的一切来源是: 网络是不可靠的.这是一切设计的根源.

    微服务最后要达到的效果: 高可用,高性能,高并发.

# Double和 SpringCloud对比

最大的区别在于SpringCloud抛弃了Dubbo的RPC通信，采用的是HTTP的REST方式，如下：

|        | Dubbo         | SpringCloud                  |
| ------ | ------------- | ---------------------------- |
| 服务注册中心 | Zookeeper     | Spring Cloud Netflix Eureka   |
| 服务调用方式 | RPC           | Rest API                     |
| 服务监控   | Dubbo-monitor | Spring Boot Admin            |
| 断路器    | 不完善           | Spring Cloud Netflix Hystrix |
| 服务网关   | 无             | Spring Cloud Netflix Zuul    |
| 分布式配置  | 无             | Spring Cloud Config          |
| 服务跟踪   | 无             | Spring Cloud Sleuth          |
| 消息总线   | 无             | Spring Cloud Bus             |
| 数据流    | 无             | Spring Cloud Stream          |
| 批量任务   | 无             | Spring Cloud Task            |

SpringCloud组件多,功能完备,Dubbo组件少,功能相对少.

# SpringBoot和SpringCloud

- SpringBoot用于开发单个个体微服务
- SpringCloud将SpringBoot开发的单体微服务整合并管理，为各个微服务之间提供配置管理，其中主要有：服务发现（Eureka），断路器（Hystrix），智能路由（Zuul），客户端负载均衡（Ribbon）等.
- SpringBoot可以离开SpringCloud独立的开发项目，但是SpringCloud离不开SpringBoot，属于依赖关系
- SpringBoot专注于快速，方便的开发单个微服务个体，SpringCloud关注全局的服务治理框架