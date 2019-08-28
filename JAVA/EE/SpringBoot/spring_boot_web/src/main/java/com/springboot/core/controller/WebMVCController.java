package com.springboot.core.controller;

import com.springboot.core.bean.DemoObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

// 1. 给类添加@Controller(或@WebRestController）注解， 表明是一个控制器类.
// 2. @ResponseBody附加于控制器方法之上，通知Spring依据HTTP请求信息(即accept值）选择合适的转换器(converter）转换数据(比如转为Json）传给客户端。
// 3. 另一个类似的@RequestBody，附加于控制器方法参数之前，通知Spring将客户端发来的数据(从HTTP请求的body中提取）转换为Java对象。注意一下本例中@RequestBody这个注解不可少。
@Controller
@RequestMapping("/mvc")
public class WebMVCController {

    @RequestMapping
    @ResponseBody
    public String index(Model model){
        return "Controller default method";
    }

    /**
     * Thymeleaf模板文件以.html作为扩展名，是在标准的HTML文档基础上扩展而成的
     */
    @RequestMapping(value = "/hello")
    public String hello(Model model){// index方法的参数，Spring IoC容器会自动注入进来
        // 使用放在resources/templates/hello.html作为模板生成HTML返回给客户端
        model.addAttribute("info", "index page");
        // 默认情况下，约定templates文件夹专用于放置视图模板文件，
        return "index";//模板文件位置/resources/templates/index.html
    }

    // 1. 控制器类的URL与方法的URL“合成”为最终的URL：http://localhost:8080/mvc/index
    // 2. Spring MVC可将Request对象“注入”到方法中
    // 3. 在RequestMapping注解中使用produces属性可以指定MIME值，这个值将放到响应的Header中发回给客户端。
    @RequestMapping(value = "/ ", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String index(HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " access";
    }

    //使用查询字符串queryString?id=100(注意id的大小写要和方法参数中的大小写一致才行)，
    // 可让方法的id得到一个值如果不传，id参数得到一个null值
    @RequestMapping(value = "/queryString")
    @ResponseBody
    public String queryString(Long id, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,id:" + id;
    }

    // 使用@RequestParam显式指定参数匹配方式,可以指定其是否是必需的参数，如果不是必需的参数，则它可以为null
    // 如果URL中的查询字符串中Key的名字与控制器中方法参数名不一致，则可以使用@RequestParam来获取参数。
    // localhost:8080/mvc/reqParam?int_val=100&str_val=hello
    @RequestMapping("/reqParam")
    @ResponseBody
    public String requestParam(
            @RequestParam("int_val") Integer intVal,
            @RequestParam(value = "str_val",required = false) String strVal) {
        if(strVal==null)
            strVal="default value";
        return "intValue:"+intVal+" strVal:"+strVal;
    }

    //使用@PathVariable为方法参数指定要提取的URL中Path的哪个部分作为方法参数，
    //注意两个名字要匹配。在URL的路径参数中加一个冒号，后面可以添加正则表达
    //式，对参数的有效性进行限定。示例中表示id必须是一个数字。
    @RequestMapping(value = "/pathvar/{id:\\d+}/{name}")
    @ResponseBody
    public String pathVar(@PathVariable("id") int id, @PathVariable("name") String name, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,id:" + id;
    }

    // 如果是method7/sss,就会报错,转换异常.
    @RequestMapping(value="/method7/{id}")
    @ResponseBody
    public String method7(@PathVariable("id") int id){
        return "method7 with id="+id;
    }

    // 1. Spring MVC可将查询字符值提取出来，自动创建一个数据对象并给其赋值。
    // 这一特性称为“模型绑定(Model Bind） ”。
    // 2. "模型绑定(Model Bind）”同样支持POST请求，此时是从HTTP请求的body中提取数据填充对象。
    // 3. 使用查询字符串填充DemoObject对象：/obj?name=xzj&id=100
    @RequestMapping(value = "/obj", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String passObj(DemoObject obj, HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access,object id:"
                + obj.getId() + " object name:" + obj.getName();
    }

    //1. 在移动互联应用中，客户端(手机App， Web前端应用）通常都是采用Json格
    //式向服务端提交请求， Spring MVC提供了相应的功能能把Json字符串直接转
    //换为Java对象。
    //2. 客户端使用POST方式，将一个Json字符串提交上来(例如: {"id": 20, "name": "xzj"})
    // 3. 服务端从HTTP body中取出数据，将其转换为DemoObject对象作为方法参数
    @RequestMapping(value="/fromJson",method = RequestMethod.POST)
    @ResponseBody
    public String fromJson(@RequestBody DemoObject demo){
        if(demo!=null){
            return demo.toString();
        }
        return "未能从HTTP请求中提取信息创建DemoObject对象";
    }

    // 1. 使用@ResponseBody注解之后,如果控制器的方法return了一个对象， Spring MVC会将
    //其序列化为一个Json字符串再返回给客户端(@ResponseBody注解的功劳)。
    // 2. 注意代码中的@ResponseBody注解不可省(因为此方法所在控制
    //器为@Controller，而不是@WebRestController），否则，字符串会
    //被当成Thymeleaf的视图文件名，导致一个异常对象被抛出。
    @RequestMapping("/getjson")
    @ResponseBody
    public DemoObject getObj() {
        Long ranValue = new Random().nextLong();
        return new DemoObject(ranValue, "DemoObject " + ranValue);
    }

    // consumers用来限制http请求的Content-Type
    @RequestMapping(value="/produceConsumer", produces={"application/json","application/xml"}, consumes="text/html")
    @ResponseBody
    public String producesAndConsumersTestMethod(){
        return "aaa";
    }

    // 带headers的@RequestMapping：指定应该存在的headers(header指的是http请求头里面的header,可以使用postman测试)
    @RequestMapping(value="/headers", headers={"name=xzj", "id=1"})
    @ResponseBody
    public String headers(){
        return "headers test";
    }

    // 为控制器类创建一个回退方法(fallback method)，以确保我们捕获所有客户端请求，
    // 即使没有匹配的处理程序方法时，可以向用户发送自定义页面
    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod(){
        return "fallback method";
    }

    //一个方法，可以响应多个URL这种功能特性用得很少，了解就好。
    @RequestMapping(value = {"/url1", "/url2"})
    @ResponseBody
    public String multiURL(HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access";
    }

    // 可以响应多个http方法
    @RequestMapping(value = "/multiMethod", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String multiMethod(HttpServletRequest request) {
        return "url:" + request.getRequestURL() + " can access";
    }

    @RequestMapping(value = "/noContent", method = RequestMethod.GET)
    public HttpStatus testNoContent() {
        return HttpStatus.NO_CONTENT;
    }

    @RequestMapping(value = "/badRequest", method = RequestMethod.GET)
    public HttpStatus testBadRequest() {
        return HttpStatus.BAD_REQUEST;
    }

}

/**
 * 使用cURL命令(catch url)来测试
 *
 * curl -X POST url;
 *
 * curl -X GET url;
 *
 * curl -H "name:xzj" -H "id:1" http://localhost:9090/mvc/headers
 *
 * curl -H "Content-Type:text/html" http://127.0.0.1:9090/mvc/produceConsumer
 *
 * curl -H "Content-Type:text/html" -H "Accept:application/json" -i http://127.0.0.1:9090/mvc/produceConsumer
 *
 * curl -H "Content-Type:text/html" -H "Accept:application/xml" -i http://127.0.0.1:9090/mvc/produceConsumer
 *
 * curl http://localhost:9090/mvc/pathVar/10/Lisa;
 *
 * curl http://localhost:9090/mvc/obj?id=20;
 */