## 控制器编程基础技巧

### 控制器信息的传入与传出

>示例项目： spring_mvc_controller_demo

#### 理解Spring Boot MVC的请求处理流程

[SpringBootMVC的请求处理流程.png]

```java
// @RequestMapping的可配置项
public @interface RequestMapping {
// 配置请求映射名称
String name() default "";

// 通过路径映射
@AliasFor("path")
String[] value() default {};

// 通过路径映射回path配置项
@AliasFor("value")
String[] path() default {};

// 限定只响应HTTP请求类型，如GET、 POST、 HEAD、 OPTIONS、 PUT、 TRACE等
// 默认的情况下，可以响应所有的请求类型
RequestMethod[] method() default {};

// 当存在对应的HTTP参数时才响应请求
String[] params() default {};

// 限定请求头存在对应的参数时才响应
String[] headers() default {};

// 限定HTTP请求体提交类型，如"application/json"、 "text/html"
String[] consumes() default {};

// 限定返回的内容类型，仅当HTTP请求头中的(Accept)
// 类型中包含该指定类型时才返回
String[] produces() default {};
}
```

> Spring 4.3之后提供简化版注解：@GetMapping、 @PostMapping、 @PatchMapping、
@PutMapping和@DeleteMapping

> 使用Postman这个工具测试Web应用的HTTP请求与响应。

```
POST: http:localhost:8080/mvc/fromJson

要提交的Json数据:
{
	"id":1000,
	"name":"DemoObject Post from Client"
}

指定Content-Type为Json

服务端发回的响应:
DemoObject{id=1000, name='DemoObject Post from Client'}
```

### 请求的转发与重定向

> 所谓“转发（forward） ”，就是某个控制器方法在响应HTTP请求时，它只把“活”干一半，另一半转给另一个方法去干。所有工作都是在同一个HTTP请求处理过程中完成的

> 所谓“重定向（Redirect） ”，就是某个控制器方法在响应HTTP请求时，“因为自己现在不干这事了”，就向浏览器发出一个“301/302”响应，通知浏览器向另一个URL发出请求，
“让其他人干这事”。

> 使用Chrome测试“转发功能”: 访问localhost:8080/redir/forward,然后打开开发者工具的Network,发现仅有一个forward请求， Server端
却调用了两个方法来响应它

> 测试重定向功能的实现,打开浏览器，访问：http://localhost:8080/redir/redirToShow(打开chrome,打开调试panel，Network，可以看到请求状态码)
,注意有两个网络请求，第一个是302,第二个是200。另外,最终浏览器显示的URL为/redir/showInfo而不是/redir/redirToShow.

### 小结

1. 本讲介绍了Spring Boot MVC项目中控制器的基础编程技巧，这些编程技巧在实际开发中用得极多，一定要熟练地掌握。
2. 学习过程中需要特别注意一下数据的传送方式，主要有两种：一是数据如何从客户端传给Server端，二是如何在Server端保存多个HTTP请求之间的状态信息。