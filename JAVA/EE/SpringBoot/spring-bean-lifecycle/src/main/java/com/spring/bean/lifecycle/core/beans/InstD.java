package com.spring.bean.lifecycle.core.beans;

public class InstD {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InstD{" +
                "name='" + name + '\'' +
                '}';
    }

    public void init() {
        System.out.println(getClass() + "\t init");
    }

    public void destroy() {
        System.out.println(getClass() + "\t destroy");
    }

}
