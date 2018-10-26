Java Stack Class Diagram(Stack类图):
	Java Stack直接扩展Vector类，间接实现RandomAccess，List，Collection等接口。

public class Stack<E> extends Vector<E> {}

public class Vector<E> extends AbstractList<E> 
implements List<E>, RandomAccess, Cloneable, java.io.Serializable{}

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {}

public abstract class AbstractCollection<E> implements Collection<E> {}

public interface Collection<E> extends Iterable<E> {}


Java Stack是一个legacy的Collection类, it extends Vector class,
只支持五个操作,支持LIFO（后进先出Last in first out）,自Java 1.0以来，它就在
Collection API中可用。

As Vector implements List, Stack class is also a List 
implementation class but does NOT support all operations 
of Vector or List. As Stack supports LIFO, it is also known 
as LIFO Lists.

由于Vector实现了List，所以Stack类也是List实现类，
但不支持Vector或List的所有操作。 由于Stack支持LIFO，因此它也称为LIFO列表。

讨论有关Java中的Stack的以下概念：

Java Stack(堆栈)
Java Stack Class Diagram
Java Stack Methods
Java Stack Basic Example
How Stack’s push() and pop() works Internally?
Java Array to Stack Example
Java List to Stack Example
Java Stack to List Example

Java Stack(堆栈):

Java Stack是LIFO对象。它扩展了Vector类，但仅支持五个操作。
(supports only five operations). Java Stack类只有一个构造函数
which is empty or default constructor.
因此，当我们创建一个Stack时,最初它不包含条目，那意味着Stack is Empty.
So, when we create a Stack, initially it contains no items that means Stack is empty.

Stack内部有一个指针：TOP，它指的是Stack元素的顶部。如果Stack为空，
则TOP指向前一个元素位置。如果Stack不为空，则TOP指向顶部元素。

Java Stack仅使用以下五个操作扩展Vector类。

boolean empty（）：测试此堆栈是否为空。
E peek（）：查看此堆栈顶部的对象，而不将其从堆栈中删除。
E pop（）：移除此堆栈顶部的对象，并将该对象作为此函数的值返回。
E推（E项）：将项目推到此堆栈的顶部。
int search（Object o）：返回对象在此堆栈上的从1开始的位置。

How Stack’s push() and pop() operations works Internally?
Stack的push（）和pop（）操作如何在内部工作？

As we know Stack’s push() and pop() are most frequently 
used Stack operations. The push() operation is used to 
insert an element into a Stack at top. The pop() operation 
is sued to remove a top element from a Stack.
我们知道Stack的push（）和pop（）是最常用的Stack操作。 push（）操作用于将
元素插入到顶部的Stack中。 执行pop（）操作以从堆栈中删除顶部元素。

Stack data structure has one internal property: top to refer 
top element of that stack. If Stack is empty, this top refers 
to the before first element as shown below:

堆栈数据结构有一个内部属性：top用于引用该堆栈的顶部元素。 
如果Stack为空，则此top指的是存放第一个元素位置的前一个元素:

				|		|
				|		|
				|		|
				|		|
				|		|
				|		|
				|		|
				|		|
				|_______|
						<------Top
				Empty Stack

Stack的Push操作始终在Stack的顶部插入新元素。
Stack的Pop操作总是从Stack顶部删除一个元素。



