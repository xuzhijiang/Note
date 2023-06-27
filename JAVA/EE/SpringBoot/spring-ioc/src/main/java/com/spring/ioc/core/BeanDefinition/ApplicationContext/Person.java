package com.spring.ioc.core.BeanDefinition.ApplicationContext;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String username;

    private Integer age;

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }

    public Person(String username) {
        this.username = username;
        System.out.println("person有参构造器---------" + username);
    }

    // 默认SpringIoC会调用Person的无参构造器
    public Person() {
        System.out.println("person无参构造器------");
    }

    public Person(Integer age) {
        this.age = age;
        System.out.println("person有参构造器---------" + age);
    }
}
