# Ribbon负载均衡

## Ribbon概述

Spring Cloude Ribbon是基于Netfilx Ribbon实现的一套客户端 负载均衡的工具，简单说，Ribbon是Netfilix发布的开源项目，主要功能就是提供 **客户端的软件负载均衡算法**，将Netfilix的中间层服务连接在一起，Ribbon客户端组件提供了一系列完善的配置项如连接超时，重试等，简单说，就是在配置文件中列出Load Balance后面的所有机器，Ribbon会自动的帮助你基于某种算法规则（简单轮询，随机连接等）去连接这些机器，也可以使用Ribbon自定义负载均衡算法。LB，即负载均衡，在微服务或者分布式集群中常用的一种应用。负载均衡就是将用户的请求平摊的分配到多个服务上，从而达到HA，常见的负载均衡软件有Nginx，LVS，硬件F5等

## Ribbon核心组件IRule

#### Ribbon负载均衡算法

Ribbon默认提供的是轮询的负载均衡算法，完整了还有如下

| RoundRobinRule            | 轮询                                       |
| ------------------------- | ---------------------------------------- |
| RandomRule                | 随机                                       |
| AvaliabilityFilteringRule | 会先过滤由于多次访问故障而处于断路器跳闸的状态的服务和并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略 |
| WeightedResponseTimeRule  | 根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大           |
| RetryRule                 | 先按照RoundRobinRule策略获取服务，如果获取服务失败会在指定时间内重试 |
| BestAvailableRule         | 会先过滤掉由于多次访问故障二处于断路器跳闸状态的服务，然后选择一个并发量最小的服务 |
| ZoneAvoidanceRule         | 默认规则，复合判断server所在的区域的性能和server的可用性选择服务器  |

```properties
<!-- 我们也可以在application.properties配置文件中加入： -->
<!-- ###### Ribbon -->
ribbon.ReadTimeout=60000
<!-- 这个是设置负载均衡的超时时间的。 -->
```