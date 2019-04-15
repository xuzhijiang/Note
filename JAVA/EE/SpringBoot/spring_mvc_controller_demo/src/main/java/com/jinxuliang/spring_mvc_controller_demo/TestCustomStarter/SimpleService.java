package com.jinxuliang.spring_mvc_controller_demo.TestCustomStarter;

import org.springboot.starter.log.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * 使用单元测试分别调用这3个方法，由于work方法没有加上@Log注解，
 * core方法虽然加上了@Log注解，但是在配置中被exclude了，只有test方法可以正常计算耗时：
 *
 * 注意测试我们的自定义的Log的时候，因为自定义了PointcutAdvisor ，所以pom中要引入aop-starter
 * ,否则就我们自定义的Log就不生效
 */
@Service
public class SimpleService {

    @Log
    public void test(int num){
        System.out.println("----test---- " + num);
    }

    @Log
    public void core(int num){
        System.out.println("----core---- " + num);
    }

    public void work(int num){
        System.out.println("----work---- " + num);
    }

}
