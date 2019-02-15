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
@Controller
@RequestMapping("/home")
public class HomeController {


	@RequestMapping(value="/method0")// 下面的注释也可以写成@RequestMapping("/method0")
	@ResponseBody// 在旁注中，我使用@ResponseBody发送此Web请求的String响应，
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



	// 使用Produces和Consumes的@RequestMapping:
	@RequestMapping(value="/method6", produces={"application/json","application/xml"}, consumes="text/html")
	@ResponseBody
	public String method6(){
		return "method6";
	}




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
	// 我们可以使用@RequestMapping和@RequestParam注释来检索URL参数并将其映射到 “方法参数”。

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
