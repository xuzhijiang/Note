## Spring Boot Web应用数据校验

在实际开发中，为了保证数据的安全，必须对用户提交上来的数据进行有效性检测。

> 数据有效性检测分为两类

1. 一类是在客户端进行的，常用的Web前端框架(比如Vue和React）都提供了相应的功能，在提交数据之前对数据的有效性进行检测;
2. 另一类则是服务端数据检测，就是在调用Spring Boot
MVC控制器方法之前对客户端数据的有效性进行校验。

> 本讲介绍的是如何使用Spring Boot MVC框架在服务端完成数据有效性检测的工作。

### Spring MVC数据检验的基本步骤(示例datavalidator_demo)

1. 给数据实体类附加注解
2. 在调用控制器方法之前检测数据是否有效
3. 数据有效，正常执行，无效，走另外流程(比如提示用户修正后重新提交）

> 示例: datavalidator_demo

## 校验示例讲解

我们写了一个插入用户的方法:

```java
@PostMapping(value = "/users")
public User addUser(@RequestParam("userName")String userName,@RequestParam("age")Integer age){
    User user = new User();
    user.setUserName(userName);
    user.setAge(15);
    return userRepository.save(user);
}
```

从上述代码中，我们可以看出随着用户的属性增多时，代码量就会变得很多，我们可以做如下的修改：

```java
@PostMapping(value = "/users")
public User addUser(User user){
    user.setUserName(user.getUserName());
    user.setAge(user.getAge());
    return userRepository.save(user);
}
```

当我们进行表单验证时，使用注解的方式在实体类中，例如年龄必须大于18岁，如下：

```java
@Min(value = 18,message = "未成年人禁止注册")
private Integer age;
```

控制类中的方法修改如下:

```java
@PostMapping(value = "/users")
public User addUser(@Valid User user, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        System.out.print(bindingResult.getFieldError().getDefaultMessage());
        return null;
    }
    user.setUserName(user.getUserName());
    user.setAge(user.getAge());
    return userRepository.save(user);
}
```

说明：

1. 使用了@Min(value = 18,message = "未成年人禁止注册")进行表单字段校验
2. 使用@Valid注解表示校验user这个对象
3. 校验结果存在在BindingResult bindingResult这个对象中

####SpringBoot采用的是Hibernate-validator校验规则，常用的校验规则如下所示：

1. @AssertTrue 用于Boolean字段，该字段只能为true
2. @AssertFalse 用于Boolean字段，该字段只能为false
3. @CreditCardNumber 对信用卡进行一个大致的验证
4. @DecimalMax("5") 只能小于或等于该值
5. @DecimalMin("48")只能大于和等于该值
6. @Digits(integer = 2,fraction = 20) 检查是否是一种数字的整数，分数，小数位数的数字
7. @Email 对邮箱进行校验
8. @Future 检查该字段的日期是否是一个将来的日期
9. @Length(min = 12,max = 45) 检查所属字段的长度是否在min和max之间，只能用于字符串
10. @Max(value = 15) 该字段的值只能小于或者等于该值
11. @Min(value = 1) 该字段的值只能大于或者等于该值
12. @NotNull 不能为null 
13. @NotBlank 不能为空，检查的时候会将空格忽略
14. @NotEmpty 不能为空，这里指的是空字符串
15. @Null 检查该字段是否为空
16. @Past 检查该字段的是日期在过去
17. @Size(min = 12,max = 51) 检查该字段的size是否在min和max之间，包括字符串，数组，集合，Map等
18. @URL(protocol = "1",host = "",port = 51) 检查是否是一个有效的URL
19. @Valid 该注解只能用于字段为一个包含其他对象的集合或map或数组的字段。

### 小结

1. 本讲介绍了Spring Boot Web项目中如何在服务端实现数据校验的基本方法，适用于开发“前后端分离”的现代Web应用。
2. 本讲示例未涉及如何在Spring Boot MVC项目的视图上显示数据校验信息，其实在掌握了Spring Boot MVC视图编程技术(比如Thymeleaf）之后，这块是很容易补上的，只需将数据校验信息直接发给视图引擎即可。