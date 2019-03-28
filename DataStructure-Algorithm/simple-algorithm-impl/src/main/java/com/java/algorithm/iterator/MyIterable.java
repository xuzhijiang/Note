package com.java.algorithm.iterator;

/**
 * 修改SingleLinkList，新增一个返回迭代器的方法.
 * 关于返回迭代器方法的名称，是任意的，不过最好还是符合某种规范，
 * java.lang.Iterable接口，定义了这样一个返回迭代器的方法
 * @param <T>
 */
public interface MyIterable<T> {

    /**
     * 其返回类型就是MyIterator，现在你可能知道我们让NodeIterator实现MyIterator接口的原因了，
     * 因为这样，我们就可以将自己写的迭代器通过Java的标准接口返回
     * @return
     */
    MyIterator<T> iterator();

}
