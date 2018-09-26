List是一个有序集合,List是一个接口，继承自Collection接口.(注意interface内部是可以实现方法的,当然也可以不用实现)

public interface List<E> extends Collection<E>{}

public interface Collection<E> extends Iterable<E> {}

public interface Iterable<T> {}

Some of the important points about Java List are:
1. Java List interface is a member of the Java Collections Framework.
2. List allows you to add duplicate elements.可以添加重复元素
3. List allows you to have ‘null’ elements.可以有null元素
4. List interface got many default methods in Java 8, for example replaceAll, sort and spliterator.
5. List indexes start from 0, just like arrays.
6. List supports Generics and we should use it whenever possible. 
Using Generics with List will avoid ClassCastException at runtime.将Generics与List一起使用将在运行时避免ClassCastException。

最常使用的List的实现类是: ArrayList, LinkedList, Vector, Stack, CopyOnWriteArrayList. 
AbstractList provides a skeletal implementation of the List interface to reduce the effort in implementing List.
AbstractList提供了List接口的骨干实现，以减少实现List的工作量。例如ArrayList是直接extends AbstractList

Some of the useful Java List methods are：有用的List方法:

size(), isEmpty(), contains(Object o), iterator(), toArray(), add(), 
remove(Object o): Removes the first occurrence of the specified element from this list.
retainAll(Collection<?> c):Retains only the elements in this list that are contained in the specified collection.
只保留在这个list中的元素，这些元素包含在指定的集合中.
clear(), get(int index),set(int index, E element), 
ListIterator<E> listIterator(): Returns a list iterator over the elements in the list.返回列表中元素的列表迭代器。
List<E> subList(int fromIndex, int toIndex)

Java8默认实现了这几个方法: replaceAll, sort(), Spliterator

Most common operations performed on java list are add, remove, set, clear, size etc. 
最常用的操作在Java list是add，remove，set，clear， size等.