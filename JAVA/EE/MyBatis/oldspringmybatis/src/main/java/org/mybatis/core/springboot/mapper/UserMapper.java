package org.mybatis.core.springboot.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.core.springboot.model.User;

// 1. 所谓“映射接口”，就是将Java中的方法与特定的SQL命令关联上，
// 含义就是执行这个Java方法， MyBatis就会向数据库发出相应的SQL查询。
// 2. 如果没有启用@MapperScan，则需要给UserMapper加上@Mapper注解
public interface UserMapper {

    // 接口的方法名可以随意定义，关键在于@Select注解中的参数要与方法参数相对应。
    @Select("select * from User where id=#{id}")
    User selectUserById(int id);

}