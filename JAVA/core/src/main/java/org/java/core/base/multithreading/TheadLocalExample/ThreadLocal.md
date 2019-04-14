### synchronized和ThreadLocal的区别

ThreadLocal用于创建线程局部变量,我们知道对象的所有线程都共享它的变量，因此该变量不是线程安全的。
我们可以使用synchronization来实现线程安全，但是如果我们想避免synchronization，我们可以使用ThreadLocal变量。

>使用synchronized的话，表示当前只有1个线程才能访问方法，其他线程都会被阻塞。当访问的线程也阻塞的时候，其他所有访问该方法的线程全部都会阻塞，这个方法相当地 “耗时”。使用ThreadLocal的话，表示每个线程的本地变量中都有SimpleDateFormat这个实例的引用，也就是各个线程之间完全没有关系，也就不存在同步问题了。综合来说：使用synchronized是一种 “以时间换空间”的概念， 而使用ThreadLocal则是 “以空间换时间”的概念。

```java
Thread线程类内部有个ThreadLocal.ThreadLocalMap类型的属性：

/* ThreadLocal values pertaining to this thread. This map is maintained
 * by the ThreadLocal class. */
ThreadLocal.ThreadLocalMap threadLocals = null;
```

```java
// ThreadLocal应用广泛，下面介绍下在SpringMVC中的应用。

// NamedThreadLocal只是1个带name属性的ThreadLocal
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private final String name;

    public NamedThreadLocal(String name) {
        Assert.hasText(name, "Name must not be empty");
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

// SpringMVC中RequestContextHolder内部结构:该类会暴露与线程绑定的RequestAttributes对象
private static final ThreadLocal<RequestAttributes> requestAttributesHolder =
        new NamedThreadLocal<RequestAttributes>("Request attributes");

// InheritableThreadLocal类是ThreadLocal的子类。为了解决ThreadLocal实例内部每个线程都只能看到自己的私有值，所以InheritableThreadLocal允许一个线程创建的所有子线程访问其父线程的值
private static final ThreadLocal<RequestAttributes> inheritableRequestAttributesHolder =
        new NamedInheritableThreadLocal<RequestAttributes>("Request context");

// 继续看下RequestContextHolder的getRequestAttributes方法，其中接口RequestAttributes是对请求request的封装：
public static RequestAttributes getRequestAttributes() {
    // 直接从ThreadLocalContext拿当前线程的RequestAttributes
    RequestAttributes attributes = requestAttributesHolder.get();
    if (attributes == null) {
        // 获得它父线程中的RequestAttributes值
        attributes = inheritableRequestAttributesHolder.get();
    }
    return attributes;
}

ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
HttpServletRequest request = requestAttributes.getRequest();
```