# HashMap 和 Hashtable 的区别

1.  **线程是否安全：** HashMap 是非线程安全的，HashTable 是线程安全的；HashTable 内部的方法基本都经过  `synchronized`  修饰。（如果你要保证线程安全的话就使用 ConcurrentHashMap,而不是HashTable,思考下为什么?）
2. HashTable 基本被淘汰，不要在代码中使用它.代码太粗糙了,例如put元素的时候,是直接使用取余算法,而不是位运算,说明已经很久没有维护了.
3. **对Null key 和Null value的支持：** HashMap 中，null 可以作为key(存放在下标为0的桶中),也可以作为value。HashTable的value不允许为null，否则直接抛出 NullPointerException.
4. **初始容量大小和每次扩充容量大小的不同 ：**   ①创建时如果不指定容量初始值，Hashtable 默认的初始大小为11，之后每次扩充，容量变为原来的2n+1。HashMap 默认的初始化大小为16。之后每次扩充，容量变为原来的2倍。②创建时如果给定了容量初始值，那么 Hashtable 会直接使用你给定的大小，而 HashMap 会将其扩充为2的幂次方.
5. **底层数据结构：** JDK1.8 以后的 HashMap 在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）时，将链表转化为红黑树，以减少搜索时间。Hashtable 没有这样的机制.