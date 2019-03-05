package com.luo.springcloud;

import com.luo.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 测试Ribbon
 *
 * 启动7001,7002,7003三个服务注册中心，再启动8001服务提供者，再启动80客户端，
 * 使用http://localhost/consumer/dept/list，在DeptController_Consumer使用的
 * 是http://MICROSERVICECLOUD-DEPT服务名称来调用服务的接口，
 * 相比之前的http://localhost:8001，Ribbon和Eureka整合后，
 * Consumer可以直接通过服务名称来调用服务，而不再关心地址和端口号。
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "MICROSERVICECLOUD-DEPT", configuration = MySelfRule.class)
public class DeptConsumer80_App {

    // 特此说明

    // RibbonClient注解中的MySelfRule类使我们自定义负载均衡算法的类，
    // 但是，这个自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下，
    // 否则我们这个自定义的配置类会被所有的Ribbon客户端所共享，也就说，
    // 达不到我们特殊化定制的目的。举例说明，自定义配置类不能放在项目主启动类所有的包以及子包下，
    // 因为主启动类使用注解@SpringBootApplication，这个注解点进去使用@ComponentScan注解
    public static void main(String[] args){
        SpringApplication.run(DeptConsumer80_App.class, args);
    }

}
