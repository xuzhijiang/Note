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

    /**
     * http://localhost:8001/dept/list以JSON的方式返回数据
     * @return
     */
    @RequestMapping(value = "dept/list", method = RequestMethod.GET)
    public List<Dept> list(){
        return service.list();
    }

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery(){
        // 对于注册到Eureka里面的微服务，可以通过服务发现来获取该服务的信息
        List<String> list = client.getServices();//得到Eureka中所有的微服务
        System.out.println("************" + list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for(ServiceInstance element : srvList){
            System.out.println(element.getServiceId() + "\t" + element.getHost()
        + "\t" + element.getPort() + "\t" + element.getUri());
        }
        return client;
    }
}
