package com.jinxuliang.springbootjpademo;

import com.jinxuliang.springbootjpademo.jpa.JpaUserRepository;
import com.jinxuliang.springbootjpademo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringbootJpaDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringbootJpaDemoApplication.class, args);

        List<User> users = null;
        User user = null;

        // 我们只要定义好接口JpaUserRepository，Spring Data JPA会自动地帮助
        // 我们生成一个实现了这个接口的数据存取对象,本例中即JpaUserRepository的实现类对象
        JpaUserRepository repo = context.getBean(JpaUserRepository.class);
        //默认情况下，是org.springframework.data.jpa.repository.support.SimpleJpaRepository
        System.out.println(repo);

        System.out.println("\n----------使用系统提供的JPA接口查询对象------\n");
        users = repo.findAll();
        users.forEach(u -> {
            System.out.println(u);
        });

        System.out.println("\n查询Id=1的用户\n");
        Optional<User> result = repo.findById(1);
        if (result.isPresent()) {
            System.out.println(result.get());
        } else {
            System.out.println("没有找到");
        }

        System.out.println("\n----------使用约定的JPA接口方法查询------\n");

        System.out.println("\n查询男性用户\n");
        users = repo.findAllByGender("男");
        for (User u : users) {
            System.out.println(u);
        }

        System.out.println("\n查询指定性别的大于指定岁数的用户\n");
        users = repo.findAllByGenderAndAgeGreaterThan("男", 40);
        for (User u : users) {
            System.out.println(u);
        }

        System.out.println("\n----------使用显式指定的查询命令进行查询------\n");

        System.out.println("测试使用原生的SQL查询指定性别的用户");
        users = repo.findAllByGenderUseNativeSQL("女");
        for (User u : users) {
            System.out.println(u);
        }

        System.out.println("\n使用JPA查询语言自定义查询\n");
        users = repo.findUsers("j");
        users.forEach(u -> {
            System.out.println(u);
        });

        context.close();
    }
}
