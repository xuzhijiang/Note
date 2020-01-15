# REST(Representational State Transfer)

    表现状态转换”,让访问的api看上去更优雅.(RESTful只是一种风格，并不是强制的标准)

# CRUD-员工列表

---
    1）、RestfulCRUD：CRUD满足Rest风格；
    URI：  /资源名称/资源标识       HTTP请求方式区分对资源CRUD操作

    普通CRUD和RestfulCRUD的区别:
    a. 普通CRUD通过uri来区分操作
    b. 用http请求的方法来区分

|      | 普通CRUD（通过uri来区分操作） | RestfulCRUD       |
| ---- | ------------------------- | ----------------- |
| 查询 | getEmp                    | emp---GET         |
| 添加 | addEmp?xxx                | emp---POST        |
| 修改 | updateEmp?id=xxx&xxx=xx   | emp/{id}---PUT    |
| 删除 | deleteEmp?id=1            | emp/{id}---DELETE |

    或者

    功能 传统的Web API 					RESTful API 		
    查询 /user/query?name=tom    		/user?name=tom 	
    详情 /user/getInfo?id=1 		 	/user/1 			
    创建 /user/create?name=tom   		/user 		
    修改 /user/update?id=1&name=jerry	/user/1 			
    删除 /user/delete?id=1 			   /user/1 	
    
---
    
    2）、请求架构;

| 功能                             | 请求URI | 请求方式 |
| ------------------------------------ | ------- | -------- |
| 查询所有员工                         | emps    | GET      |
| 查询某个员工(来到修改页面)           | emp/1   | GET      |
| 来到添加页面                         | emp     | GET      |
| 添加员工                             | emp     | POST     |
| 来到修改页面（查出员工进行信息回显） | emp/1   | GET      |
| 修改员工                             | emp     | PUT      |
| 删除员工                             | emp/1   | DELETE   |

# restful中参数的上传

    在REST风格的设计中，如果是简单的参数，往往会通过URL直接传递，在Spring MVC可以使用注解@PathVariable进行获取，
    这样就能够满足REST风格传递参数的要求.

    对于那些复杂的参数，例如，你需要传递一个复杂的资源需要十几个甚至几十个字段，
    通常会使用Json字符串来封装它们，然后将它放到HTTP请求的Body中，以POST方式提交给服务器。
    这样Server端可以使用注解@RequestBody将Json数据转换为Java对象。

# restful的请求方式

    可以通过 GET、 POST、 PUT、 PATCH、 DELETE 等方式对服务端的资源进行操作。其中：

    GET：用于查询资源
    POST：用于创建资源
    PUT：用于更新服务端的资源的全部信息(全部字段)
    PATCH：用于更新服务端的资源的部分信息(部分字段)
    DELETE：用于删除服务端的资源。

    【GET】          /users                # 查询用户信息列表
    【GET】          /users/1001           # 查看某个用户信息
    【POST】         /users                # 新建用户信息
    【PUT】          /users/1001           # 更新用户信息
    【PATCH】        /users/1001           # 更新用户信息
    【DELETE】       /users/1001           # 删除用户信息

# restful命名风格

![](../pics/restful01.png)
![](../pics/restful02.png)
![](../pics/restful03.png)
![](../pics/restful04.png)
![](../pics/restful05.png)
![](../pics/restful06.png)
![](../pics/restful07.png)
![](../pics/restful08.png)
![](../pics/restful09.png)
![](../pics/restful10.png)
![](../pics/restful11.png)
![](../pics/api文档设计.png)
