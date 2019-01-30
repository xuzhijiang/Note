package com.jinxuliang.springbootjpademo.model;

import javax.persistence.*;

// 标明是一个实体类
// 类和字段上的注释是由JPA规范 所定义的，用于指明类名和属性
// 名如何与表名和字段名进行配对。
@Entity(name="user")
// 定义映射的表
@Table(name = "user")
public class User {
    // 标明主键
    @Id
    // 主键策略，递增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 定义属性和表的映射关系
    @Column(name = "name")
    private String name;

    private String gender;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
