## Collection(集合)

### 概念

>java.util.Collection是集合框架的根接口，位于集合框架顶部

在Collection体系中，存在了Iterator迭代器和Comparable，Comparator对象排序接口

> Collection接口是最基本的集合接口，它不提供直接的实现，而是由子接口来提供实现,
以下是其重要子接口:

* Set是元素无序，不可重复的接口
* List是存储有序可重复的元素接口
* Queue接口

### 集合重要的实现类

* ArrayList(常用)
* LinkedList(线程不安全的)
* HashMap(常用)
* TreeMap(线程不安全的)
* HashSet(常用)
* TreeSet
* Vector(线程安全,因为内部使用了synchronized修饰)

### 存储对象的容器

>除了集合可以存储对象，数组也可以存储对象，但是数组存储对象有如下弊端：

1.数组长度一旦初始化就不可改变，存储对象的个数就不能改变
2.数组中真实存储的对象的个数也没有现成方法可用
