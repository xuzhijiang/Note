package com.springboot.core.mapper;

import com.springboot.core.bean.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO user (id, name) VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Update("UPDATE user SET name=#{name} WHERE id=#{id}")
    int update(User user);

    @Select("SELECT * FROM user")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
    })

    List<User> selectAll();

    @Delete("DELETE FROM user WHERE id = #{id}")
    int delete(int id);
}