# @ConfigurationProperties的使用

    将配置文件中的属性和值注入到bean实体类中，我们可以使@ConfigurationProperties注解

~~~yaml
person:
  name: "张三\n李四"
  age: 14
  map: {k1: V1,k2: V2}
  list:
    - cat
    - dog
    - pig
  cat:
    name: '小猫\n小狗'
    age: 4
~~~

```java
@Component
// prefix = "person"表示获取yaml中前缀为person的值
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private int age;

    private Map<String,Object> map = new HashMap<>();

    private List<String> list = new ArrayList<>();

    private Cat cat;
}
```
