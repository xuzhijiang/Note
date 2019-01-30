package com.jinxuliang.mybatisspring.mapper;

import com.jinxuliang.mybatisspring.model.User;
import org.apache.ibatis.annotations.Select;

import org.springframework.stereotype.Component;

//如果没有启用@MapperScan，则需要给此接口加上@Mapper注解
//@Mapper
//@Component注解是可选的，主要是为了消除IntelliJ给出的Bean注入警告信息：
//无法Autowire特定的Bean
@Component(value="userMapper")
public interface UserMapper {
    // 接口中的方法名可以随意定义，关键在于@Select注解中的参数
    //要与方法参数相对应。
    @Select("select * from User where id=#{id}")
    User selectUserById(int id);
}

