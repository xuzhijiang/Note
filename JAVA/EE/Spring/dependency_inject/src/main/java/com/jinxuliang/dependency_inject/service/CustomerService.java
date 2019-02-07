package com.jinxuliang.dependency_inject.service;

import com.jinxuliang.dependency_inject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository repository;

    //使用构造方法进行注入
    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "CustomerService{" +
                "repository=" + repository +
                '}';
    }
}