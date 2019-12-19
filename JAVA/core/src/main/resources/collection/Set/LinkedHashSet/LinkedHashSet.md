# LinkedHashSet

LinkedHashSet继自HashSet，但是内部的map是使用LinkedHashMap构造的，并且accessOrder为false(也就是不使用访问顺序,使用插入顺序)。所以LinkedHashSet遍历的顺序就是插入顺序。

![](../../pics/LinkedHashSet内部实现.png)

![](../../pics/LinkedHashSet内部实现02.png)