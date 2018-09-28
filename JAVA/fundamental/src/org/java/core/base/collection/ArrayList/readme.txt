ArrayList是使用最多的集合类，

ArrayList继承自AbstractList，AbstractList是一个List接口的骨干实现，也就是实现了List接口的大部分方法.
Java ArrayList是List接口的可调整大小的数组实现，这意味着它以默认大小开始，并在将更多数据添加到数组列表时自动增长

一些重要的方法是:

1. Java ArrayList几乎与Vector类似，只是它不同步，因此在单线程环境中性能更好。
2. Java ArrayList不是线程安全的，因此在多线程环境中使用时必须特别小心。
3. Java ArrayList可以包含重复值，它还允许“null”值。
4. java ArrayList中的对象按顺序添加。 因此，您始终可以通过索引0检索第一个对象。
5. Java ArrayList默认容量定义为10.但是我们可以通过它的构造函数或通过调用ensureCapacity（int minCapacity）方法来更改默认容量。
6. Java ArrayList Iterator和ListIterator实现是快速失败的。 如果在iterator创建后修改列表结构, 
 除去用迭代器自身的add或remove之外的任何其他方式修改列表结构，则会抛出ConcurrentModificationException。
7. Java ArrayList提供对其元素的随机访问，因为它适用于索引。 我们可以通过它的索引检索任何元素。
8. Java ArrayList支持Generics，它是创建ArrayList的推荐方法。