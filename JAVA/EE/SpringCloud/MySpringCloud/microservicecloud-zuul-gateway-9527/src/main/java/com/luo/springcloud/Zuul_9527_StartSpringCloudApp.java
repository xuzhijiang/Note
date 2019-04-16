package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *
 *
 * Zuul路由访问映射:
 *
 * 1. 修改host文件: 127.0.0.1 myzuul.com,修改路由项目的yml文件,添加 mydept.path: /mydept/**
 * 2. yml在没有使用ignored-services的情况下，
 * 可以使用http://myzuul.com:9527/openPrefix/my-microservicecloud-dept-8001/dept/list (或者使用url映射:
 * http://myzuul.com:9527/openPrefix/mydept/dept/list)访问我们的接口，
 * 不过这样就暴露我们的微服务名称，需要做安全加固，就用到了路由访问映射.
 * 3. 安全加固: 在yml中使用ignored-services，忽略掉my-microservicecloud-dept-8001.(安全加固后，访问这个http://myzuul.com:9527/openPrefix/my-microservicecloud-dept-8001/dept/list就无效了)
 * 4. 启动三个集群
 * 5. 启动一个服务提供类microservicecloud-provider-dept-8001，
 * 6. 启动一个路由microservicecloud-zuul-gateway-9527
 * 7. 启动consumer-dept-80
 * 8. 访问连接：http://myzuul.com:9527/openPrefix/mydept/dept/list
 */
@SpringBootApplication
@EnableZuulProxy// (开启zuul)
public class Zuul_9527_StartSpringCloudApp {

	public static void main(String[] args) {
		SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
	}


}
