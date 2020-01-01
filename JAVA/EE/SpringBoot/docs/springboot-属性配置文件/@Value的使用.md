# @Value的使用

    在application.properties中定义一些自己使用的属性，然后在类中通过@Value("${属性名}")注解来加载对应的配置属性

```java
//要注意的是，@Value注解是在Bean实例化之后再进行注入的，也就是说,当我们在构造方法中使用这个属性的值时，还是为null。等到实例化之后，这个属性就会被设置
public class Application {
    @Value("${name}")
    private String name;
}
```
