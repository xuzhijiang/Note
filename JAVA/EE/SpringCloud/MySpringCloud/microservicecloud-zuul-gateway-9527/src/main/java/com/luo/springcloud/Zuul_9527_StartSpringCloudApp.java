package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 1. 修改host文件: 127.0.0.1 myzuul.com
 *
 * 2. yml在没有使用ignored-services的情况下，
 * 可以使用http://myzuul.com:9527/openPrefix/my-microservicecloud-provider-dept/dept/list访问.
 * 也可以使用http://myzuul.com:9527/openPrefix/feign-consumer-dept/dept/list访问.
 * 不过这样就暴露我们的微服务名称，需要做安全加固，就用到了路由访问映射.
 *
 * 3. 安全加固: 在yml中使用ignored-services，忽略掉my-microservicecloud-provider-dept
 * (安全加固后，访问这个http://myzuul.com:9527/openPrefix/my-microservicecloud-provider-dept/dept/list就无效了)
 *
 * 4. 正规的访问方式:http://myzuul.com:9527/openPrefix/mydept/dept/list
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class Zuul_9527_StartSpringCloudApp {
	public static void main(String[] args) {
		SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
	}
}
