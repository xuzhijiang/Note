### HelloWorld流程

>示例项目： mybatisspring

只要在创建Spring boot的项目中引入MyBatis依赖，一切就都就绪了！(勾选MySQL和MyBatis)

> 注意User表有4个字段，id，name，age，gender

### 定义MyBatis的映射接口

所谓“映射接口”，其实就是将Java中的方法与特定的SQL命令关联上，其含义就是——执行这个Java方法， MyBatis就会向数据库发出相应的SQL查询。

>测试：localhost:8080/showUser/5

### 小结

本讲介绍了MyBatis的基础编程模式，其实是很简单的，也就几步：

1. 定义Java方法与SQL命令的映射（Mapper）接口
2. 启用Mapper扫描功能
3. 将Mapper注入到需要访问数据库的地方。

记住了上面的小结，也就对MyBatis是干什么的，怎么用有了一个总体的印象，余下的就是自己搜寻各种学习资源，阅读其官网的技术文档，了解其技术细节了。