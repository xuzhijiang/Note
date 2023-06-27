package com.shiro.springboot.dao;

import com.shiro.springboot.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}