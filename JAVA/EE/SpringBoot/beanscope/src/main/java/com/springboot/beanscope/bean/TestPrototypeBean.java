package com.springboot.beanscope.bean;

public class TestPrototypeBean {

    public TestPrototypeBean(String scope) {
        System.out.println("Bean created!! scope: " + scope);
    }
}
