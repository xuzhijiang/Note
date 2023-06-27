# spring aop什么时候使用jdk代理,什么时候使用cglib?在可选的情况下,如何进行指定?

![](../pics/aop使用的哪个代理.png)

![](../pics/选择aop的动态代理方式01.png)

![](../pics/选择aop的动态代理方式02.png)

# Spring AOP的实现原理?

AOP的实现，最关键的有两步：

1. 得到代理对象
2. 利用递归责任链执行前置以及后置的advice(通知)及目标方法

假设代理对象proxy调用了某个方法，而这个方法会触发CglibAopProxy.intercept()。先不要理会为啥会触发这个方法，反正人家就是这样设定的。我们来看看intercept()方法：

![](../pics/v2-b1b48f15070ae90b770e3ba5e100c2a9_hd.jpg)

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
