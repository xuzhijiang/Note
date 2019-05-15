package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

// 简单查询，相当于直接查询数据库，这里直接返回Result，相当于是数据库查询的结果
public class SimpleQuery implements Query{
    @Override
    public Result query(String sql) {
        return new Result(new Object(), sql);
    }
}
