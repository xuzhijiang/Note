package com.redis.core.springboot.redistemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private int age;
    private String name;
    public boolean newlyCreated;

    public User(Integer id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
}
