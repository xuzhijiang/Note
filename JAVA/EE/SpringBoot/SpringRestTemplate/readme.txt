Spring RestTemplate

1. Spring RestTemplate class是spring-web的一部分，在Spring 3中引入。
2. 我们可以使用RestTemplate来测试基于HTTP的restful Web service，它不支持HTTPS协议。
3. RestTemplate类为不同的HTTP methods 提供overloaded methods，例如GET，POST，PUT，DELETE等。

URI 						HTTP方法				DESCRIPTION(描述)
/springData/person 			GET					Get all persons from database
/springData/person/{id} 	GET					Get person by id
/springData/person 			POST				Add person to database
/springData/person 			PUT					Update person
/springData/person/{id} 	DELETE				Delete person by id
