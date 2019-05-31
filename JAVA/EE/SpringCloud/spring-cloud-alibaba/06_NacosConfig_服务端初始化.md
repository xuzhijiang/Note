# Nacos Config 服务端初始化

## 分布式配置中心

在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。

## Nacos Config

Nacos 提供用于存储配置和其他元数据的 key/value 存储，为分布式系统中的外部化配置提供服务器端和客户端支持。使用 Spring Cloud Alibaba Nacos Config，您可以在 Nacos Server 集中管理你 Spring Cloud 应用的外部属性配置。

Spring Cloud Alibaba Nacos Config 是 Spring Cloud Config Server 和 Client 的替代方案.

## 创建配置文件

需要在 Nacos Server 中创建配置文件，我们依然采用 YAML 的方式部署配置文件，操作流程如下：

1. 浏览器打开 http://localhost:8848/nacos ，访问 Nacos Server
2. 点击配置列表,然后点击右侧的加号(+)
3. Data ID: nacos-provider-config.yaml
4. Group: DEFAULT_GROUP
5. 描述: 测试服务提供者配置
6. 配置格式: yaml
7. 配置内容:

```yml
spring:
  application:
    name: nacos-provider
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848 # Nacos Server 启动监听的ip地址和端口

server:
  port: 8081

management:
  endpoints:
    web:
      exposure:
        include: "*"                
```

>注意：Data ID 的默认扩展名为 .properties ，希望使用 YAML 配置，此处必须指明是 .yaml
 
发布成功后在 “配置列表” 一栏即可看到刚才创建的配置项
