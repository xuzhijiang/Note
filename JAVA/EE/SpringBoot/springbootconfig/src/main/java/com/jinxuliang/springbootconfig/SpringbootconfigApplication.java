package com.jinxuliang.springbootconfig;

import com.jinxuliang.springbootconfig.config.ELConfig;
import com.jinxuliang.springbootconfig.domain.User;
import com.jinxuliang.springbootconfig.mapper.UserMapper;
import com.jinxuliang.springbootconfig.profile.IProfileBean;
import com.jinxuliang.springbootconfig.properties.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
public class SpringbootconfigApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(SpringbootconfigApplication.class, args);

        //testSpringEL(context);

        testExtractInfoFromPropertyFileByPrefix(context);

        //testProfile(context);


    }

    private static void testProfile(ApplicationContext context) {
        //依据spring.profiles.active的值，实例化不同的Bean
        IProfileBean profileBean = context.getBean(IProfileBean.class);
        System.out.println(profileBean);
    }

    //测试从application.properties中提取信息构建相应的配置对象
    private static void testExtractInfoFromPropertyFileByPrefix(ApplicationContext context) {
        MyProperties myProperties = context.getBean(MyProperties.class);
        System.out.println(myProperties);
    }

    //演示Spring表达式
    private static void testSpringEL(ApplicationContext context) throws IOException {
        ELConfig config = context.getBean(ELConfig.class);
        config.printFields();
    }

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(path = "/insert", method = RequestMethod.GET)
    public String insertUser(){
        StringBuilder sb = new StringBuilder();
        User user = new User();
        user.setName("xzj");

        userMapper.insert(user);
        int id = user.getId();// 获得自动生成的主键

        List<User> users = userMapper.selectAll();
        for(User u : users){
            System.out.println("id: " + u.getId() + ", name:" + u.getName());
            sb.append("id: " + u.getId() + ", name:" + u.getName() + "\n");
        }

        User u2 = new User();
        u2.setName("xxxxx");
        u2.setId(id);
        userMapper.update(u2);

        userMapper.delete(id);

        return sb.toString();
    }
}
