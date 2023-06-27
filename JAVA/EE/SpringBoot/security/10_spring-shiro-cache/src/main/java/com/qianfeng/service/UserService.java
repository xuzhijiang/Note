package com.qianfeng.service;

import com.qianfeng.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    User queryUserByUsername(@Param("username") String username);
    Integer insertUser(User user);
    List<User> selectAllUsers();
}
