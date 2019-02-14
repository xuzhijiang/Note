package com.jinxuliang.rest_demo.controllers;

import com.jinxuliang.rest_demo.domain.MyClass;
import com.jinxuliang.rest_demo.domain.UserQueryCondition;
import com.jinxuliang.rest_demo.repositories.MyClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//指定本控制器中的方法是RESTful Service
@RestController
@RequestMapping(path="/myservice")
//允许跨域调用
@CrossOrigin(origins = "*")
public class MyServiceController {

    //返回一个JavaBean,将会被序列化为Json字符串
    @GetMapping(path="/hello")
    public MyClass hello(){
        // RESTful Controller方法的返回值，默认情况下会自动地转换为JSON。
        // 可以通过看Http请求的Response Headers的Content-Type来确认.
        return new MyClass(1,"Hello");
    }

    //使用路径参数
    //在RESTful API中，使用路径参数传送数据，是一种非 常常见的方式。

    //传入路径参数
    @GetMapping("/find/{id}/{info}")
    public MyClass find(@PathVariable("id") int id,
                            @PathVariable("info") String info){
        return new MyClass(id,info);
    }

    //使用查询参数
    @GetMapping("/query")
    public String testQueryParam(@RequestParam(name="info",defaultValue = "hello") String message){
        return message;
    }

    // 将多个查询参数合成为一个参数对象-1
    // http://localhost:8080/myservice/querycondition?usename=John&isMale=true&age=30
    // 关键点：查询参数的key，与Java类UserQueryCondition中的字段名称一致。
    @GetMapping("/querycondition")
    //合并多个查询参数为查询参数对象
    public UserQueryCondition testQueryObject(UserQueryCondition condition){
        //Do something...
        return  condition;
    }




    // 分页功能的实现:
    // Spring MVC提供了一个Page接口（归属于org.springframework.data.domain），
    // 只需要给其传入size， page和sort参数值，
    //其实方法得到的是一个PageRequest对象，其成员见下：

    //http://localhost:8080/myservice/page?size=10&page=2&sort=age,desc
    //示例的分页参数：
    //1. 每页显示10条记录
    // 2. 显示第2页
    // 3. 按照年龄降序显示（desc）

    // 控制器方法就能得到其值。
    @GetMapping("/page")
    public Pageable testPageable(Pageable pageable){
        return pageable;
    }




    // 生成特定状态码的响应:

    // 当需要将特定的状态码和相关信息返回给客户端时，需要使用ResponseEntity<T>这个类：
    //将Repository注入到Controller中
    @Autowired
    MyClassRepository repository;

    @GetMapping("/find/{id}")
    public ResponseEntity<MyClass> findById(@PathVariable("id") int id){
        //查找指定Id对象
        MyClass obj=repository.findById(id);
        if(obj==null){
            //生成404响应
            return new ResponseEntity<>(obj,HttpStatus.NOT_FOUND);
        }
        //生成200响应
        return new ResponseEntity<>(obj,HttpStatus.OK);
    }

    //指定Post上来的数据必须是json,返回值也序列化为json
    @PostMapping(consumes = "application/json",
            produces = "application/json",
            path = "/post")
    public ResponseEntity<MyClass> postMyClass(@RequestBody MyClass obj){
        if(obj!=null){
            repository.save(obj);
            //设置location Header
            return new ResponseEntity<>(obj,HttpStatus.CREATED);
        }
        else{
            //非法数据，返回一个400响应
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





    // 处理 HTTP Header:
    // http://localhost:8080/myservice/header/hello
    // 在实际开发中， Web Server经常使用这种方式向浏览
    // 器端发送特定的HTTP Header，完成诸如缓存控制、跨域访问等各种功能。
    //相应地，浏览器端应用也经常发送特定的HTTPHeader到Web Server，完成诸如认证和授权等功能。

    //添加自定义的Header
    @GetMapping("/header/{value}")
    public ResponseEntity creatHeader(@PathVariable("value") String value){
        HttpHeaders headers=new HttpHeaders();
        headers.add("info",value);
        return new ResponseEntity(headers,HttpStatus.OK);
    }
}
