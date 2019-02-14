package com.jinxuliang.exceptiondemo.controllers;

import com.jinxuliang.exceptiondemo.domain.User;
import com.jinxuliang.exceptiondemo.domain.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private Random random=new Random();

    @GetMapping()
    public User getDefaultUserObject(){
        int ranValue=random.nextInt(100);
        User user=new User(ranValue,"user"+ranValue,"password");
        return user;
    }

    // localhost:8080/user/1
    // 当控制器中的方法抛出一个未捕获的异常时，如果是浏览器访问的，
    // 默认的处理方式是返回一个500响应，同时显示异常对象的message信息。
    // 非浏览器访问，得到的是一个Json字符串
    @GetMapping("/{id}")
    public User findById(@PathVariable("id") int id){
        throw new UserNotFoundException(id,"user not existed.");
    }


    // 如果方法中有BindingResult参数(方法参数类型为BindingResult)，则我们可以定制要返回的信息。
    // 即：localhost:8080/user/withBindingResult, 使用Postman，用POST方法访问，携带
    //json字符串: {"id":43, "name":"", "password":"password1234"},可以看到，方法体中的代码得以执
    //行，特定的信息被传回给Postman(返回的信息:name 姓名不能为空
    //password 密码为1到8个字符)
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



    // 如果数据校验失败了， createUser()中的代码还会执行吗？
    //访问http://localhost:8080/user/noBindingResult，向Server端发出不符合要求的数据,例如：
    // {"id":43, "name":"", "password":"password1234"}
    //默认情况下， Spring Boot会返回一个Json字符串，包容了 相应的出错字段信息

    // 从控制台的输出信息可以看到，当Post上来的数据不符合事先约定的数据有效性
    // 规则时，方法体根本就不会执行， Spring Boot会直接把数据校验失败的相关信息
    // 转换为json，发回给客户端应用。
    @PostMapping(path="/noBindingResult",consumes = "application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        System.out.println("从客户端Post上来的数据中创建对象："+user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
