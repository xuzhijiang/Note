package com.springboot.core.bean;

public class EnvironmentBean {

    private String name;

    @Override
    public String toString() {
        return "EnvironmentBean{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
