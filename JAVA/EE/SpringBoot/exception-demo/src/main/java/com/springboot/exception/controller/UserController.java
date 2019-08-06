package com.springboot.exception.controller;

import com.springboot.exception.domain.User;
import com.springboot.exception.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @GetMapping
    public User getDefaultUserObject(){
        int ranValue = new Random().nextInt(100);
        User user = new User(ranValue,"user" + ranValue,"password");
        return user;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") int id){
        // 抛出自定义异常,然后CustomAdviceController会进行拦截异常
        throw new UserNotFoundException(id,"user not existed.");
    }

    // 如果方法中有BindingResult参数，则我们可以定制要返回的信息
    // 测试: {"id":43, "name":"", "password":"password1234"}
    @PostMapping(path="/withBindingResult",consumes = "application/json")
    public ResponseEntity createUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            StringBuffer sb=new StringBuffer();
            result.getAllErrors().stream().forEach(err->{
                FieldError fieldError=(FieldError)err;
                sb.append(fieldError.getField());
                sb.append(" ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            });
            return new ResponseEntity<>(sb.toString(),HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    /**
     *  如果数据校验失败了， createUser()中的代码还会执行吗？ 答:不会
     *  向Server端发出不符合要求的数据,例如：{"id":43, "name":"", "password":"password1234"}
     *  从控制台的输出信息可以看到，当Post上来的数据不符合事先约定的数据有效性规则时，方法体根本就不会执行， Spring Boot会直接把数据校验失败的相关信息转换为json，发回给客户端应用。
     */
    @PostMapping(path="/noBindingResult",consumes = "application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        System.out.println("从客户端Post上来的数据中创建对象："+user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
