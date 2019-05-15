package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

public class Result {// 查询结果类，相当于一个domain
    private Object obj;
    private String sql;

    public Result(Object obj, String sql) {
        this.obj = obj;
        this.sql = sql;
    }
}
