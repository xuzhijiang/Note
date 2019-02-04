## Spring Boot 开发 RESTful Service

### REST概述

在互联网发展的早期阶段，浏览器是最主要的上网工具，相应地，Web应用主要考虑到浏览器的需求。只要它能够在浏览器中正确显示，一切就OK。

由于在很长的一段时间内，浏览器的功能受限，它仅能承担网页渲染和一些简单脚本的执行工作，因此，WebServer需要负责生成所有HTML文档和提供数据资源的任务。

在这个时代，占主流的是各种MVC框架， Web Server负责生成好HTML代码之后，再发回给浏览器显示。

然而,2010年以后，以手机为代表的智能设备异军突起，很快地，手机引发的互联网流量就超过了PC端引发的。

手机App的界面有多种构建方式， HTML仅是其中的一种，手机App的用户使用体验，与浏览器中的网页通常很不一样。

为了能同时支持手机和PC，传统互联网进化为了“移动互联网”，相应地,Web Server端应用的职责也发生了大的变化，Web Server慢慢地不再负责“组装”或“生成”用户界面，而将这个工作“下发”给客户端App负责。

### 时代的变迁引发技术的变革

    前端				<---------------->RESTful Service   	后端
客户端应用 									             Server端应用

#### MPA vs SPA

1. Multipage Application(MPA)------Spring MVC
2. Single Page Application(SPA)----Web前端框架 + RESTful Service


### REST的由来

REST这个词，是Roy Thomas Fielding在他2000年的博士论文中提出的。 Fielding博士是HTTP协议（1.0版和1.1版）的主要设计者、Apache服务器软件的作者之一、 Apache基金会的第一任主席。所以，他的这篇论文一经发表，就引起了业界关注，并
且对互联网开发产生了深远的影响。

Fielding将他对互联网软件的架构原则，命名为“REST（Representational State Transfer）”。

如果一个架构符合REST原则，就称它为“REST风格”架构。

#### REST的三个关键术语

##### 资源

* 互联网被看成是一个Web资源的集合资源
* 每个资源对应一个特定的URI。

##### 资源的表达

* 通常使用Json来标准化客户端访问资源的信息交换过程

##### 状态转换

* 资源可以创建（create）、访问（visit）、修改（update）和删除（delete）。
* 状态转换过程基于HTTP协议所定义的method实现

### HTTP method定义了若干种资源访问方式

HTTP method 			说明
GET（VISIT） 	访问服务器资源（一个或者多个资源）。
POST（CREATE） 	提交服务器资源信息，用来创建新的资源。
PUT（UPDATE） 	修改服务器已经存在的资源，使用PUT时需要
把资源的所有属性一并提交。
PATCH（UPDATE）  修改服务器已经存在的资源，使用PATCH时只
需要将部分资源属性提交。
DELETE（DELETE） 从服务器将资源删除。
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
* 使用HTTP方法描述数据存取行为（即CRUD），使用HTTP状态码来表示不同的处理结果。
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

* @Controller(标明本类可以被IoC容器所识别)
* 标明本类方法的返回值，直接写入到HTTP响应的body中。

### Spring Boot提供的RESTful Service可用注解

1. @GetMapping: 对应HTTP的GET请求，获取资源.
2. @PostMapping: 对应HTTP的POST请求，创建资源
3. @PutMapping: 对应HTTP的PUT请求，提交所有资源属性以及修改资源
4. @PatchMapping: 对应HTTP的PATCH请求，提交资源部分修改的属性.
5. @DeleteMapping: 对应HTTP的DELETE请求，删除服务器端的资源

> rest_demo示例项目解析

access: localhost:8080/myservice/hello

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

### 学习指导

本讲介绍了Spring Boot开发RESTful Service的基本编程技巧，仅仅只是一个入门的介绍，同学们需要在PPT所介绍的基础之上，进一步学习相关的技术。RESTful Service在现代的Web应用中得到了广泛的应用，是一个需要重点掌握的内容。