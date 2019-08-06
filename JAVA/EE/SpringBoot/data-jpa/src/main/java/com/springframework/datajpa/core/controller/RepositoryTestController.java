package com.springframework.datajpa.core.controller;

import com.springframework.datajpa.core.domain.Teacher;
import com.springframework.datajpa.core.domain.User;
import com.springframework.datajpa.core.repository.TeacherRepository;
import com.springframework.datajpa.core.repository.UserRepository;
import com.springframework.datajpa.core.request.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Random;
import java.util.UUID;

@RestController
public class RepositoryTestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 25; i++) {
            createUser();
            createTeacher();
        }
    }

    private void createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("teacher " + UUID.randomUUID().toString());
        teacher.setAge(new Random().nextInt(50));
        String gender = (randomString().hashCode() % 2 == 0) ? "男" : "女";
        teacher.setGender(gender);
        teacherRepository.save(teacher);
    }

    @PostMapping("/api/users")
    public User createUser() {
        User user = new User();
        user.setEmail(randomString() + "@cp.com");
        user.setName("Mr " + randomString());
        user.setGender(randomString().hashCode() % 2 == 0);
        userRepository.save(user);
        return user;
    }

    @GetMapping(value = "/")
    @ResponseBody
    String home() {
        return "<h1>Hello World!</h1>";
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserReq user) {
        User exist = userRepository.findById(id).get();
        if (exist == null) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        exist.setEmail(user.getEmail());
        exist.setGender(user.isGender());
        exist.setName(user.getName());
        userRepository.save(exist);
        return exist;
    }

    @DeleteMapping("/api/users/{id}")
    public boolean deleteUser(@PathVariable("id") Long id) {
        User exist = userRepository.findById(id).get();
        if (exist != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @GetMapping("/api/users")
    public Page<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return userRepository.findAll(new PageRequest(page, size));
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
    }

}
