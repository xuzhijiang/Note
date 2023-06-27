package com.qianfeng.test;

import com.qianfeng.dao.PermissionDAO;
import com.qianfeng.dao.RoleDAO;
import com.qianfeng.dao.UserDAO;
import com.qianfeng.pojo.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDAO {
    @Test
    public void testDAO(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
        RoleDAO roleDAO = context.getBean("roleDAO", RoleDAO.class);
        PermissionDAO permissionDAO = context.getBean("permissionDAO", PermissionDAO.class);
        User user = userDAO.queryUserByUsername("松松");
        System.out.println(user);

        System.out.println(roleDAO.queryAllRolenameByUsername("松松"));

        System.out.println(permissionDAO.queryAllPermissionByUsername("张远航"));
        System.out.println(permissionDAO.queryAllPermissionByUsername("松松"));

    }
}
