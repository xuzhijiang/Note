成员变量与局部变量区别     

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

    

final：
    final是最终修饰符，可以修饰类、成员方法、变量。
    final修饰的类：最终的类，不能被继承
    final修饰的变量： 相当于是一个常量,  一次赋值,终身不变
    final修饰的方法： 最终的方法，子类不能重写，可以继承过来使用

    final修饰的引用数据类型变量，可以修改对象里面的属性内容，不可改变地址值
    final修饰的成员变量，不能使用默认值，必须在创建对象之前完成赋值。

static:
    静态修饰符，被static修饰的内容属于类不专属于某个对象，多个对象共享使用这一个成员
    使用static修饰的成员可以用类名直接访问，建议这样使用：
        类名.静态方法名(参数);
        类名.静态常量名;
    静态修饰的成员只能直接访问静态修饰的成员，不能出现this、super,因为类是优于对象产生
        
包的访问：(前提 类用public修饰)
    同一个包下，随意访问
    不同包下：
        可以直接使用全名使用
            创建对象格式：包名.类名 变量名 = new包名.类名();
        为了方便，可以选择导包后，再直接使用类名本身，不加包名  在package后，class前使用import导入类
        如果是lang包下的类，可以不导包，直接使用
访问权限：
    权限修饰符
        public : 公共的
        protected: 受保护的
        默认的：不写就是默认的default
        private : 私有的
                    public  protected   默认的 private
        在当前类中       Y       Y           Y       Y
        同一包中的其他类Y       Y           Y
        不同包中的子类 Y       Y
        不同包中的其他类Y
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

0.78 默认是Double类型的，如果要指定它为Float类型的，就要加上f
Object o = 0.78; System.out.println(o instanceof Double);    // true
Object o = 0.7; System.out.println(o instanceof Float);    // false
注意instanceof后面的类型必须是对象类型，不能为primitive原始类型，否则编译不过去.

对于消息系统，个人的建议：

轻量级 选择 RabbitMQ
重量级 选择 Kafka
如果没有历史原因，不要再选择 ActiveMQ

系统吞吐量(tian)

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