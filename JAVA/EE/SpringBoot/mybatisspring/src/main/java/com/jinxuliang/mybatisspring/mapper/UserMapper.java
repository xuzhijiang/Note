package com.jinxuliang.mybatisspring.mapper;

import com.jinxuliang.mybatisspring.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

// 定义MyBatis的映射接口:

// 所谓“映射接口”，其实就是将Java中的方法与特定的SQL命令关联上，
// 其含义就是——执行这个Java方法， MyBatis就会向数据库发出相应的SQL查询。

//如果没有启用@MapperScan，则需要给此接口(UserMapper)加上@Mapper注解

//@Component注解是可选的，主要是为了消除IntelliJ给出的Bean注入警告信息：无法Autowire特定的Bean
@Component(value="userMapper")
public interface UserMapper {

    // 接口中的方法名可以随意定义，关键在于@Select注解中的参数要与方法参数相对应。
    @Select("select * from User where id=#{id}")
    User selectUserById(int id);

}