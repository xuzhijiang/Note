package org.mybatis.core.mapper;

import org.mybatis.core.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    int insertUser(User user);
    public User selectById(Integer id);
}