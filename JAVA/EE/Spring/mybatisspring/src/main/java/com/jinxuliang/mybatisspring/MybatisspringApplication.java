package com.jinxuliang.mybatisspring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 创建Spring boot的项目中引入MyBatis依赖(勾选MySQL和MyBatis)
@SpringBootApplication
// 启用Mapper扫描，如果不加这个注解，则需要为每个MyBatis Mapper接口添加@Mapper注解
@MapperScan("com.jinxuliang.mybatisspring.mapper")
public class MybatisspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisspringApplication.class, args);
	}

}

// 本讲介绍了MyBatis的基础编程模式，其实是很简单的，也就几步：

// 1. 定义Java方法与SQL命令的映射(Mapper）接口
// 2. 启用Mapper扫描功能
// 3. 将Mapper注入到需要访问数据库的地方。

// 记住了上面的小结，也就对MyBatis是干什么的，怎么用有了一个总体的印象，
// 余下的就是自己搜寻各种学习资源，阅读其官网的技术文档，了解其技术细节了。