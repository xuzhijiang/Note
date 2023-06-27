package com.qianfeng.test;

import com.qianfeng.dao.UserDAO;
import com.qianfeng.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName TestDAo
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/15
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestDAo {

    @Autowired
    private UserDAO userDAO;
    @Test
    public void testDAO(){
        userDAO.insertUser(new User(null,"叶承卓2","123","aaa"));
        List<User> users = userDAO.selectAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
