# TreeMap继承关系

![](../pics/TreeMap继承关系.png)

# TreeMap的数据结构

![](../pics/树节点Entry.png)
![](../pics/TreeMap-class.png)
![](../pics/红黑树的示意图.png)

# TreeMap是如何保证键的顺序的?(面试题)

![](../pics/TreeMap-comparator.png)

![](../pics/TreeMap-comparator02.png)

    TreeMap 是通过实现 SortMap 接口，根据 key 进行排序，从而保证 TreeMap 中所有键值对处于有序状态.

要么key实现Comparable,创建TreeMap的时候提供Comparator,否则提示`java.lang.ClassCastException: org.java.core.base.map.core.HashMapAndTreeMapDiff$Student cannot be cast to java.lang.Comparable`

# get方法分析

![](../pics/get方法比较器的选择.png)

# 何时在Java中使用TreeMap?

大多数情况下，HashMap足以在程序中用作Map实现。但是如果你有一些与排序相关的特殊要求，那么你可以使用TreeMap。
