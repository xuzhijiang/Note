package com.jinxuliang.spring_mvc_controller_demo.controller;

import com.jinxuliang.spring_mvc_controller_demo.domain.DemoObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

// 路径的“合成”、对象注入和MIME


//展示Spring Boot项目中MVC控制器的基本功能
@Controller
@RequestMapping("/mvc")
public class MVCDemoController {

    // 控制器的URL与方法的URL“合成”为最终的URL：http://localhost:8080/mvc/index
    // Spring MVC可将Request对象“注入”到方法中
    // 在RequestMapping注解中使用produces属性可以指定
    // MIME值，这个值将放到响应的Header中发回给客户端。
    //访问标准Servlet对象(HttpServletRequest)
    @RequestMapping(value = "/index", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String index(HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access";
    }
    //  @ResponseBody和@RequestBody
    // @ResponseBody附加于控制器方法之上，通知Spring依据HTTP请求信息（即
    //accept值）选择合适的转换器（converter）转换数据（比如转为Json）传给客
    //户端。
    // 另一个类似的@RequestBody，附加于控制器方法参数之前（以后会看到例子），
    // 通知Spring将客户端发来的数据（从HTTP请求的body中提取）转换为Java对象。

    // 从查询字符串中提取参数-1
    //使用查询字符串queryString?id=100，可让方法的id得到一个值
    //如果不传，id参数得到一个null值
    @RequestMapping(value = "/queryString")
    @ResponseBody
    public String queryString(Long id, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,id:" + id;
    }
    //如果@RequestMapping只指定了Path，其他什么也没有，
    //那么可以通过严格匹配查询字符串key值与方法参数名的方式传值。
    //上例只是展示了一个参数的情况，事实上，可以有多个参数。

    // 从查询字符串中提取参数-2
    //使用@RequestParam显式指定参数匹配方式
    //可以指定其是否是必需的参数，如果不是必需的参数，则它可以为null
    @RequestMapping("/reqParam")
    @ResponseBody
    public String requestParam(
            @RequestParam("int_val") Integer intVal,
            @RequestParam(value = "str_val",required = false) String strVal) {
        if(strVal==null)
            strVal="default value";
        return "intValue:"+intVal+" strVal:"+strVal;
    }
    // 如果URL中的查询字符串中Key的名字与控制器中方法参数名不一
    // 致，则可以使用@RequestParam来获取参数。
    // localhost:8080/mvc/reqParam?int_val=100&str_val=hello

    // URL中的路径参数
    // 展示PathVariable注解的使用
    @RequestMapping(value = "/pathvar/{id:\\d+}")
    @ResponseBody
    public String pathVar(@PathVariable String id, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,id:" + id;
    }
    //使用@PathVariable为方法参数指定要提取的URL中Path的哪个部分作为方法参数，
    //注意两个名字要匹配。在URL的路径参数中加一个冒号，后面可以添加正则表达
    //式，对参数的有效性进行限定。示例中表示id必须是一个数字。



    // 使用查询字串填充对象

    // Spring MVC可以将查询字符值提取出来，自动创建一个数据对象并给其赋值。
    // 这一特性称为“模型绑定（Model Bind） ”。

    // "模型绑定（Model Bind）”同样支持POST请求，
    // 此时是从HTTP请求消息的body中提取数据填充对象。
    //使用查询字符串传送对象：/obj?name=jxl&id=100
    @RequestMapping(value = "/obj", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String passObj(DemoObject obj, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,object id:"
                + obj.getId() + " object name:" + obj.getName();
    }

    //传递JSON
    //在移动互联应用中，客户端（手机App， Web前端应用）通常都是采用Json格
    //式向服务端提交请求， Spring MVC提供了相应的功能能把Json字符串直接转
    //换为Java对象。

    //客户端使用POST方式，将一个Json字符串提交上来
    //服务端从HTTP body中取出数据，将其转换为DemoObject对象作为方法参数
    @RequestMapping(value="/fromJson",method = RequestMethod.POST)
    @ResponseBody
    public String fromJson(@RequestBody DemoObject demo){
        if(demo!=null){
            return demo.toString();
        }
        return "未能从HTTP请求中提取信息创建DemoObject对象";
    }
    // 注意一下本例中@RequestBody这个注解不可少。

    Random ran = new Random();

    //将向外界返回一个Json字符串：
    // {"id":2628847830238592887,"name":"DemoObject 2628847830238592887"}
    @RequestMapping("/getjson")
    @ResponseBody
    public DemoObject getObj() {
        Long ranValue = ran.nextLong();
        return new DemoObject(ranValue, "DemoObject " + ranValue);
    }
    // 在默认配置下，如果控制器的方法return了一个对象， Spring MVC会将
    //其序列化为一个Json字符串再返回给客户端。

    //注意上述代码中的@ResponseBody注解不可省（因为此方法所在控制
    //器为@Controller，而不是@RestController），否则，字符串会
    //被当成Thymeleaf的视图文件名，导致一个异常对象被抛出。

    //一个方法，可以响应多个URL
    //这种功能特性用得很少，了解就好。
    @RequestMapping(value = {"/url1", "/url2"})
    @ResponseBody
    public String multiURL(HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access";
    }




}

