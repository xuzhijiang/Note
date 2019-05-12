## ArrayList

ArrayList是List接口的实现类之一,ArrayList内部使用一个数组完成数据的增删改查.

### 类继承结构

```java
// ArrayList继承自AbstractList，AbstractList是一个List接口的骨干实现，它实现了List接口的大部分方法.
public class ArrayList<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable{}

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {}
```

### ArrayList注意事项

1. 几乎与Vector类似，只是它不同步，因此在单线程环境中性能更好。
2. 不是线程安全的，因此在多线程环境中使用时必须特别小心。
3. 可以包含重复值，它还允许“null”值。
4. ArrayList中的对象按顺序添加。 因此，您始终可以通过索引0检索第一个对象。
5. 默认容量定义为10.但是我们可以更改默认容量。
6. ArrayList Iterator和ListIterator是快速失败的。 如果在iterator创建后修改列表结构, 
 `除去用迭代器自身的add或remove之外的任何其他方式修改列表结构`，都会抛出ConcurrentModificationException。
7. ArrayList提供对其元素的随机访问，我们可以通过它的索引检索任何元素。
8. ArrayList支持Generics

来获取线程安全的List: `List<Integer> synchronizedList = Collections.synchronizedList(ints);`
