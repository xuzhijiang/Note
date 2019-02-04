package com.jinxuliang.spring_mvc_controller_demo.domain;

public class DemoObject {
    private Long id;

    @Override
    public String toString() {
        return "DemoObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private String name;

    public DemoObject() {
    }

    public DemoObject(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}