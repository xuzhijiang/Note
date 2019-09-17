package com.funtl.hello.spring.cloud.web.admin.ribbon.controller;

import com.funtl.hello.spring.cloud.web.admin.ribbon.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试访问：在浏览器上多次访问 http://localhost:8764/hi?message=HelloRibbon
 *
 * 浏览器交替显示.
 *
 * 请求成功则表示我们已经成功实现了负载均衡功能来访问不同端口的实例
 * 相当于我们请求的是8764这个服务实例,然后这个服务实例通过ribbon+rest，
 *  把请求调度到了hello-spring-cloud-service-admin服务实例上，因为有2个
 *  hello-spring-cloud-service-admin服务实例，所以会根据ribbon算法选择一个。
 *
 * 此时的架构(classpath:ribbon-architecture.png)：
 *
 * 1. 一个服务注册中心，Eureka Server，端口号为：8761
 * 2. service-admin 工程运行了两个实例，端口号分别为：8762，8763
 * 3. web-admin-ribbon 工程端口号为：8764
 * 4. web-admin-ribbon 通过 RestTemplate 调用 service-admin 接口时因为启用了
 * 负载均衡功能故会轮流调用它的 8762 和 8763 端口
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String sayHi(@RequestParam(value = "message")String message){
        return adminService.sayHi(message);
    }

}
