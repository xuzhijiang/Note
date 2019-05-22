Java Heap由String Pool和非String Pool的部分组成，见图:https://cdn.journaldev.com/wp-content/uploads/2012/11/String-Pool-Java1.png

A. Why String is immutable and final?

0. Why String is immutable in java is one of the popular interview question,
We know that String is immutable and final in java and java runtime maintains 
a String pool that makes it a special class.
为什么在Java中String是不可变的？String在java中是final的，不可变的，Java运行时维护了一个String Pool
使String成为一个特殊的类.

Let’s look at some benefits of String immutability, 
that will help in understanding why String is immutable in java.

1. String pool is possible (字符串池是可能的)only because String is immutable in java(因为String在java中是不可变的), 
this way Java Runtime saves a lot of java heap space(堆空间) because different 
String variables can refer to same String variable in the pool. If String
would not have been immutable, then String interning would not have been 
possible(如果String不是不可变的，那么String interning就不可能) because
if any variable would have changed the value, it would 
have been reflected to other variables also.

2. If String is not immutable then it would cause severe security 
threat to the application. For example, database username, password 
are passed as String to get database connection and in socket programming
 host and port details passed as String. Since String is immutable it’s 
 value can’t be changed otherwise any hacker could change the referenced 
 value to cause security issues in the application.
2.如果字符串不是不可变的，将造成严重的威胁到应用的安全，例如，
数据库用户名密码作为String传递以获取数据库连接,以及在套接字编程中，主机和端口号详细信息是作为字符串传递的。
因为字符串是不可变的，所以他的值是不可以被改变的，否则任何黑客就可以通过改变被引用的值，造成在应用中的安全问题.

3.Since String is immutable, it is safe for multithreading and a 
single String instance can be shared across different threads. 
This avoid the usage of synchronization for thread safety, 
Strings are implicitly thread safe.
因为字符串是不可变的，对于多线程是安全的，一个单个的字符串实例可以跨不同的线程访问。
这个就避免了为了线程安全使用同步，字符串是隐式线程安全的。

4. Strings are used in java classloader and immutability provides security 
that correct class is getting loaded by Classloader. For example, think 
of an instance where you are trying to load java.sql.Connection class 
but the referenced value is changed to myhacked.Connection class that 
can do unwanted things to your database.
字符串在Java类加载器中使用，不可变性提供了正确的类可以正确被加载的安全保障。例如想一下你正在尝试
去加载java.sql.Connection类的实例，但是引用的值被改成myhacked.Connection类，可以
对数据库执行不想做的事情.

5. ince String is immutable, its hashcode is cached at the time of creation 
and it doesn’t need to be calculated again. This makes it a great 
candidate for key in a Map and it’s processing is fast than other 
HashMap key objects. This is why String is mostly used Object as HashMap keys.
因为String是不可变的，它的hashcode是在创建的时候被缓存，它不需要再次被计算，String作为HashMap的key，处理速度比其他objects作为key速度
要快很多，这就是为什么很多时候将它作为HashMap的key。

Above are some of the reasons I could think of that 
shows benefits of String immutability. It’s a great feature 
of Java String class and makes it special.

B. What is Java String Pool?(https://www.journaldev.com/797/what-is-java-string-pool)

As the name suggests, String Pool in java is a pool of Strings 
stored in Java Heap Memory. We know that String is special class
in java and we can create String object using new operator as well as 
providing values in double quotes.
如同名字所展示的，String Pool在Java中是存储在Java堆内存中字符串池,我们知道String在Java中
是特殊的类，我们可以用new操作符创建字符串，也可以用双引号创建String

Here is a diagram which clearly explains how String Pool is maintained 
in java heap space and what happens when we use different ways to create Strings.

String Pool is possible only because String is immutable in Java 字符池是可能的只因为String
在java中是不可变的.

String pool helps in saving a lot of space for Java Runtime 
although it takes more time to create the String.(字符池在Java运行时帮助节省
了不少空间，尽管需要花费更多的时间去创建字符串)

When we use double quotes to create a String, it first looks for String with same value 
in the String pool, if found it just returns the reference else 
it creates a new String in the pool and then returns the reference.
我们用双引号创建字符串的时候，首先会在字符池中寻找有着相同值的字符，找到就返回这个字符的引用，否则就在
字符池中创建新的字符池，然后返回这个字符池的引用.

However using new operator, we force String class to create a new 
String object in heap space. We can use intern() method to put it 
into the pool or refer to other String object from string pool having same value.
然而用new操作符，我们强制在堆内存中创建新的String对象，我们可以用intern方法把他放到pool中，或者
引用来自String pool池中有相同值的其他字符对象

Sometimes in java interview, you will be asked question around String pool. 
For example, how many string is getting created in below statement?
String str = new String("Cat");

In above statement, either 1 or 2 string will be created. If there is 
already a string literal “Cat” in the pool, then only one string “str” 
will be created in the heap. If there is no string literal “Cat” in the pool, 
then it will be first created in the pool and then in the heap space, so total 
2 string objects will be created.
在上面的语句中，1个或者2个将会被创建，如果在pool中已经有一个Cat，那么只有一个Cat会在heap中创建
，如果pool中没有Cat,那么会现在Pool中创建Cat，然后再heap中创建heap, str将引用heap中的Cat.

Detailed explanation is –(细节解释)
String pool is also part of heap.(String Pool是heap的一部分)
So the string will always be created in heap. 
But string may be or may not be created in pool.

String str = new String(“Cat”);

So in above sentence, if “Cat” is present in string pool, 
it will only be created in heap and will not be created in String pool.

But if it is not present in String pool it will be created 
in string pool as well as heap space (or we can say space apart from String pool)

Java String subSequence

为什么String实现subSequence方法?
Java 1.4 introduced CharSequence interface and String implements this 
interface, this is the only reason for the implementation of subSequence
 method in String class. Internally it invokes the String substring method.
Java在1.4引入了CharSequence接口，String实现了这个接口，这个是唯一的原因在String中实现这个方法
此方法内部调用了substring方法.

An invocation of this method of the form str.subSequence(begin, end) 
behaves in exactly the same way as the invocation of str.substring(begin, end).
str.subSequence(begin, end) 与 str.substring(begin, end)效果一模一样
结论：

There is no benefit in using subSequence method, ideally you should
 always use String substring method.
 使用subSequence方法没有益处，理想情况下应该总是使用substring方法.
