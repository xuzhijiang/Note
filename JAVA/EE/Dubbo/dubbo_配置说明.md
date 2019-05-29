## io.dubbo.springboot下的spring-boot-starter-dubbo配置

```shell
server.port=8081

## Dubbo 服务提供者配置
# 应用名称
spring.dubbo.application.name=consumer
# 注册中心地址
spring.dubbo.registry.address=zookeeper://ip:2181
# 协议名称
spring.dubbo.protocol.name=dubbo
# 协议端口(注意不写会有问题)
spring.dubbo.protocol.port=20880
# 扫描服务实现的包，否则注册不到服务
spring.dubbo.scan=com.funtl.hello.dubbo.service.user.consumer.controller

user.service.version=1.0.0
```