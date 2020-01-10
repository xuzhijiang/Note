package com.springframework.datajpa.core.domain;

import javax.persistence.*;

// 使用标准的JPA注解在bean中进行映射(hibernate就是JPA的具体实现.)

// 类和字段上的注释是由JPA规范所定义,用于指明类名和属性名如何与表名和字段名进行配对。
@Entity(name="teacher") // 标明是一个实体类
@Table(name = "teacher")// 将一个类与数据库中的表相关联
public class Teacher {

    @Id// 标明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 主键策略，递增
    private int id;

    // 定义属性和列的映射关系,
    // name表示数据库中的column name与该字段的名字想对应
    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
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
