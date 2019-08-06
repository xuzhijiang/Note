package com.springboot.mvc.domain;

import com.fasterxml.jackson.annotation.JsonView;

public class User {
    // 定义两个内部接口,将实体类User的属性 分为两组：
    // 一是简单属性组UserSimpleView，只包容id和name两个属性，
    // 另一个是,除了password属性之外的所有属性，都归于UserDetailView组。
    public interface UserSimpleView{}
    public interface UserDetailView {}

    private int id;
    private String username;
    private String password;
    private int age;
    private String gender;

    //使用@JsonView给字段加上标识，让其归属于特定的接口(即“组”）
    @JsonView({UserSimpleView.class, UserDetailView.class})
    public int getId() {
        return id;
    }

    @JsonView({UserSimpleView.class, UserDetailView.class})
    public String getUsername() {
        return username;
    }

    @JsonView({UserDetailView.class})
    public int getAge() {
        return age;
    }

    @JsonView({UserDetailView.class})
    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

