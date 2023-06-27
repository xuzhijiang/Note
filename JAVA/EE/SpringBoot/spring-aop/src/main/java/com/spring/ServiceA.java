package com.spring;

import org.springframework.stereotype.Service;

// 会创建ServiceA类型的对象,但是不会把它放到容器中,而只是把它的aop生成的代理对象放到容器中.
@Service
public class ServiceA implements Calculate{
    @Override
    public int div(int numA, int numB) {
        System.out.println("***********执行目标方法************");
        return numA / numB;
    }
}
