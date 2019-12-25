package com.mybatis.core.legacy.stepfour;

import org.apache.ibatis.annotations.Select;

// 可以通过注解的方式直接将sql写到UserMapper接口的方法上，此时你就不再需要UserMapper.xml
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User selectById(int id);
}