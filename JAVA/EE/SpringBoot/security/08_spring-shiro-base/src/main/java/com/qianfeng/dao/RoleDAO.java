package com.qianfeng.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleDAO {
    public Set<String> queryAllRolenameByUsername(@Param("username") String username);
}
