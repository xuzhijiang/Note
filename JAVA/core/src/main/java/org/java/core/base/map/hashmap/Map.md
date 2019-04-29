## Map概述

>基于哈希表实现,key不能重复

Map接口的常用实现类:HashTable，HashMap

1. HashMap：是基于‘拉链法’实现的散列表，底层采用数组+链表实现，一般用于单线程
2. HashTable：基于‘拉链法’实现的散列表，一般用于多线程
3. TreeMap：有序散列表，底层通过红黑树实现。