package com.qianfeng.dao;

import com.qianfeng.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
    public User queryUserByUsername(@Param("username") String username);
}
