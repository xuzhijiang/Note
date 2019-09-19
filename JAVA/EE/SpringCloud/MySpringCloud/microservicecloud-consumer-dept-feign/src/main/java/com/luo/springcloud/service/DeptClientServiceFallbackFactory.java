package com.luo.springcloud.service;

import com.luo.springcloud.entities.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 服务降级
 *
 * 服务降级处理是在客户端完成的，与服务端没有关系，在前面的服务熔断中，
 * 我们发现每一个业务方法都要写一个processHystrix_方法，这样就造成了很大耦合，
 * 根据Spring的学习，我们可将processHystrix_改写一个异常通知。
 *
 * ### 测试服务降级
 *
 * 启动三个Eureka集群，再启动microservicecloud-provider-dept-8001，
 * 再启动microservicecloud-consumer-dept-feign，正常访问http://localhost/consumer/dept/get/1，
 * 此时是正常返回的.
 * 故意关停microservicecloud-provider-dept-8001，客户端自己调用提示
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>{

    @Override
    public DeptClientService create(Throwable arg0) {

        return new DeptClientService() {

            @Override
            public List<Dept> list() {
                return null;
            }

            @Override
            public Dept get(long id) {
                return new Dept().setDeptno(id)
                        .setDname("该ID："+id+"没有对应的信息，Consumer客户端提供的降级信息，此服务暂停使用")
                        .setDb_source("no this database in Mysql");
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };

    }

}
