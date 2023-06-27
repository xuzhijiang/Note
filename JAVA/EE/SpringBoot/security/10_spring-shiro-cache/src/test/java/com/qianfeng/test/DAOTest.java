package com.qianfeng.test;

import com.qianfeng.service.PermissionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName JiaMiTest
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/10
 * @Version V1.0
 **/
public class DAOTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PermissionService ps = (PermissionService) context.getBean("permissionServiceImpl");
        ps.queryAllPermissionByUsername("songsong");
    }
}
