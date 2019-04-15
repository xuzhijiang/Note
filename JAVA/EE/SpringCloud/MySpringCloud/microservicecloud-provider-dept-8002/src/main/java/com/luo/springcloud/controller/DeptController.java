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
        List<String> list = client.getServices();
        System.out.println("8002************ size: " + list.size());
        System.out.println("8002************ list: " + list.toString());

        List<ServiceInstance> srvList = client.getInstances("my-microservicecloud-dept-8001");
        System.out.println("8002************ svrList size: " + srvList.size());
        System.out.println("8002************ svrList" + srvList);
        System.out.println("8002************ ***");
        for(ServiceInstance element : srvList){
            System.out.println(element.getServiceId() + "\t" + element.getHost()
        + "\t" + element.getPort() + "\t" + element.getUri());
        }
        return client;
    }
}
