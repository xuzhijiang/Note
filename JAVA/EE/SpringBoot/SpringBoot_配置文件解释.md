```properties
jpa:
  hibernate:
    ddl-auto: create  
    # create表示每次都会自动删除原先存在的表，就是说如果表中存在数据，运
    # 行程序数据就不存在了。也可以是 update：会创建表，如果表中有数据，不会删除表
    # ，其中表名就是实体类的名字，表字段就是实体类的对应字段.
  show-sql: true
```

springboot项目中yaml中可以定义多个profile，也可以指定激活的profile：

```yml
spring:
  profiles.active: dev

---
spring:
  profiles: dev
myconfig:
  config1: dev-enviroment

---
spring:
  profiles: test
myconfig:
  config1: test-enviroment

---
spring:
  profiles: prod
myconfig:
  config1: prod-envioment
```


也可以在运行的执行指定profile：

`java -Dspring.profiles.active="prod" -jar yourjar.jar`

还可以使用Profile注解，MyConfig只会在prod这个profile下才会生效，其他profile不会生效

```java
@Profile("prod")
@Component
public class MyConfig {
    ....
}
```

