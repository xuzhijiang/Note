package com.jinxuliang.autoconfig.dao;

import java.util.Arrays;
import java.util.List;

//模拟使用JDBC从关系型数据库中提取数据
public class JdbcUserDAO implements UserDAO {
    @Override
    public List<String> getAllUserNames() {
        System.out.println("**** Getting usernames from RDBMS *****");
        return Arrays.asList("Jim","John","Rob");
    }
}

