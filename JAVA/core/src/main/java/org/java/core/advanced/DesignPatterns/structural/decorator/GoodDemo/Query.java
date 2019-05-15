package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

// 装饰者模式的作用就在于它可以在不改变原有类的基础上动态地给类添加新的功能。
// 之前写过一篇通过源码分析MyBatis的缓存文章，mybatis中的query就是使用了装饰者设计模式。
// 用一段简单的代码来模拟一下mybatis中query的实现原理：
public interface Query {// 查询接口，有简单查询和缓存查询
    Result query(String sql);
}
