# String

>String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值

## String为什么是不可变的

在 Java 8 中，String 内部使用 char 数组存储数据。

```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    private final char value[];
}
```

value 数组被声明为 final，这意味着 value 数组初始化之后就不能再引用其它数组。因此可以保证 String 不可变。

## 不可变的好处

**1. 可以缓存 hash 值** 

因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，hashcode在创建的时候被缓存，因此只需要进行一次计算String作为HashMap的key，处理速度比其他objects作为key速度要快很多，这就是为什么很多时候将它作为HashMap的key.

**2. String Pool 的需要** 

如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。只有 String 是不可变的，才可能使用 String Pool。

**3. 线程安全** 

String不可变性天生具备线程安全，可以在多个线程中安全地使用。

## String, StringBuffer and StringBuilder

**2. 线程安全** 

- String 不可变，因此是线程安全的
- StringBuilder 不是线程安全的
- StringBuffer 是线程安全的，内部使用 synchronized 进行同步

## String Pool

可以使用 String 的 intern() 方法在运行过程中将字符串添加到 String Pool 中.

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2);    // false
// 当一个字符串调用 intern() 方法时，如果 String Pool 中已经存在一个字符串和该字符串值相等（使用 equals() 方法进行确定），
// 那么就会返回 String Pool 中字符串的引用；否则，就会在 String Pool 中添加一个新的字符串，并返回这个新字符串的引用。
// s3 和 s4 是通过 s1.intern() 方法取得一个字符串引用
// intern() 首先把 s1 引用的字符串放到 String Pool 中，然后返回这个字符串引用。因此 s3 和 s4 引用的是同一个字符串。
String s3 = s1.intern();
String s4 = s1.intern();
System.out.println(s3 == s4); // true
```

如果是采用 "bbb" 这种字面量的形式创建字符串，会自动地将字符串放入 String Pool 中.

```java
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
```

>在 Java 7 之前，String Pool 被放在运行时常量池中，它属于永久代。而在 Java 7，String Pool 被移到堆中。这是因为永久代的空间有限，在大量使用字符串的场景下会导致 OutOfMemoryError 错误。

## new String("abc")

使用 new 的方式则会在堆中创建一个字符串对象

```java
String a = new String("ab"); // a 为一个引用
String b = new String("ab"); // b为另一个引用
```

## String a = "aaa";

```java
// "abc" 属于字符串字面量,因此编译时期会在 String Pool 中创建一个字符串对象，指向这个 "abc" 字符串字面量；
String a = "aaa"; // 放在常量池中
String b = "aaa"; // 从常量池中查找
System.out.println(a == b); //true
```

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
