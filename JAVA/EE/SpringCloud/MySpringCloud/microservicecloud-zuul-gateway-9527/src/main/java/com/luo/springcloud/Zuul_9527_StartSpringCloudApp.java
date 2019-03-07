package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *
 * 修改host文件:
 *
 * 127.0.0.1 myzuul.com
 *
 * ## Zuul路由访问映射
 *
 * 在前面的测试中我们可以使用http://myzuul.com:9527/microservicecloud-dept/dept/get/2访问我们的接口，
 * 这样就暴露我们的微服务名称，需要做安全加固，就用到了路由访问映射，
 * 修改路由项目的yml文件,添加 mydept.path: /mydept/**
 *
 * 启动三个集群，启动一个服务提供类microservicecloud-provider-dept-8001，
 * 启动一个路由microservicecloud-zuul-gateway-9527,启动consumer-dept-80
 *
 * 测试:
 *
 * 访问连接：http://myzuul.com:9527/luo/mydept/dept/get/1**
 */
@SpringBootApplication
@EnableZuulProxy
public class Zuul_9527_StartSpringCloudApp {
	public static void main(String[] args) {
		SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
	}
}
