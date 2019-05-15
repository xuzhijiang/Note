package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

import java.util.ArrayList;
import java.util.List;

// 如果我们需要添加一个过滤的查询(sql中有敏感字的就直接返回null，而不查询数据库)，
// 只需要可以添加一个FilterQuery装饰者即可：
public class FilterQuery implements Query{

    private Query query;

    private List<String> words = new ArrayList<String>();

    public FilterQuery(Query query) {
        this.query = query;
        words.add("fuck");
        words.add("sex");
    }

    @Override
    public Result query(String sql) {
        for (String word : words) {
            if (sql.contains(word)) return null;
        }
        return query.query(sql);
    }

}
