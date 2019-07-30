package com.jinxuliang.springbootjpademo.model;

import javax.persistence.*;

// 类和字段上的注释是由JPA规范所定义的
// 用于指明类名和属性名如何与表名和字段名进行配对。
@Entity(name="teacher") // 标明是一个实体类
@Table(name = "teacher")// 定义映射的表
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 主键策略，递增
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
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
