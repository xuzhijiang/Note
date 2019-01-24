Java Heap Memory and Stack Memory(Java堆内存和栈内存):

Java Heap Space

    Java堆内存(堆空间)是在java运行时为对象和JRE classes分配内存的时候被使用,无论什么时候我们创建任何对象，
    它总是在堆内存中被创建。

    垃圾收集在堆内存上运行，以释放没有任何引用的对象使用的内存。在堆空间中创建的任何对象都具有全局访问权限，
    可以从应用程序的任何位置引用。

Java Stack Memory

    Java Stack Memory 用于执行线程, 它们包含方法中的特定值,这些值声明周期是短暂的(short-lived)，
    还包含了在堆内存中的对其他对象的引用，这些对象的引用都是在方法中引用的。

    Stack memory 总是以LIFO(后进先出)的顺序被引用，每当调用一个方法时，都会在Stack Memory中创建一个新的块，
    以便该方法 保存 local primitive values(本地原始值) 和 在方法中其他对象的引用

    只要方法结束，这个块就变成没有用的，这个块的空间对于下一个方法就变得可用，
    Stack memory的大小相比于Heap Memory是非常小的。
    As soon as method ends, the block becomes unused
    and become available for next method. Stack memory size is
    very less compared to Heap memory.

Difference between Java Heap Space and Stack Memory

1. Heap memory is used by all the parts of the application 
whereas stack memory is used only by one thread of execution.
heap memory被应用程序的所有程序共用，而stack memory仅被一个执行的线程所使用。

2. Whenever an object is created, it’s always stored in the Heap 
space and stack memory contains the reference to it. Stack memory only 
contains local primitive variables and reference variables to objects in heap space.
每当创建一个对象的时候，它总是被存储在heap space中，stack memory中包含了对它的引用。
Stack memory仅包含了本地原始值变量以及对heap space中对象的引用变量

3. Objects stored in the heap are globally accessible whereas 
stack memory can’t be accessed by other threads.
存储在heap中的对象是程序全局访问的，然而stack memory不可以被其他线程访问，只能被执行它的一个线程访问。

4. Memory management in stack is done in LIFO manner 
whereas it’s more complex in Heap memory because it’s used globally. 
Heap memory is divided into Young-Generation, Old-Generation etc, 
more details at Java Garbage Collection.
stack中的内存管理方式是LIFO(LAST IN FIRST OUT后进先出)，然而对于heap space是更加复杂的，因为它是
可以被程序全局访问的，Heap memory被分成了年轻一代，老一代等等，更多细节看Java垃圾收集。

5. Stack memory is short-lived whereas heap memory lives from 
the start till the end of application execution.
stack内存是短命的，然鹅heap 内存是从程序开始到程序执行结束。

6. We can use -Xms and -Xmx JVM option to define the startup size and 
maximum size of heap memory. We can use -Xss to define the stack memory size.
我们可以用-Xms 和-Xmx JVM选项去定义heap内存的开始大小和最大大小，我们可以用-Xss去定义stack内存的大小.

7. When stack memory is full, Java runtime throws java.lang.StackOverFlowError 
whereas if heap memory is full, it throws java.lang.OutOfMemoryError: Java Heap Space error.
当stack内存满的时候，Java运行时抛出java.lang.StackOverFlowError ，然而当heap memory慢的时候，会抛出
java.lang.OutOfMemoryError，java heap space error.

8. Stack memory size is very less when compared to Heap memory. 
Because of simplicity in memory allocation (LIFO), 
stack memory is very fast when compared to heap memory.
stack memory大小相比于heap空间的是非常小的，因为LIFO内存分配的简单，
stack 内存相比于heap memory内存是非常快速的。

9. That’s all for Java Heap Space vs Stack Memory in terms of 
java application, I hope it will clear your doubts regarding 
memory allocation when any java program is executed.


java.lang.OutOfMemoryError: Java heap space

java.lang.OutOfMemoryError is thrown when JVM is unable to allocate 
memory to create an object. Java OutOfMemoryError is an Error and occurs a runtime.
当JVM无法分配内存来创建对象时，抛出java.lang.OutOfMemoryError。
Java OutOfMemoryError是一个错误，发生在运行时。

在这种情况下，垃圾收集器不可以回收更多空间去满足程序需要，因此错误发生，因此java运行时抛出此异常。

see more: https://www.journaldev.com/21010/java-lang-outofmemoryerror-java-heap-space

修改运行class文件的heap space：

    You can use g or G for GB, m or M for MB, k or K for KB.
    For example all of the following are equivalent to saying that
    the maximum Java heap space is 1GB:

        java -Xmx1073741824 com.mycompany.MyClass
        java -Xmx1048576k com.mycompany.MyClass
        java -Xmx1024m com.mycompany.MyClass
        java -Xmx1g com.mycompany.MyClass