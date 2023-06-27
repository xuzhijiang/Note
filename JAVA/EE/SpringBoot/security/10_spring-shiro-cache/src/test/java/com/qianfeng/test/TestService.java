package com.qianfeng.test;

import com.qianfeng.pojo.User;
import com.qianfeng.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName TestDAo
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/15
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestService {

    @Autowired
    private UserService userService;
    @Test
    public void testService(){
        userService.insertUser(new User(null,"叶承卓4","123","aaa"));
        /*List<User> users = userService.selectAllUsers();
        for (User user : users) {
            System.out.println(user);
        }*/
    }
}
