package com.jinxuliang.datavalidator_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 使用Postman进行测试，提交的数据有效时，控制器方法将返回一个有效的ValidatorPojo对象。
* 非法数据,SpringMVC给出的校验结果

```
// 提交的数据有效时
POST: localhost:8080/datavalidate/post

JSON(application/json)
{
	"id": 100,
	"date": "2019-12-19T03:28:45.449+0000",
	"doubleValue":500.0,
	"integer":34,
	"range":888,
	"email":"xuzhijing@bit.edu.cn",
	"size":"String length must be [20 30]."
}

```

```
// 提交非法数据
POST: localhost:8080/datavalidate/post

JSON(application/json)
{
	"id": 100,
	"date": "2019-12-19T03:28:45.449+0000",
	"doubleValue":1500.0,
	"integer":34,
	"range":888,
	"email":"xuzhijing@bit.edu.",
	"size":"很短的字符串."
}

```
* */
@SpringBootApplication
public class DatavalidatorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatavalidatorDemoApplication.class, args);
    }
}
