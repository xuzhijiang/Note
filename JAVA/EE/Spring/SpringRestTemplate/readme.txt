Spring RestTemplate提供了一种测试RESTful Web Service的便捷方式。

Spring RestTemplate

1. Spring RestTemplate class是spring-web的一部分，在Spring 3中引入。
2. 我们可以使用RestTemplate来测试基于HTTP的restful Web service，它不支持HTTPS协议。
3. RestTemplate类为不同的HTTP methods 提供overloaded methods，例如GET，POST，PUT，DELETE等。


我们将测试在Spring Data JPA文章中创建的REST Web服务。 下表说明了此休息Web服务支持的URI。

URI HTTP方法描述
/ springData / person GET从数据库中获取所有人员
/ springData / person / {id} GET按ID获取人物
/ springData / person POST将人员添加到数据库
/ springData / person PUT更新人
/ springData / person / {id} DELETE按ID删除人员
让我们开始创建我们的Rest客户端项目来测试这些Web服务

