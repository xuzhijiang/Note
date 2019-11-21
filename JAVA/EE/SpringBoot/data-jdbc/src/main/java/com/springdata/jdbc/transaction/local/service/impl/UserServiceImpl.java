package com.springdata.jdbc.transaction.local.service.impl;

import com.springdata.jdbc.transaction.local.service.AccountService;
import com.springdata.jdbc.transaction.local.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AccountService accountService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 判断插入user和添加账户是否是同一个事务,就要判断使用的是不是同一个Connection实例
    // 这个要通过debug验证,参考这个视频的debug验证过程: https://www.bilibili.com/video/av67474938/?p=5
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(String name) {
        // 插入user记录
        jdbcTemplate.update("insert into user (id, name) values(?,?)", 1, name);
        // 调用accountService添加账户
        accountService.addAccount("luban", 10000);
    }

    @Override
    public void getAllUser() {
        jdbcTemplate.query("select * from user", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                System.out.println("user 查询完成: ");
                System.out.println("id: " + resultSet.getInt("id") + ", name: " + resultSet.getString("name"));
            }
        });
    }

}
