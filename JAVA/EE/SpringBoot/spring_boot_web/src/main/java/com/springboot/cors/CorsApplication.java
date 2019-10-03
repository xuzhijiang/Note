package com.springboot.cors;

import com.springboot.cors.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class CorsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorsApplication.class, args);
    }

    @RequestMapping(value = "/getUser", produces = "application/json")
    public User getUser() {
        User user = new User();
        user.setUsername("xxxxxooooo");
        return user;
    }

}
