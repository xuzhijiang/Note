### Spring MVC中最为常用的几个核心注解

介绍Spring MVC中最为常用的几个核心注解
：@Controller,@RestController,RequestMapping以及一些参数绑定的注解：@PathVariable,@ModelAttribute,@RequestParam:

* @Controller：修饰class，用来创建处理http请求的对象
* @RestController：Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody来配合，如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式。
* @RequestMapping：配置url映射
* @PathVariable:url中的参数可通过@PathVariable绑定到函数的参数中

@RequestMapping注释用于将请求URI映射到处理程序方法。
我们还可以指定客户端应用程序应该使用的HTTP方法来调用rest方法(to invoke the rest method)。

@ResponseBody注释用于映射response对象中的response体。
一旦handler method返回响应对象，MappingJackson2HttpMessageConverter
就会启动(kicks in)并将其转换为JSON响应。

@RequestBody注释用于将"请求主体JSON数据(request body JSON data)"映射到Employee对象，
这也是由MappingJackson2HttpMessageConverter映射完成的。
again this is done by the MappingJackson2HttpMessageConverter mapping.

@PathVariable注释是从rest URI中提取数据并将其映射到method argument的简单方法。

#### @JsonView的应用场景

1. 在实际开发中，有些数据实体类包容相当多的属性，但在特定的场景中，往往并不需要那么多的属性，只要几个就好了。
2. 通过在数据实体类和控制器中使用@JsonView注解，能够让我们针对特定的场景从数据实体类的属性集合中仅返回那些对于客户端应用来说“有用的”属性数据。
3. Spring将数据转换的结果统称为“View”，生成的Json数据也是“View”。
