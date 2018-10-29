今天我们将学习如何在java中创建不可变类。 不可变对象是其状态在初始化后不会更改的实例。 
例如，String是一个不可变类，一旦实例化，其值永远不会改变。

不可变类有利于缓存目的，因为您不需要担心值的变化。
不可变类的其他好处是它本身就是线程安全的，所以在多线程环境下你不需要担心线程安全。

要在java中创建不可变类，必须执行以下步骤:

1. 将类声明为final，因此它就不可以被扩展。(Declare the class as final so it can't be extended.)
2. 将所有字段设为私有，以便不允许直接访问。(Make all fields private so that direct access is not allowed.)
3. 不要为变量提供setter方法(Don't provide setter methods for variables.)
4. 使所有可变字段成为最终字段，以便它的值只能分配一次。(Make all mutable fields final so that it’s value can be assigned only once.)
5. 通过执行深层复制的构造函数初始化所有字段。(Initialize all the fields via a constructor performing deep copy.)
6. 在getter方法中执行克隆对象以返回副本，而不是返回实际的对象引用。
(Perform cloning of objects in the getter methods to return a copy 
rather than returning the actual object reference.)

为了理解第4点和第5点，让我们运行一个运行良好的示例Final类，并在实例化后不会更改值。

