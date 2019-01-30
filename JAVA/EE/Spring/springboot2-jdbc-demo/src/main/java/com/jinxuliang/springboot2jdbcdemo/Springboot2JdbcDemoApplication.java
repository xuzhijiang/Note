package com.jinxuliang.springboot2jdbcdemo;

import com.jinxuliang.springboot2jdbcdemo.dao.DaoHelper;
import com.jinxuliang.springboot2jdbcdemo.dao.UserDao;
import com.jinxuliang.springboot2jdbcdemo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class Springboot2JdbcDemoApplication {

    public static void main(String[] args) {

       ConfigurableApplicationContext context= SpringApplication.run(Springboot2JdbcDemoApplication.class, args);

        testConnection(context);

        //testSaveUsers(context);

        //testGetAllUsers(context);

    }

    //测试保存新用户
    private static void testSaveUsers(ConfigurableApplicationContext context) {
        //获取UserDao对象
        UserDao dao=context.getBean(UserDao.class);
        System.out.println("\n保存新用户");
        User user= DaoHelper.createUserObj();
        dao.save(user);
        System.out.println(user+"已保存");
    }


    //测试提取全部用户清单
    private static void testGetAllUsers(ConfigurableApplicationContext context) {
        //获取UserDao对象
        UserDao dao=context.getBean(UserDao.class);
        System.out.println("\n提取所有用户清单");
        List<User> users=dao.getAllUsers();
        users.forEach(u->{
            System.out.println(u);
        });
    }

    //测试数据库的连接
    private static void testConnection(ConfigurableApplicationContext context) {
        DataSource dataSource= (DataSource) context.getBean("dataSource");
        System.out.println("\n数据源对象类型：");
        System.out.println(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("己连接");
        } catch (SQLException e) {
            System.out.println("未能连接:"+ e.getMessage());
        }
    }


}
