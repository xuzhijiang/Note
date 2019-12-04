# Spring AOP的实现原理?

AOP的实现，最关键的有两步：

1. 得到代理对象
2. 利用递归责任链执行前置以及后置的advice(通知)及目标方法

IOC容器初始化时，对于涉及AOP操作的目标类，其实Spring返回的是代理对象，而不是目标类的实例。

代理对象proxy其实包含了很多东西，比如：

- 目标对象
- 增强器

代理对象最终都会间接调用目标对象的同名方法，比如proxy.add() --> target.add(),但代理对象允许在调用add()前后添加一些增强代码，作为功能扩展
即，调用代理对象的方法最终都会“转嫁”成调用目标方法，但是在调用前后会执行一些其他操作，我称这些其他操作为“增强代码”，本质上就是上面提到的增强器。

>代理对象方法 = 拦截器链 + 目标对象方法

假设代理对象proxy调用了某个方法，而这个方法会触发CglibAopProxy.intercept()。先不要理会为啥会触发这个方法，反正人家就是这样设定的。我们来看看intercept()方法：

![](pics/v2-b1b48f15070ae90b770e3ba5e100c2a9_hd.jpg)

intercept()如果没有拦截器链,直接执行目标方法,如果有拦截器链,就传入拦截器链和目标对象，最终new CglibMethodInvocation(...).proceed(),我们主要考虑有拦截器链的情况。

intercept()说穿了，就干了两件事：收集拦截器，做成链,然后把拦截器链和目标对象等传入，执行new CglibMethodInvocation(...).proceed()

>new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();

这里new了一个CglibMethodInvocation对象，包括目标对象、拦截器链啥的。

![](https://pic3.zhimg.com/80/v2-9ab467470a3b663abff9fc83d9d757e8_hd.jpg)

简化后的示意图：

![](https://pic3.zhimg.com/80/v2-ed1796a21b678af0f6db065ab0444ac0_hd.jpg)

也就是说，只要拦截器链没执行完，就不会执行目标方法。即：

- 先执行全部的拦截器
- 最后执行目标方法

我知道你看到这里，会有什么疑问：既然拦截器都在目标方法前执行，怎么会出现AOP的后置调用呢?

```java
before... 
target.add() 
afterReturning... ...
```

别急，我们看看拦截器的invoke()干了啥。

刚才说过了，拦截器是对增强的包装。我们增强方法有哪些来着？Before/AfterReturning...等等，所以拦截器肯定也有对应的Before/AfterReturning...

我们先看Before拦截器：

![](https://pic3.zhimg.com/80/v2-107b1daa76d57feddb3c69f1eff15991_hd.jpg)

- 先调用了this.advice.before()。即，反射执行@Before方法。
- 再调用了mi.proceed()。这个mi，就是我上一个截图传入的this，也就是CglibMethodInvocation对象。

再看After拦截器：

![](https://pic4.zhimg.com/80/v2-143b5411efc29f30d41eb923265cb19f_hd.jpg)

- 先调用mi.proceed()
- 后反射调用@After方法

先别管具体上下文环境以及方法含义，我就问你，单纯看语句调用顺序，Before拦截器和After拦截器有何不同？

答案是：

- Before拦截器是先反射调用@Before，再调用mi.proceed()。
- 而After拦截器是先调用mi.proceed()，再反射调用@AfterRetruning方法。(因为finally是目标对象的方法返回之后才带调用,这里就是aop的精华)

>其实，只有Before是特殊的，其他拦截器都是先调用proceed()，再反射调用通知方法。

AOP递归责任链：

![](https://pic3.zhimg.com/80/v2-d5158657084b9a6f0f3b3587a2a1e8da_hd.jpg)

跟着调用栈，可以看到顺序是before---target.add()---after...,流程解读：

1. proxy.add()触发CglibAopProxy.intercept()
2. intercept()中获取所有的拦截器，排好序后做出拦截器链（顺序和AOP执行顺序相反，before反而放链的末尾！）
,传入拦截器链和目标对象，new CglibMethodInvocation()并调用proceed()
3. proceed()先执行全部拦截器，最后执行目标方法
4. 目标方法的return是整个递归责任链的精华所在

## Spring Boot中使用AOP

### 引入AOP依赖

在Spring Boot中引入AOP就跟引入其他模块一样，非常简单，只需要在pom.xml中加入如下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

在完成了引入AOP依赖包后，一般来说并不需要去做其他配置。也许在Spring中使用过注解配置方式的人会问是否需要在程序主类中增加@EnableAspectJAutoProxy来启用，实际并不需要。

可以看下面关于AOP的默认配置属性，其中spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，默认已经增加了@EnableAspectJAutoProxy。

```
# AOP
spring.aop.auto=true # Add @EnableAspectJAutoProxy.
spring.aop.proxy-target-class=false # Whether subclass-based (CGLIB) proxies are to be created (true) as
 opposed to standard Java interface-based proxies (false).
```

>而当我们需要使用CGLIB来实现AOP的时候，需要配置spring.aop.proxy-target-class=true，不然默认使用的是标准Java的实现。

### 优化：AOP切面的优先级

由于通过AOP实现，程序得到了很好的解耦，但是也会带来一些问题，比如：我们可能会对Web层做多个切面，校验用户，校验头信息等等，这个时候经常会碰到切面的处理顺序问题。

所以，我们需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高。假设我们还有一个切面是CheckNameAspect用来校验name必须为xxx，我们为其设置@Order(10)，而上文中WebLogAspect设置为@Order(5)，所以WebLogAspect有更高的优先级，这个时候执行顺序是这样的：

- 在@Before中优先执行@Order(5)的内容，再执行@Order(10)的内容
- 在@After和@AfterReturning中优先执行@Order(10)的内容，再执行@Order(5)的内容

>所以我们可以这样子总结：

* 在切入点前的操作，按order的值由小到大执行
* 在切入点后的操作，按order的值由大到小执行

# 参考:

- [https://www.zhihu.com/question/23641679/answer/704897152](https://www.zhihu.com/question/23641679/answer/704897152)