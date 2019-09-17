package com.funtl.hello.spring.cloud.web.admin.feign.service;

import com.funtl.hello.spring.cloud.web.admin.feign.service.hystrix.AdminServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建 Feign 接口(默认实现了负载均衡)
 *
 * 通过 @FeignClient("服务名") 注解来指定调用哪个服务
 *
 * 使用熔断器，注意需要添加fallback，需要在application.yml中打开feign自带熔断器开关
 */
@FeignClient(value = "hello-spring-cloud-service-admin", fallback = AdminServiceHystrix.class)
public interface AdminService {

    @RequestMapping(value = "hi", method = RequestMethod.GET)
    // 这个@RequestParam(value = "message")一定要加上,确保controller中的参数和这里的message能匹配上
    String sayHi(@RequestParam(value = "message") String message);
}