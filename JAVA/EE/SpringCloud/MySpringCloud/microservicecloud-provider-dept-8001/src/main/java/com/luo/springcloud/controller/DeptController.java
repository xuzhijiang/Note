package com.luo.springcloud.controller;

import com.luo.springcloud.entities.Dept;
import com.luo.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService service;

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept){
        return service.add(dept);
    }

    @RequestMapping(value = "dept/get/{id}",method=RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id){
        return service.get(id);
    }

    @RequestMapping(value = "dept/list", method = RequestMethod.GET)
    public List<Dept> list(){
        return service.list();
    }

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery(){
        // 对于注册到Eureka里面的微服务，可以通过服务发现(DiscoveryClient)来获取该服务的信息
        List<String> list = client.getServices();//得到在Eureka中注册的所有的微服务
        System.out.println("8001************ size: " + list.size());
        System.out.println("8001************ list: " + list.toString());

        // DiscoveryClient.getInstances(serviceId);// serviceId为yml中的spring.application.name
        List<ServiceInstance> srvList = client.getInstances("my-microservicecloud-dept-8001");
        System.out.println("8001************ svrList size: " + srvList.size());
        System.out.println("8001************ svrList" + srvList);
        System.out.println("8001************ ***");
        for(ServiceInstance element : srvList){
            System.out.println(element.getServiceId() + "\t" + element.getHost()
        + "\t" + element.getPort() + "\t" + element.getUri());
        }
        return client;
    }

}
