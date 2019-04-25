package com.jinxuliang.springboot2jdbcdemo.dao;

import com.jinxuliang.springboot2jdbcdemo.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

// 创建一个数据存取辅助类，以便重用代码。
public class DaoHelper {

    //从ResultSet中提取数据，创建一个User对象
    public static User fillUser(ResultSet rs) throws SQLException {
        if(rs==null){
            return null;
        }
        User user=new User();
        user.setId(rs.getInt("id"));
        user.setAge(rs.getInt("age"));
        user.setGender(rs.getString("gender"));
        user.setName(rs.getString("name"));
        return user;
    }

    private static Random random=new Random();
    //用于创建一个示例的用户数据，以便进行测试
    public static User createUserObj(){
        int ranValue=random.nextInt(100);
        User user=new User();
        user.setName("user"+ranValue);
        user.setGender(ranValue%2==0?"男":"女");
        user.setAge(ranValue);
        return user;
    }
}