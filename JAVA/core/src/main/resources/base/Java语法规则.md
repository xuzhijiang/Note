# 访问权限

Java 中的访问权限修饰符：private、不加访问修饰符(表示包级可见)，protected 以及 public.

可见性： private < friendly < protected < public

# 接口

从 Java 8 开始，接口也可以拥有默认的方法实现，这是因为不支持默认方法的接口的维护成本太高了。在 Java 8 之前，如果一个接口想要添加新的方法，那么要修改所有实现了该接口的类。

接口的成员（字段 + 方法）默认都是 public 的，并且不允许定义为 private 或者 protected。

接口的字段默认都是 static 和 final 的

# 接口和抽象类的区别是什么？

- 接口的方法默认是 public，所有方法在接口中不能有实现(Java 8 开始接口方法可以有默认实现），抽象类可以有非抽象的方法。
- 接口中的字段默认是 final和static 的，而抽象类中则不一定
- 接口中的 方法/变量 是public的

# 异常

Throwable 是异常的顶级类，分为两种： **java.lang.Error(错误)**  和 **java.lang.Exception(异常)**。

![](../pics/异常分类.png)

- Error 用来表示 JVM 无法处理的错误,表示运行应用程序中较严重问题。例如当内存不够时，将出现 OutOfMemoryError。这些异常发生时，Java虚拟机（JVM）一般会选择线程终止,这些错误表示故障发生于虚拟机自身、或者发生在虚拟机试图执行应用时.
- Exception（异常）:是程序本身可以处理的异常,常见异常有RuntimeException，NullPointerException，ArithmeticException，ArrayIndexOutOfBoundsException。 

>异常和错误的区别：异常能被程序本身可以处理，错误是无法处理

```java
// Throwable类常用方法
// getMessage(): 返回异常发生时的详细信息
// printStackTrace(): 在控制台上打印Throwable对象封装的异常信息
```

# final

>final修饰变量的时候：
 
- 如果修饰的是int等基本类型变量，例如int a = 10；给这个变量赋值一次之后，就不能再修改这个变量的值了
- 如果修饰的是引用类型的变量，比如修饰的变量是Person的一个实例，那么给这个变量赋值一次之后，不可以再指向其他新的Person实例，但是这个变量所引用的person的属性是可以修改的。

# Java 面向对象编程三大特性: 封装 继承 多态

- 封装: 把一个对象的属性私有化，同时提供一些可以被外界访问的属性的方法
- 继承: 通过继承，子类可以用父类的功能，通过继承能够方便地复用以前的代码
- 多态: animal.run(); cat.run(); bird.run(); 调用run方法的时候，传的参数是Animal,`run (Animal animal)`

# 构造器 Constructor 是否可被 override?
 
继承的时候我们就知道父类的私有属性和构造方法并不能被继承，所以 Constructor 也就不能被 override

# 在 Java 中定义没有参数的构造方法的作用

- 因为Java是一门严谨的语言，要构造孙子，先要构造父亲，要构造父亲，先要构造爷爷。
- 每一级在自己构造之前，必须先调用super让上一级先构造成功。
- 注意是必须加super，而且第一行必须是super，有时候好像我们自己没有加super，那是因为编译器自动给你加的，那说明也是必须加的

# Java序列化中如果有些字段不想进行序列化，怎么办？

对于不想进行序列化的变量，使用transient关键字修饰。transient只能修饰变量，不能修饰类和方法

# Java 与 C++ 的区别

- Java 是纯粹的面向对象语言，所有的对象都继承自 java.lang.Object，C++ 为了兼容 C 即支持面向对象也支持面向过程。
- Java 支持自动垃圾回收，而 C++ 需要手动回收。
- Java 不支持多重继承，只能通过实现多个接口来达到相同目的，而 C++ 支持多重继承。
- Java 不支持条件编译，C++ 通过 #ifdef #ifndef 等预处理命令从而实现条件编译。

# == 与 equals区别

基本数据类型==比较的是值，引用数据类型==比较的是内存地址
 
**equals()** : 它的作用也是判断两个对象是否相等。有两种使用情况：

- 一个自定义类没有覆盖 equals() 方法。当使用 equals() 比较两个对象时，等价于通过“==”比较这两个对象,也就是使用父类Object中的equals方法，比较的是地址。
- 如果类覆盖了 equals() 方法。用来来判断两个对象的内容是否相等；若它们的内容相等，则返回 true 。例如`java.lang.String`.

>两个Integer比较，是引用比较，永远用equals.两个int比较，只能用==,一个是Integer，一个是int，不想让编译器帮你自动转的话，I.intValue() == i

# 为什么要有 hashCode

我们以“HashSet 如何检查重复”为例子来说明为什么要有hashCode。

#  你重写过 hashcode 和 equals 么，为什么重写equals时必须重写hashCode方法？

我以HashSet中存放Student为例，Student有2个成员变量，name和age，如果重写了equals方法，new Student('a', 20)和new Student('a',20)这两个对象equals后，返回true，但是如果我们不重写hashcode(),则2个对象的hashcode肯定不同，那么HashSet中就会存放重复的对象，这个和Set的是违背的。

```java
Student s1 = new Student('xzj', 18);
Student s2 = new Student('xzj', 20);
System.out.println(s1.hashCode()); // 1020371697
System.out.println(s2.hashCode()); // 789451787
HashSet set = new HashSet();
// 只重写equals，不重写hashcode，会导致重复
set.add(s1);
set.add(s2);
```

- 如果两个对象使用equals方法进行比较并且相等，那么在两个对象上调用hashCode就必须产生的结果
- 如果两个对象根据equals(Object)方法比较并不相等，则不要求在每个对象上调用hashCode都必须产生不同的结果。
- 因此，equals 方法被覆盖过，则 hashCode 方法也必须被覆盖

# 如何编写hashCode

```java
public int hashCode() {
    int result = 17;
    result = 31 * result + (name == null ? 0 : name.hashCode());
    result = 31 * result + (age == null ? 0 : age.hashCode());
    return result;
}
```

- 把某个非零的常数值，比如17，保存在变量int result中
- 然后累加每个成员变量的hashcode.

# 线程有哪些状态

- NEW
- RUNNABLE
- BLOCKED
- WAITING
- TIMED_WAITING
- TERMINATED

# 成员变量与局部变量区别     

    3. 默认值不同
        成员变量,有自己的默认值
        局部变量,没有默认值,不赋值不能使用

    4. 内存位置不同
        成员变量,跟随对象进入堆内存存储
        局部变量,跟随自己的方法,进入栈内存

    5. 生命周期不同
        成员变量,跟随对象,在堆中存储,内存等待JVM清理  生命相对较长
        局部变量,跟随方法,方法出栈,就消失                生命相对较短

this：
    本类构造方法调用本类其他构造          本类构造方法第一行this(参数)
    
super：
    访问本类对象当中的父类对象成员变量       super.变量名
    调用本类对象当中的父类普通方法         super.方法名()
    本类构造方法调用父类构造            本类构造方法第一行super(参数)

    this与super在调用构造方法时，均必须在第一行，只能调用其中的一个。
    父类多个构造，子类调用父类某个参数的构造时，必须保证父类有这个构造，否则报错

    



包的访问：(前提 类用public修饰)
    同一个包下，随意访问
    不同包下：
        可以直接使用全名使用
            创建对象格式：包名.类名 变量名 = new包名.类名();
        为了方便，可以选择导包后，再直接使用类名本身，不加包名  在package后，class前使用import导入类
        如果是lang包下的类，可以不导包，直接使用

代码块：一块执行代码的区域用{}包裹
        局部代码块：定义在方法中的，用来限制变量的作用范围
        构造代码块：定义在类中方法外，用来给对象中的成员初始化赋值
        静态代码块：定义在类中方法外，用来给类的静态成员初始化赋值
        静态代码块:不管创建几次对象,只执行一次
        构造代码块:new一次,就执行一次,优先于构造方法
        构造方法:new一次,就执行一次

        一个类默认是有一个无参的构造器，以防止程序猿不定义构造器，

但是如果人为的定义了一个构造器，系统默认的无参数的构造器就不起作用了，因为假设此类必须要通过构造器传入一些
必须的参数才可以使用其功能，要不然就不能使用此类，所以如果无参数的
构造器还起作用，那么使用其功能方法的时候就有问题.

类的成员变量，如果没有初始化，会有默认值:
boolean falg; //flag 默认false
String str; //str 默认为null

abstract class可以有非abstract 方法

接口interface也是可以继承接口interface的

switch语句可以使用的类型: int, enum

null instanceof String;//false

自己定义的那个AIDL接口所在的包的包名必须保持客户端和服务器端一致，

java转义字符：

 \0：空字符 

一个方法被定义为final，说明这个方法不可以被覆盖.

wireshark过滤http方法: http.request.method == "POST"

Java函数内部的变量必须要初始化才能使用，函数内部的局部变量是没有默认值的，
如果不初始化，编译就编译不过去，会提示错误.

Timer对调度的支持是基于绝对时间的，而不是相对时间，所以它对系统时间的改变非常敏感。
其次 Timer 线程是不会捕获异常的，如果 TimerTask 抛出的了未检查异常则会导致 Timer 线程终止，同时 Timer 也不会重新恢复线程的执行，他会错误的认为整个 Timer 线程都会取消。同时，已经被安排单尚未执行的 TimerTask 也不会再执行了，新的任务也不能被调度。故如果 TimerTask 抛出未检查的异常，Timer 将会产生无法预料的行为。

对于 Timer 的缺陷，我们可以考虑 ScheduledThreadPoolExecutor 来替代。
Timer 是基于绝对时间的，对系统时间比较敏感，而 ScheduledThreadPoolExecutor 则是基于相对时间；
Timer 是内部是单一线程，而 ScheduledThreadPoolExecutor 内部是个线程池，所以可以支持多个任务并发执行。

实现接口不允许使用@Override,

素数/质数

斐波那契数列

// 继承的这些接口是干啥的.
public class ArrayList<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{}

相关：StringBuffer与StringBuilder

mysql数据库优化，索引优化.

jvm调优.

CREATE TABLE `Users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(20) NOT NULL DEFAULT '',
  `country` varchar(20) DEFAULT 'USA',
  `password` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


Default Value of Data Types in Java :
Data Type    Default Value (for fields)
float           0.0f
double          0.0d
char            'u0000'
String (or any object)   null
int             0
Integer         null

0.78 默认是Double类型的，如果要指定它为Float类型的，就要加上f
Object o = 0.78; System.out.println(o instanceof Double);    // true
Object o = 0.7; System.out.println(o instanceof Float);    // false
注意instanceof后面的类型必须是对象类型，不能为primitive原始类型，否则编译不过去.

为什么Thread.stop被废弃.

java.util.UUID

线程通信的方式总结

### 4. finalize()

类似 C++ 的析构函数，用于关闭外部资源。但是 try-finally 等方式可以做得更好，并且该方法运行代价很高，不确定性大，无法保证各个对象的调用顺序，因此最好不要使用。

当一个对象可被回收时，如果需要执行该对象的 finalize() 方法，那么就有可能在该方法中让对象重新被引用，从而实现自救。自救只能进行一次，如果回收的对象之前调用了 finalize() 方法自救，后面回收时不会再调用该方法。

不可变对象是其状态在初始化后不会更改的实例。

String是一个不可变类，一旦实例化，其值永远不会改变。

不可变类有利于缓存目的，因为您不需要担心值的变化。
不可变类的其他好处是它本身就是线程安全的，所以在多线程环境下你不需要担心线程安全。

1. 将类声明为final，因此它就不可以被扩展。(Declare the class as final so it can't be extended.)
4. 使所有可变字段成为最终字段，以便它的值只能分配一次。(Make all mutable fields final so that it’s value can be assigned only once.)

XML是广泛使用的用于存储或传输数据技术，并且它与平台无关。 

Java提供各种API来读取，写入或操作XML数据。 

一些常用的java xml解析器是:

DOM Parser
SAX Parser
StAX Parser
JAXB

DOM Parser是最容易学习的java xml解析器。 
DOM解析器将XML文件加载到内存中，我们可以逐节点遍历它来解析XML。 
DOM Parser适用于小文件，但是当文件大小增加时，它执行速度慢并消耗更多内存。

Java8是的接口可以添加default方法和类方法，参见: java.lang.Iterable

java7中引入了Objects

java7引入了Integer.compare(),比较 2个int是否相等

对于接口interface,4个修饰符public,默认,private,protected,和类是一样的作用,限制使用范围,但是interface的方法默认都是public的,不用再主动声明public关键字.

private int age; // 默认值为0
private String username;// 默认值为null
private boolean isMale;//默认为false.

网站支持https.

Filter的FilterChain用于调用链中的下一个过滤器,这是责任链模式的一个很好的例子。

Servlet的Listener涉及到的是观察者模式(Observer)

类不能使用private,protected修饰.


值传递,引用传递.

使用synchronized的3种方式: 静态方法,实例方法,代码块.



锁的粒度,表锁,行锁,整个数据库的锁.

双重锁检查.

如何判断一个请求是来自电脑还是mobile.

门面模式(slf4j-logback)

别人问什么是spring mvc,直接讲mvc是什么即可,spring mvc是一个mvc框架的实现,当然struts也是mvc的实现,都属于mvc框架.

如果不用spring mvc,就要使用servlet,那么每处理一个请求,都要创建一个servlet,很大工作量,以及没法维护.
所以引入spring mvc,里面有一个核心组件,叫做DispatcherServlet,帮助我们分发请求(把servlet变成controller).
就不用写那么多sevlet,而且还根据业务逻辑,把请求分到不同的controller,便于维护.(面试要问如何实现,可以简单说下handlermapping到controller.)


拦截器,其实就是使用servelt的filter来实现的,为什么不用filter呢,为什么会出现拦截器这个东西呢,因为filter使用和维护比较麻烦,所以spring mvc为了解决filter的问题,引入了拦截器.

spring mvc依赖了哪些jar包啊?

spring-webmvc.(spring-webmvc已经依赖了spring-web,spring-aop,spring-beans,spring-context,spring-core等.) 

1.	Object的equals方法如何使用更好?

Object的equals方法容易抛出空指针异常，应使用常量和确定有值的对象来调用equals方法，如下:
if(str == "2" || "2".equals(str)){} // 正确写法
if(str == "2" || str.equals("2")){} // 错误写法

2.	Final关键字的作用?
final可以用来修饰类，修饰属性，修饰方法，修饰参数
	final修饰的类表示此类不允许被继承，提高了安全性，例如String类，System类，它的成员可以是final，也可以是非final.
	final修饰的方法不能被子类重写，例如Object类中的getClass()方法.
	final修饰的变量在第一次赋值后就不能被更改(不能被修改的原因是为了效率和安全性)，阿里的Java开发手册中说道：常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长，例如MAX_STOCK_COUNT
	final修饰的参数表示不可变

3.	否可以继承String类
不能，因为String类是final类不能被继承。

4.	Object类
java.lang.Object类中只有一个空参的构造器，
