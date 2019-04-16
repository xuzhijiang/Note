# Zuul路由网关

## 概述

Zuul包含了对请求的路由和过滤两个主要的功能，其中`路由的功能是负责将外部请求转发到具体的微服务实例上`，是实现外部访问统一入口的基础而过滤功能是负责对请求的处理过程进行干预，是实现请求校验，服务聚合等功能的基础，Zuul和Eureka进行整合，将Zuul自身注册近Eureka服务治理的应用，同时从Eureka中获取其他微服务的消息，也就是以后的访问服务都是通过Zuul跳转后获得， **注意的是Zuul服务最终还是会注册到Eureka中**

### URL映射


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
