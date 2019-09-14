# REST的由来

REST这个词，是国外的一个博士论文中提出的,“REST(Representational State Transfer）-表现状态转换”。

REST比较抽象,简单的说就是一种软件的架构风格,让访问的api看上去更优雅.

### HTTP method定义了若干种资源访问方式

HTTP method 			说明
GET(VISIT） 	访问服务器资源(一个或者多个资源）。
POST(CREATE） 	提交服务器资源信息，用来创建新的资源。
PUT(UPDATE） 	修改服务器已经存在的资源，使用PUT时需要
把资源的所有属性一并提交。
PATCH(UPDATE）  修改服务器已经存在的资源，使用PATCH时只
需要将部分资源属性提交。
DELETE(DELETE） 从服务器将资源删除。
HEAD 			获取资源的元数据。
OPTIONS   		提供资源可供客户端修改的属性信息。

### RESTful API设计

> 区分传统的Web API和RESTful API。

功能 传统的Web API 					verb  RESTful API 		verb
查询 /user/query?name=tom    		GET   /user?name=tom 	GET
详情 /user/getInfo?id=1 		 		GET   /user/1 			GET
创建 /user/create?name=tom   		POST  /user 			POST
修改 /user/update?id=1&name=jerry	POST  /user/1 			PUT
删除 /user/delete?id=1 			    GET   /user/1 			DELETE

为Web应用设计RESTful API，是项目初期的一项重要工作，它是实现前后端协同工作的关键。

### 小结： RESTful Service的特点

* 使用URL来标识可访问的Web资源。
* 使用HTTP方法描述数据存取行为(即CRUD），使用HTTP状态码来表示不同的处理结果。
* 客户端与Web Server之间使用json作为数据交换标准。
* RESTful只是一种风格，并不是强制的标准。

### Richardson Maturity Model

> https://martinfowler.com/articles/richardsonMaturityModel.html

1. ⓪ 使用HTTP作为传输方式
2. ①引入资源的概念，每个资源都有对应的URL
3. ②使用HTTP方法进行不同的操作，使用HTTP状态码来表示不同的结果
4. ③使用超媒体，在资源的表达中包容链接信息

## Spring Boot开发RESTful服务

最重要的注解： @RestController

> @RestController = @Controller + @ResponseBody

* @Controller: 标明本类可以被IoC容器所识别
* @ResponseBody: 标明本类方法的返回值，直接写入到HTTP响应body中。

### Spring Boot提供的RESTful Service可用注解

1. @GetMapping: 对应HTTP的GET请求，获取资源.
2. @PostMapping: 对应HTTP的POST请求，创建资源
3. @PutMapping: 对应HTTP的PUT请求，提交所有资源属性以及修改资源
4. @PatchMapping: 对应HTTP的PATCH请求，提交资源部分修改的属性.
5. @DeleteMapping: 对应HTTP的DELETE请求，删除服务器端的资源

### 分页功能的实现

指定分页默认参数值:

```java
@PostMapping("/orders/search")
public Page<SearchOrderOut> getOrders(@RequestBody @Valid Search search,
	@PageableDefault(
		sort = {"modifiedDate", "createdDate"},
		direction = Sort.Direction.DESC
		) Pageable pageable){
	return preOrderService.getOrders(search, pageable);
}
```

### 小结： Client-->Server间信息的传送方式

在REST风格的设计中，如果是简单的参数，往往会通过URL直接传递，在Spring MVC可以使用注解@PathVariable进行获取，这样就能够满足REST风格传递参数的要求。

对于那些复杂的参数，例如，你需要传递一个复杂的资源需要十几个甚至几十个字段，通常会使用Json字符串来封装它们，然后将它放到HTTP请求的Body中，以POST方式提交给服务器。这样Server端可以使用注解@RequestBody将Json数据转换为Java对象。