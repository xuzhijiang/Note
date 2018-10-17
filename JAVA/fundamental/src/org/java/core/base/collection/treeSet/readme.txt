Java TreeSet 是 java.util.SortedSet最流行的实现.

SortedSet 是一个接口，它 extends java.util.Set. 
Java Sorted Set 对它的所有的元素排序。

public class TreeSet<E> extends AbstractSet<E>
implements NavigableSet<E>, Cloneable, java.io.Serializable{}

public interface NavigableSet<E> extends SortedSet<E> {}

public interface SortedSet<E> extends Set<E> {}

public interface Set<E> extends Collection<E> {}

public interface Collection<E> extends Iterable<E> {}

In other words.当我们迭代TreeSet的时候，我们可以期待有顺序的数据，
Java TreeSet元素按其自然顺序排序，或者我们可以在创建SortedSet时提供Comparator。
如果我们在set创建期间不提供特定的Comparator，则元素必须实现Comparable以确保自然排序。 

根据规范，所有排序集实现类应提供4种类型的构造函数。
As per specification, all sorted set implementation 
classes should provide 4 types of constructors.

A void (no arguments) constructor:：它应该创建一个按照元素的自然顺序排序的有序集。
A constructor with an argument of type Comparator:：它应该创建一个按照提供的Comparator排序的有序集。
A constructor with an argument of type Collection：它应该创建一个带有提供集合元素的有序集合，该集合根据元素的自然顺序排序。
A constructor with an argument of type SortedSet:：它应该表现为复制构造函数，并使用相同的元素和提供的有序集的相同顺序创建新的有序集。

不幸的是interface不能contains constructors.没有任何方法 强迫这些推荐.
there isn't any way to enforce these recommendations.

Java SortedSet方法:

与Set相比，SortedSet肯定会获得一些额外的特权，因为它的排序性质。
正如您可能已经猜到的那样，除了Set接口的继承方法之外，它还提供了一些其他方法。

Comparator<? super E> comparator()：返回用于对集合中的元素进行排序的比较器实例。如果元素按其自然顺序排序，则返回null。
SortedSet <E> subSet（E fromElement，E toElement）：返回给定范围的此集合的一部分。 （fromElement是包容性的，而toElement是独占的）。请注意，它返回子集的视图。因此，对返回集执行的更改将反映在实际集中。
SortedSet <E> headSet（E toElement）：返回此set的部分视图，其元素严格小于toElement。
SortedSet <E> tailSet（E fromElement）：返回此set的部分视图，其元素大于或等于fromElement。
E first（）：返回集合中恰好是集合中最低元素的第一个元素。
E last（）：返回集合中最后一个元素，它恰好是集合中的最高元素。