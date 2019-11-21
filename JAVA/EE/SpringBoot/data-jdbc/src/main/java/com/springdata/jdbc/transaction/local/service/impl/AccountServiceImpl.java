package com.springdata.jdbc.transaction.local.service.impl;

import com.springdata.jdbc.transaction.local.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 各种结果看类路径下的: 事务传播-各种结果.png
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addAccount(String name, int initMoney) {
        String accountid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        jdbcTemplate.update("insert into account (id, accountName, user, money) values(?,?,?,?)",
                1, accountid, name, initMoney);
        // 出现分母为0 的异常
        int i = 1 / 0;
    }

    @Override
    public void getAllAccount() {
        jdbcTemplate.query("select * from account", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                System.out.println("accountName: " + resultSet.getString("accountName")
                        + ", user: " + resultSet.getString("user") + ", money: " + resultSet.getInt("money"));
            }
        });
    }

}
