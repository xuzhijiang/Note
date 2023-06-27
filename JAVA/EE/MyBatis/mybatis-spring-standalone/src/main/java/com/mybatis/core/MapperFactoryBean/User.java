package com.mybatis.core.MapperFactoryBean;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
// 一定要添加这个无参构造器,因为好多框架就是依赖这个无参构造器工作的,如果不添加框架可能就报错
@NoArgsConstructor
public class User {
    private Integer id;
    private String lastName;
    private String email;
    private String gender;
}