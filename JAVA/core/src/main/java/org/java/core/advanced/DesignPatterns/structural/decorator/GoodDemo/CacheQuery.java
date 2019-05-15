package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

import java.util.Map;
import java.util.HashMap;

// 缓存查询，如果查询相同的sql，不直接查询数据库，而是返回map中存在的Result

// 这里CacheQuery就是一个装饰类，SimpleQuery是一个被装饰者。
// 我们通过装饰者设计模式动态地给SimpleQuery添加了缓存功能，而不需要修改SimpleQuery的代码。

//当然，装饰者模式也有缺点，就是会存在太多的类。
public class CacheQuery implements Query{

    private Query query;

    private Map<String, Result> cache = new HashMap<>();

    public CacheQuery(Query query){
        this.query = query;
    }

    @Override
    public Result query(String sql) {
        if(cache.containsKey(sql)){
            return cache.get(sql);
        }
        Result result = query.query(sql);
        cache.put(sql, result);
        return result;
    }
}
