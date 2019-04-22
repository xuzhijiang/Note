package org.springframework.samples.routing.mapper.db1;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.samples.routing.entity.User;

public interface UserSelectMapper {

    @Select("select * from user where id = #{id}")
    public User selectById(@Param("id") int id);

}
