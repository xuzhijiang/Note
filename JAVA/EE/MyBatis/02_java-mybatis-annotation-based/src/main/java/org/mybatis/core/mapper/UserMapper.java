package org.mybatis.core.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.core.model.User;

import java.util.List;

public interface UserMapper {
    @Insert("INSERT INTO user(id,last_name,email,gender) VALUES (#{id},#{lastName},#{email},#{gender})")
    public int insert(User user);
}
