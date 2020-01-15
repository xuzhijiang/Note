# 工程结构(最佳实践）

    Spring Boot框架本身并没有对工程结构有特别的要求，但是按照最佳实践的工程结构可以帮助我们减少可能会遇见的坑，
    减少工作量,尤其是Spring包扫描机制的存在.

    每一个类都应在一个包下面，而不应该直接放在classes目录下，因为这可能会导致一些注解，
    例如@ComponentScan, @EntityScan or @SpringBootApplication失效。
    对于@EnableAutoConfiguration注解，官方建议将其放在我们项目的root package下面.

# 典型示例

* root package结构：com.example.myproject
* 我们一般会在root package下面放置启动类Application.java(通常命名为Application.java)，通常我们会在启动类中做一些框架配置扫描等配置，我们放在root package下可以帮助程序减少手工配置来加载到我们希望被Spring加载的内容.
* 实体(Entity）与数据访问层(Repository）置于com.example.myproject.domain包下
* 逻辑层(Service）置于com.example.myproject.service包下
* Web层(web）置于com.example.myproject.web包下

```shell script
.
└── com
    └── example
        └── myproject
            ├── Application.java
            ├── config
            ├── dao
            │   └── PersonDao.java
            ├── domain
            │   └── Person.java
            ├── service
            │   ├── impl
            │   │   └── PersonServiceImpl.java
            │   └── PersonService.java
            └── web
                ├── controller
                │   └── PersonController.java
                ├── filter
                ├── listener
                └── servlet
```
