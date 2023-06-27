package com.qianfeng.dao;

import com.qianfeng.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDAO {
    User queryUserByUsername(@Param("username") String username);
    Integer insertUser(User user);
    List<User> selectAllUsers();
}
