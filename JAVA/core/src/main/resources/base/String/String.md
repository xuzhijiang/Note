# String

    String类就重新了Object的equals和hashCode.String类的equals不再比较地址,而是比较内容.
    
>String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值

## String为什么是不可变的

在 Java 8 中，String 内部使用 char 数组存储数据。

```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    private final char value[];
}
```

value 数组被声明为 final，这意味着 value 数组初始化之后就不能再引用其它数组。因此可以保证 String 不可变

## 不可变的好处

**1. 可以缓存 hash 值** 

因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，hashcode在创建的时候被缓存，因此只需要进行一次计算String作为HashMap的key，处理速度比其他objects作为key速度要快很多，这就是为什么很多时候将它作为HashMap的key.

**2. 线程安全** 

String不可变性天生具备线程安全，可以在多个线程中安全地使用

## Char和String的区别?

- Char是单引号引起的一个字符; String是双引号引起的若干个字符
- Char相当于一个整形值( ASCII 值),可以参加表达式运算; String不能进行运算。
- char在Java中占两个字节.

## String StringBuffer 和 StringBuilder 的区别是什么? 

而StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串`char[]value` 但是没有用 final 关键字修饰，所以这两种对象都是可变的。

**线程安全性**

StringBuffer 对方法加了synchronized，所以是线程安全的。StringBuilder 并没有synchronized，是线程不安全的

**对于三者使用的总结：** 
1. 操作少量的数据: 适用String
2. 单线程操作大量数据: 适用StringBuilder
3. 多线程操作大量数据: 适用StringBuffer

**2. 线程安全** 

- String 不可变，因此是线程安全的
- StringBuilder 不是线程安全的
- StringBuffer 是线程安全的，内部使用 synchronized 进行同步

## 编码与解码

编码就是把字符转换为字节，而解码是把字节重新组合成字符。如果编码和解码过程使用不同的编码方式那么就出现了乱码。

- GBK 编码中，一个中文字符占 2 个字节，一个英文字符占 1 个字节；
- UTF-8 编码中，一个中文字符占 3 个字节，一个英文字符占 1 个字节；
- UTF-16be 编码中，一个中文字符和一个英文字符都占 2 个字节。

>UTF-16be 中的 be 指的是 Big Endian，也就是大端。相应地也有 UTF-16le，le 指的是 Little Endian，也就是小端。

Java 的内存编码使用双字节编码 UTF-16be，这不是指 Java 只支持这一种编码方式，而是说 char 这种类型使用 UTF-16be 进行编码。char 类型占 16 位，也就是两个字节，Java 使用这种双字节编码是为了让一个中文或者一个英文都能使用一个 char 来存储。

## String 的编码方式

String 可以看成一个字符序列，可以指定一个编码方式将它编码为字节序列，也可以指定一个编码方式将一个字节序列解码为 String。

```java
String str1 = "中文";
// 获取字符序列对应的字节序列
byte[] bytes = str1.getBytes("UTF-8");
// 获取字节序列 对应的字符串序列
String str2 = new String(bytes, "UTF-8");
System.out.println(str2);
```

在调用无参数 getBytes() 方法时，默认的编码方式不是 UTF-16be(双字节编码)。双字节编码的好处是可以使用一个 char 存储中文和英文，而将 String 转为 bytes[] 字节数组就不再需要这个好处(String类型理论可以占用内存大小,所以不用再考虑字符占用的空间,但是char是每个字符最多占用2个字节，大小有限制)，因此也就不再需要双字节编码。getBytes() 的默认编码方式与平台有关(看源码,一般是UTF-8)


### String和StringBuffer、StringBuilder的区别是什么？String为什么是不可变的？

####  String和StringBuffer、StringBuilder的区别

**可变性**
　

简单的来说：String 类中使用 final 关键字字符数组保存字符串，`private　final　char　value[]`，所以 String 对象是不可变的。而StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串`char[]value` 但是没有用 final 关键字修饰，所以这两种对象都是可变的。

StringBuilder 与 StringBuffer 的构造方法都是调用父类构造方法也就是 AbstractStringBuilder 实现的，大家可以自行查阅源码。

AbstractStringBuilder.java

```java
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    char[] value;
    int count;
    AbstractStringBuilder() {
    }
    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }
```


**线程安全性**

String 中的对象是不可变的，也就可以理解为常量，线程安全。AbstractStringBuilder 是 StringBuilder 与 StringBuffer 的公共父类，定义了一些字符串的基本操作，如 expandCapacity、append、insert、indexOf 等公共方法。StringBuffer 对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。StringBuilder 并没有对方法进行加同步锁，所以是非线程安全的。
　　

**性能**

每次对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。StringBuffer 每次都会对 StringBuffer 对象本身进行操作，而不是生成新的对象并改变对象引用。相同情况下使用 StirngBuilder 相比使用 StringBuffer 仅能获得 10%~15% 左右的性能提升，但却要冒多线程不安全的风险。

**对于三者使用的总结：** 
1. 操作少量的数据 = String
2. 单线程操作字符串缓冲区下操作大量数据 = StringBuilder
3. 多线程操作字符串缓冲区下操作大量数据 = StringBuffer

####  String为什么是不可变的吗？
简单来说就是String类利用了final修饰的char类型数组存储字符，源码如下图所以：

```java
    /** The value is used for character storage. */
    private final char value[];
```

####  String真的是不可变的吗？
我觉得如果别人问这个问题的话，回答不可变就可以了。
下面只是给大家看两个有代表性的例子：

**1) String不可变但不代表引用不可以变**
```java
		String str = "Hello";
		str = str + " World";
		System.out.println("str=" + str);
```
结果：
```
str=Hello World
```
解析：

实际上，原来String的内容是不变的，只是str由原来指向"Hello"的内存地址转为指向"Hello World"的内存地址而已，也就是说多开辟了一块内存区域给"Hello World"字符串。

**2) 通过反射是可以修改所谓的“不可变”对象**

```java
		// 创建字符串"Hello World"， 并赋给引用s
		String s = "Hello World";

		System.out.println("s = " + s); // Hello World

		// 获取String类中的value字段
		Field valueFieldOfString = String.class.getDeclaredField("value");

		// 改变value属性的访问权限
		valueFieldOfString.setAccessible(true);

		// 获取s对象上的value属性的值
		char[] value = (char[]) valueFieldOfString.get(s);

		// 改变value所引用的数组中的第5个字符
		value[5] = '_';

		System.out.println("s = " + s); // Hello_World
```

结果：

```
s = Hello World
s = Hello_World
```

解析：

用反射可以访问私有成员， 然后反射出String对象中的value属性， 进而改变通过获得的value引用改变数组的结构。但是一般我们不会这么做，这里只是简单提一下有这个东西。