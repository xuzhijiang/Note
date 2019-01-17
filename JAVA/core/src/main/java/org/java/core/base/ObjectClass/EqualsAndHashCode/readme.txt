Java equals（）和hashCode（）方法存在于Object类中。
 所以每个java类都获得equals（）和hashCode（）的默认实现。
 
Object equals默认实现:

	public boolean equals(Object obj) { return (this == obj); }

Java Object hashCode（）是native方法，
它返回对象的整数哈希码值. hashCode（）方法的一般契约是：

1. 除非修改了equals（）方法中使用的object属性，
否则hashCode（）的多次调用应该返回相同的整数值。
2. 对象哈希码值可以在同一应用程序的多次执行中更改。
3. 如果两个对象equals（）方法相等，那么它们的哈希码必须相同。
4. 如果两个对象equals（）方法不相等，则它们的哈希码不需要不同。 
它们的哈希码值可能相等也可能不相等。

Java hashCode（）和equals（）方法在java中用于
基于哈希表(hash table)实现的存储和检索数据的。

equals（）和hashCode（）的实现应遵循这些规则(follow these rules)：

如果是o1.equals（o2），则o1.hashCode（）== o2.hashCode（）应始终为true。
如果o1.hashCode（）== o2.hashCode为true，则(it doesn't mean that)不表示o1.equals（o2）为true。

何时重写equals（）和hashCode（）方法？

1. 当我们重写equals（）方法时，几乎有必要重写hashCode（）方法，
以便我们的实现不会违反它们的契约。

2. 请注意，如果违反了equals（）和hashCode（）契约，
您的程序将不会抛出任何异常，如果您不打算将该类用作哈希表键(key)，
那么它不会产生任何问题。

3. 如果您计划将类用作Hash表键，那么必须override equals（）和hashCode（）方法。

哈希碰撞:

简单来说，Java Hash表实现使用以下逻辑进行get和put操作。

1. 首先使用“key”的hashCode识别要使用的“桶(bucket)”。
2. 如果存储桶(bucket)中没有具有相同的哈希码(hashCode)对象，如果是put操作则添加对象，如果是get操作返回null。
3. 如果存储桶（bucket)中有具有相同的哈希码的对象，则“key”的equals方法就起作用。
4. 如果equals（）返回true并且它是put操作，则覆盖对象值。
5. 如果equals（）返回false并且它是put操作，则将新条目添加到存储桶。
6. 如果equals（）返回true并且它是get操作，则返回对象值。
7. 如果equals（）返回false并且它是get操作，则返回null。

当两个key具有相同的哈希码(hashCode)时的现象称为哈希冲突。

 如果hashCode（）方法未正确实现，则会有更多的哈希冲突，并且映射条目将无法正确分布，
 从而导致get和put操作变慢。 这是生成哈希码时使用素数的原因，以便在所有桶中正确分布映射条目。 
 
 如果我们不同时实现hashCode（）和equals（）怎么办？
 What if we don’t implement both hashCode() and equals()?
 
我们已经在上面看到，如果没有实现hashCode（），
我们将无法检索该值，因为HashMap使用hash Code来查找存储桶以查找条目(entry)。

如果我们只使用hashCode（）并且不实现equals（），
那么也检索不到值，因为equals（）方法将返回false。

实现equals（）和hashCode（）方法的最佳实践:

1. 在equals（）和hashCode（）方法实现中使用相同的属性，
以便在更新任何属性时不违反它们的契约。

2. 最好将不可变对象用作Hash表键，这样我们就可以缓存哈希码而不是在每次调用时计算它。 
这就是为什么String是Hash表键的一个很好的候选者，因为它是不可变的并且缓存了哈希码值。

3. 实现hashCode（）方法，以便发生最少数量的哈希冲突，并且条目均匀分布在所有桶中。

可以学习String中的hashCode()和equals方法的实现.

hashCode（）和equals（）方法的重要性是什么？

HashMap使用Key对象hashCode（）和equals（）方法来确
定放置键值对的索引。 当我们尝试从HashMap获取值时，也会使用这些方法。 
如果这些方法没有正确实现，两个不同的Key可能会产生相同的hashCode（）
和equals（）输出，在这种情况下，而不是将它存储在不同的位置，HashMap
会认为它们相同并覆盖它们。

类似地，所有不存储重复数据的集合类都使用hashCode（）和equals（）
来查找重复项，因此正确实现它们非常重要。