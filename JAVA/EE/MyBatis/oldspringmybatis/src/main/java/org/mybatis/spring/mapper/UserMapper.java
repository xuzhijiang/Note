package org.mybatis.spring.mapper;

import org.mybatis.spring.domain.User;

public interface UserMapper {

    public int insert(User user);

    public User selectById(int id);
}
