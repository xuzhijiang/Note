package com.funtl.hello.spring.cloud.alibaba.nacos.consumer.feign.service;

import com.funtl.hello.spring.cloud.alibaba.nacos.consumer.feign.service.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign 是一个声明式的伪 Http 客户端。使用 Feign，只需要创建一个接口并注解。
 * Feign 默认集成了 Ribbon，Nacos 也很好的兼容了 Feign，默认实现了负载均衡的效果
 *
 * 测试熔断器: 关闭服务提供者，再次请求 http://localhost:9092/echo/hi
 */
// 通过 @FeignClient("服务名") 注解来指定调用哪个服务。代码如下：
@FeignClient(value = "nacos-provider", fallback = EchoServiceFallback.class)
// 在 Service 中增加 fallback 指定类,实现熔断器的功能
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}
