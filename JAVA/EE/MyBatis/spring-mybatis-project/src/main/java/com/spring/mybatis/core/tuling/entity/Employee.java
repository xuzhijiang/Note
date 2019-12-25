package com.spring.mybatis.core.tuling.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Employee {
    private String lastName;
    private String email;
    private String gender;
    private Integer deptId;
}
