package org.java.core.base.collection.core;

import java.util.*;
import java.util.function.Predicate;

/**
 *
 * 涉及到比较的都要依赖对象的equals方法。一般而言集合中的对象元素都应该重写equals方法。
 * @see Collections  Collections是一个工具类，提供了一系列的静态方法来辅助容器操作，这些方法包括对容器的搜索，排序等
 * @since 1.2
 */
public interface JDKCollection<E> extends Iterable<E> {

    /**
     * 返回集合中元素个数
     */
    int size();

    /**
     * 判断集合是否为空
     */
    boolean isEmpty();

    /**
     * 查看集合中是否包含某一个元素，返回一个布尔类型的值
     */
    boolean contains(Object o);

    /**
     * 返回一个Iterator，实现集合遍历
     */
    Iterator<E> iterator();

    /**
     * 将集合装换为Object类型的数组
     */
    Object[] toArray();

    /**
     * 把集合转换成 指定类型的数组
     *
     * @see Collections#addAll(Collection, Object[])  把一个数组添加到集合当中
     */
    <T> T[] toArray(T[] a);

    /**
     * 添加元素到队列里，添加成功返回true，由于容量满导致添加失败会抛出IllegalStateException异常
     */
    boolean add(E e);

    /**
     * 基于对象o,找到对应的元素，并删除。删除成功返回true，否则返回false
     */
    boolean remove(Object o);

    // Bulk Operations

    /**
     * 当前集合是否包含list中的所有元素，只有全部包含才返回true
     * @see    #contains(Object)
     */
    boolean containsAll(Collection<?> c);

    /**
     * 向集合中添加一个集合中的全部元素c
     * @throws IllegalStateException if not all the elements can be added at
     *         this time due to insertion restrictions
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 移除当前集合中包含形参集合的元素。
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);

    /**
     *
     * Removes all of the elements of this collection that satisfy the given
     * predicate.
     * 删除此集合中满足给定谓词(满足给定条件的所有元素)的所有元素。
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * 将当前集合中与形参集合一样的元素保留。(retain是保留的意思)
     */
    boolean retainAll(Collection<?> c);

    /**
     * 清空集合元素
     */
    void clear();

    /**
     * 判断当前集合是否与形参集合完全相同.
     * (注意元素顺序不同的时候，返回false,即使元素通过equals是相等的.)
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this collection.
     */
    int hashCode();

}
