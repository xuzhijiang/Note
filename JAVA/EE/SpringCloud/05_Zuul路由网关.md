# 使用路由网关统一访问接口

## 概述

在微服务架构中，需要几个基础的服务治理组件，包括服务注册与发现、服务消费、负载均衡、熔断器、智能路由、配置管理等，由这几个基础组件相互协作，共同组建了一个简单的微服务系统。一个简单的微服务系统如下图：

![](简单的微服务系统.png)

在 Spring Cloud 微服务系统中，一种常见的负载均衡方式是，客户端的请求首先经过负载均衡（Zuul、Ngnix），再到达服务网关（Zuul 集群），然后再到具体的服。服务统一注册到高可用的服务注册中心集群，服务的所有的配置文件由配置服务管理，配置服务的配置文件放在 GIT 仓库，方便开发人员随时改配置。

## Zuul(Zuul路由网关)

>Zuul 的主要功能是`路由转发和过滤器`。路由功能是微服务的一部分，比如 /api/user 转发到到 User 服务，/api/shop 转发到到 Shop 服务。Zuul 默认和 Ribbon 结合实现了负载均衡的功能。

Zuul包含了对请求的`路由转发和过滤`两个主要的功能，其中`路由的功能是负责将外部请求转发到具体的微服务实例上`，是实现外部访问统一入口的基础而过滤功能是负责对请求的处理过程进行干预，是实现请求校验，服务聚合等功能的基础，Zuul和Eureka进行整合，将Zuul自身注册近Eureka服务治理的应用，同时从Eureka中获取其他微服务的消息，也就是以后的访问的服务都是通过Zuul跳转后获得的， **注意的是Zuul服务最终还是会注册到Eureka中**

### URL映射(Zuul路由网关的转发功能)

zuul提供了两种配置方式，一种是通过url直接映射，另一种是利用注册到eureka server中的服务id作映射：

>url直接映射：

```properties
<!-- 所有到Zuul的中规则为：/api-a-url/**的访问都映射到http://localhost:2222/上，比如 ：http://localhost:9010/api-a-url/add?a=1&b=2，该请求会转发到：http://localhost:2222/add?a=1&b=2上。 -->

zuul.routes.api-a-url.path=/api-a-url/**
zuul.routes.api-a-url.url=http://localhost:2222/
```


>但是这么做必须得知道所有得微服务得地址，才能完成配置，这时我们可以利用注册到eureka server中的服务id作映射：

```properties
<!-- integral-server和member-server是这俩微服务系统注册到微服务中心得一个serverId，我们通过配置，访问http://localhost:9010/integual/add?a=1&b=2，该请求就会访问integral-server系统中得add服务。 -->

zuul.routes.api-integral.path=/integral/**
zuul.routes.api-integral.serviceId=integral-server

zuul.routes.api-member.path=/member/**
zuul.routes.api-member.serviceId=member-server
```

## 使用路由网关(Zuul)的服务过滤功能

Zuul 不仅仅只是路由，还有很多强大的功能，本节演示一下它的服务过滤功能，比如用在安全验证方面。
