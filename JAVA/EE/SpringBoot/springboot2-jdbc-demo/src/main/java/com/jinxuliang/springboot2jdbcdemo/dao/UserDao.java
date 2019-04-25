package com.jinxuliang.springboot2jdbcdemo.dao;

import com.jinxuliang.springboot2jdbcdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 设计并实现一个数据存取对象(Data Access Object)，
// 里面展示了如何使用JdbcTemplate存取MySQL数据库
@Repository
public class UserDao {

    //自动注入Spring所提供的JdbcTemplate对象
    @Autowired
    private JdbcTemplate jdbcTemplate;


    //提取所有的用户数据
    public List<User> getAllUsers(){
        String sql="select * from user";
        final List<User> users=new ArrayList<>();
        //从数据库中提取记录集，针对每条记录，调用RowCallbackHandler回调函数
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                //调用辅助函数创建User对象，并将其加入到对象集合中
                System.out.println("processRow called!");
                users.add(DaoHelper.fillUser(resultSet));
            }
        });
        return users;
    }

    //保存一个用户对象
    public int save(User user){
        String sql="insert into user(name,age,gender) values(?,?,?)";
        int result= jdbcTemplate.update(sql,user.getName(),
                user.getAge(),user.getGender());
        return result;
    }

}
