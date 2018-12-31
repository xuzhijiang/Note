package com.journaldev.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
// 和class定义一起使用来创建base URI
@Controller
@RequestMapping("/home")
public class HomeController {
	
	//下面的注释也可以写成@RequestMapping（“/ method0”）。 
	//在旁注中，我使用@ResponseBody发送此Web请求的String响应，
	// 这样做是为了使示例简单。 像我一直这样，
	// 我将在Spring MVC应用程序中使用这些方法，并使用简单的程序或脚本对它们进行测试。
	@RequestMapping(value="/method0")
	@ResponseBody
	public String method0(){
		return "method0";
	}
	
	// 带有多个URI的@RequestMapping：我们可以使用单个方法来处理多个URI，例如：
	// 如果您将查看RequestMapping注释的源代码，您将看到它的所有变量都是数组。 
	// 我们可以为处理程序方法的URI映射创建String数组。
	@RequestMapping(value={"/method1","/method1/second"})
	@ResponseBody
	public String method1(){
		return "method1";
	}
	
	// 使用HTTP方法进行@RequestMapping：
	// 有时我们希望基于所使用的HTTP方法执行不同的操作，即使请求URI保持不变。 
	// 我们可以使用@RequestMapping method 变量来缩小将调用此方法的HTTP方法。
	@RequestMapping(value="/method2", method=RequestMethod.POST)
	@ResponseBody
	public String method2(){
		return "method2";
	}
	
	@RequestMapping(value="/method3", method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String method3(){
		return "method3";
	}
	
	// 带headers的@RequestMapping：
	// 我们可以指定应该存在的headers来调用the handler method.
	@RequestMapping(value="/method4", headers="name=pankaj")
	@ResponseBody
	public String method4(){
		return "method4";
	}
	
	@RequestMapping(value="/method5", headers={"name=pankaj", "id=1"})
	@ResponseBody
	public String method5(){
		return "method5";
	}
	
	// 使用Produces和Consumes的@RequestMapping：
	// 我们可以使用header Content-Type和Accept来查找request contents
	// 以及它想要响应的mime message。 为清楚起见，@RequestMapping提供了
	// produces和consumes变量，用这两个变量，我们可以指定请求content-type以及响应content-type
	@RequestMapping(value="/method6", produces={"application/json","application/xml"}, consumes="text/html")
	@ResponseBody
	public String method6(){
		return "method6";
	}
	// 上面的方法只能在Content-Type作为text/html的时候调用，
	// 并且能够生成( produce) application/json和application/xml类型的消息。
	
	
	
	// @RequestMapping with @PathVariable：
	// RequestMapping注释可用于处理动态URI，其中一个或多个URI值用作参数。
	// 我们甚至可以为URI动态参数指定正则表达式以仅接受特定类型的输入。 
	// 它与@PathVariable注释一起使用，通过它我们可以将URI变量映射到其中一个方法参数(URI中的id映射到method7的id)
	@RequestMapping(value="/method7/{id}")
	@ResponseBody
	public String method7(@PathVariable("id") int id){
		return "method7 with id="+id;
	}
	
	@RequestMapping(value="/method8/{id:[\\d]+}/{name}")
	@ResponseBody
	public String method8(@PathVariable("id") long id, @PathVariable("name") String name){
		return "method8 with id= "+id+" and name="+name;
	}
	
	// @RequestMapping使用@RequestParam获取URL参数：
	// 有时我们在请求URL中获取参数，主要是在GET请求中。 
	// 我们可以使用@RequestMapping和@RequestParam注释来检索URL参数并将其 映射到 “方法参数”。 

	// 要使此方法起作用，参数名称应为“id”，并且应为int类型。
	@RequestMapping(value="/method9")
	@ResponseBody
	public String method9(@RequestParam("id") int id){
		return "method9 with id= "+id;
	}
	
	
	// @RequestMapping default method：如果方法的值为空，则它作为控制器类的默认方法

	// 正如您在上面看到的那样，我们已将/home映射到HomeController，此方法将用于默认的URI请求。
	@RequestMapping()
	@ResponseBody
	public String defaultMethod(){
		return "default method";
	}
	
	// @RequestMapping fallback(后备) 方法：
	// 我们可以为控制器类创建一个回退方法，以确保我们捕获所有客户端请求，
	// 即使没有匹配的处理程序方法。 当没有请求的处理程序方法时，它可以向用户发送自定义404响应页面。
	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod(){
		return "fallback method";
	}
}

//请注意，我已在Tomcat-8上部署了我的Web应用程序，并且它在端口9090上运行.
//SpringRequestMappingExample是应用程序的servlet上下文。 
//现在，当我通过命令行执行springTest.sh脚本时，我得到以下输出。


//xzj:~ xzj$ ./springTest.sh 
//curl http://localhost:9090/SpringRequestMappingExample/home/method0
//method0
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home
//default method
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/xyz
//fallback method
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method1
//method1
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method1/second
//method1
//
//*****
//
//curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method2
//method2
//
//*****
//
//curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method3
//method3
//
//*****
//
//curl -X GET http://localhost:9090/SpringRequestMappingExample/home/method3
//method3
//
//*****
//
//curl -H name:pankaj http://localhost:9090/SpringRequestMappingExample/home/method4
//method4
//
//*****
//
//curl -H name:pankaj -H id:1 http://localhost:9090/SpringRequestMappingExample/home/method5
//method5
//
//*****
//
//curl -H Content-Type:text/html http://localhost:9090/SpringRequestMappingExample/home/method6
//method6
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method6
//fallback method
//
//*****
//
//curl -H Content-Type:text/html -H Accept:application/json -i http://localhost:9090/SpringRequestMappingExample/home/method6
//HTTP/1.1 200 OK
//Server: Apache-Coyote/1.1
//Content-Type: application/json
//Content-Length: 7
//Date: Thu, 03 Jul 2014 18:14:10 GMT
//
//method6
//
//*****
//
//curl -H Content-Type:text/html -H Accept:application/xml -i http://localhost:9090/SpringRequestMappingExample/home/method6
//HTTP/1.1 200 OK
//Server: Apache-Coyote/1.1
//Content-Type: application/xml
//Content-Length: 7
//Date: Thu, 03 Jul 2014 18:14:10 GMT
//
//method6
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method7/1
//method7 with id=1
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method8/10/Lisa
//method8 with id= 10 and name=Lisa
//
//*****
//
//curl http://localhost:9090/SpringRequestMappingExample/home/method9?id=20
//method9 with id= 20
//
//*****DONE*****

