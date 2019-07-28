package com.springboot.beanscope.bean;

public class EmployeeConsumer {

    public EmployeeConsumer(Employee e) {
        System.out.println("EmployeeConsumer e: " + e.hashCode());
    }
}
