package com.springboot.beanscope.bean;

public class TestSingletonBean {

    public TestSingletonBean(String scope) {
        System.out.println("Bean created!! scope: " + scope);
    }
}
