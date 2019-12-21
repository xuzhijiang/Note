package com.springboot.customAop;

import com.springboot.customAop.factory.BeanFactory;
import com.springboot.customAop.service.UserService;
import com.springboot.customAop.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * 自定义AOP事务测试
 */
public class AopTest {

    @Test
    public void testCustomAop() {
        BeanFactory beanFactory = new BeanFactory();
        try {
            UserService bean = (UserService) beanFactory.getBean(UserServiceImpl.class);
            System.out.println(bean.getClass().getName());
            bean.getUser();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test Finally
     */
    @Test
    public void test() {
        String val = aa();
        System.out.println(val);
    }

    private String aa() {
        try {
            return "aaa";
        }finally {
            System.out.println("fi");
        }
    }
}
