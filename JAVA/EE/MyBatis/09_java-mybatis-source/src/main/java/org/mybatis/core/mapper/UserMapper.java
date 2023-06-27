package org.mybatis.core.mapper;

import org.mybatis.core.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    int insert(User user);
    int insertEnum(User user);
    public User selectById(Integer id);
    List<User> selectAll();
}