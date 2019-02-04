package com.jinxuliang.spring_boot_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 给项目添加一个controller包，再添加一个
// HelloController类，在类上添加@Controller注解，在hello方法上添加@RequestMapping注解。

// 示例代码解析

// 给类添加@Controller（或@RestController）注解， 表明这个
// 类是一个控制器， Spring IoC容器能自动识别@Controller注解
// 并在合适的时候实例化它。

// 给控制器类中的方法添加@RequestMapping注解，指定此方法所关
// 联的URL， Spring MVC负责将此URL请求映射到注解了以此URL为参数的@RequestMapping的方法上，
// 换句话说，就是使用控制器类中的这个方法来响应这个Web请求，生成HTML响应发回给客户端。

// URL + HTTP method，唯一确定某控制器的某方法用于响应此HTTP请求。
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        //使用放在resources/templates/hello.html作为模板
        //Thymeleaf生成HTML返回给客户端
        return "hello";
    }
    // 在resources/templates文件夹下添加一个hello.html文件，
    // 注意其`文件名字`与`前面控制器中hello()方法返回的字符串`是一致的。

    // 默认情况下，约定templates文件夹专用于放置视图模板文件，
    // 本例使用Thymeleaf模板文件，它以.html作为扩展名，是在标准的HTML文档基础上扩展而成的
}
