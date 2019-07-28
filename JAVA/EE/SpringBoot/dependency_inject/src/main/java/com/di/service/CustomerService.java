package com.di.service;

import com.di.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository repository;

    // 使用构造方法进行注入
    @Autowired // @Autowired注释用于字段，构造函数和方法
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