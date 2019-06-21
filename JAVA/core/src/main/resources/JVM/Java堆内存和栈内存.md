# Java Heap Memory and Stack Memory(Java堆内存和栈内存):

## Java Heap Space(Java堆内存,堆空间)

所有对象都在这里分配内存，是垃圾收集的主要区域（"GC 堆"）。垃圾收集在堆内存上运行，以释放没有任何引用的对象。在堆空间中创建的任何对象都具有全局访问权限，可以从应用程序的任何位置引用。

现代的垃圾收集器基本都是采用分代收集算法，针对不同类型的对象采取不同的垃圾回收算法。可以将堆分成两块：

- 新生代（Young Generation）
- 老年代（Old Generation）

堆不需要连续内存，并且可以动态增加其内存，增加失败会抛出 OutOfMemoryError 异常。

可以通过 -Xms 和 -Xmx 这两个虚拟机参数来指定一个程序的堆内存大小，第一个参数设置初始值，第二个参数设置最大值。

```java
java -Xms1M -Xmx2M HackTheJava
```

## Java Stack Memory(Java栈内存)

栈内存用于执行线程,每个线程都会开一个自己的栈内存(thread stacks)，它们包含方法中的特定值,这些值生命周期是短暂的(short-lived)，
还包含了堆内存中的对其他对象的引用，这些对象的引用都是在方法中引用的.

栈内存总是以LIFO(后进先出)的顺序被引用，每当调用一个方法时，都会在Stack Memory中创建一个新的块，
以便该方法 保存 local primitive values(本地原始值) 和 在方法中其他对象的引用

只要方法结束，这个块就变成没有用的，这个块的空间对于下一个方法就变得可用，
栈内存的大小相比于堆内存是非常小的。

## Java堆内存和栈内存不同

- 堆内存被应用程序的所有程序共用，而栈内存仅被一个执行的线程所使用。
- 每当创建一个对象的时候，它总是被存储在堆内存中，栈内存中包含了对它的引用。
- Stack memory仅包含了本地原始值变量以及对堆内存中对象的引用变量
- 存储在堆内存中的对象是程序全局访问的，然而栈内存不可以被其他线程访问，只能被执行它的一个线程访问。
- stack中的内存管理方式是LIFO，然而对于堆内存是更加复杂的，因为它是
可以被程序全局访问的，Heap memory(堆内存)被分成了年轻一代，老一代等。
- stack内存是短命的，然而堆内存是从程序开始到程序执行结束。
- 可以用-Xms 和-Xmx JVM选项去定义`堆内存`的初始大小和最大大小，我们可以用-Xss去定义stack内存的大小.
- JVM将以Xms大小的堆内存启动，可以使用最大Xmx大小的堆内存。Xms没有默认值，Xmx通常的默认值是256MB,当遇到OutOfMemoryError的时候会修改Xms和Xmx.
- 当stack内存满的时候，Java运行时抛出java.lang.StackOverFlowError ，然而当heap memory慢的时候，会抛出java.lang.OutOfMemoryError，java heap space error.
- stack memory大小相比于heap空间的是非常小的，因为LIFO内存分配的简单，stack 内存相比于heap memory内存是非常快速的。
- JVM可以使用超过Xmx的(堆内存)更大内存空间.因为Xmx和Xms只是针对堆内存.

## java.lang.OutOfMemoryError: Java heap space

当JVM无法分配内存来创建对象时，抛出java.lang.OutOfMemoryError。Java OutOfMemoryError是一个错误，发生在运行时。

在这种情况下，垃圾收集器不可以回收更多空间去满足程序需要，因此错误发生，因此java运行时抛出此异常。

## 修改运行class文件的heap space：

你可以使用g或者G表示GB，m或M表示MB，k或K表示KB，例如分配最大1GB堆内存的表示方法:

```java
java -Xmx1073741824 com.mycompany.MyClass
java -Xmx1048576k com.mycompany.MyClass
java -Xmx1024m com.mycompany.MyClass
java -Xmx1g com.mycompany.MyClass
```

可以同时指定初始堆内存大小和最大堆内存大小: `java -Xms256m -Xmx2048m`