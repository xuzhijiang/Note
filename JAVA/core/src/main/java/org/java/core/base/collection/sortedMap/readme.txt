Java SortedMap is a Map 在它的key上提过全部的排序.

Java SortedMap可以根据其键的自然顺序(the natural ordering)进行排序，
也可以在创建map时提供Comparator。

如果我们在创建SortedMap时不提供任何Comparator（这个Comparator可以接受
map的key），则map的all key elements都必须实现Comparable接口以确保排序。

TreeMap -> NavigableMap -> SortedMap -> Map

根据规范(As per the specification.)，所有通用的sorted map implementation classed应提供以下标准构造函数：

1. A void (no arguments) constructor: 它应该创建一个按照其键(key)的自然顺序(the natural ordering)排序的有序映射。
2. A constructor with an argument of type Comparator: 它应该创建一个有序sorted map，其key按照指定的比较器(Comparator)进行排序。
3. A constructor with an argument of type Map:  It should create a sorted map with elements 
of provided map which is sorted according to the natural ordering of its keys.
4. A constructor with an argument of type SortedMap: It should behave as a copy 
constructor and create a new sorted map with the same elements and the same 
ordering of provided sorted map.
它应该表现为复制构造函数，并创建一个新的有序元素，并提供相同的元素和提供的有序映射的相同顺序。

Of course, it’s impossible to enforce these recommendation as interfaces 
can’t specify constructors unlike methods.
(接口不能象方法那样 指定构造器)

与Map相比，提供了几种额外的方法来利用排序:

Comparator comparator（）：返回用于在地图中对键进行排序的比较器实例。如果按照自然顺序对键进行排序，则返回null。
Set <Map.Entry> entrySet（）：返回地图中包含的一组映射。
K firstKey（）：返回地图中的第一个（最低）键。
K lastKey（）：返回地图中的最后一个（最高）键。
Set keySet（）：返回包含地图所有键的Set。
SortedMap headMap（K toKey）：返回键小于toKey的地图部分的视图。
SortedMap tailMap（K fromKey）：返回其键大于或等于fromKey的映射部分的视图。
Collection values（）：返回此映射中包含的值的Collection视图。

Note that Set returned in above methods is a view of the actual Set. Changes done on these 
views are reflected on actual data structure as well.
请注意，上述方法中返回的Set是实际Set的视图。对这些视图所做的更改也会反映在实际数据结构中。