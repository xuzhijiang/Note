package com.mybatis.core.MapperFactoryBean;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User selectById(int id);
}