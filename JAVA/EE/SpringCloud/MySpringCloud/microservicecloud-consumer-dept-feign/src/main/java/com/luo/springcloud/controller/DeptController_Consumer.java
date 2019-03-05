package com.luo.springcloud.controller;

import com.luo.springcloud.entities.Dept;
import com.luo.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptController_Consumer {

    @Autowired
    private DeptClientService service;

    //http://localhost/consumer/dept/add?dname=AI
    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept){
        return service.add(dept);
    }

    //http://localhost/consumer/dept/get/2
    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return service.get(id);
    }

    //  http://localhost/consumer/dept/list
    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list(){
        return service.list();
    }

}
