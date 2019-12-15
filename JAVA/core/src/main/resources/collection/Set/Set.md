# Set

![](../../../java/org/java/core/base/collection/core/Collection_interfaces.png)

## Set特点

不允许存储重复元素,元素是无序的,允许为null值

## 重要的实现类

- HashSet
- TreeSet:基于红黑树实现，支持有序性操作，例如根据一个范围查找元素的操作。但是查找效率不如 HashSet，HashSet 查找的时间复杂度为 O(1)，TreeSet 则为 O(logN)。
- LinkedHashSet：具有 HashSet 的查找效率，且内部使用双向链表维护元素的插入顺序。
